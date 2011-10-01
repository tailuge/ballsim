package org.motion.ballsimapp.client.comms;

import static org.motion.ballsimapp.shared.Events.ALREADY_CONNECTED;
import static org.motion.ballsimapp.shared.Events.CLIENT_ERROR;

import org.motion.ballsimapp.client.GWTGameServer;
import org.motion.ballsimapp.client.GWTGameServerAsync;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Only one channel can be opened per client therefore this class acts as a
 * distributer of messages arriving at that channel to registered listeners
 * based on the 'target' name in the message Only needed to support multiple
 * clients per browser during development.
 */
public class GWTGameClient {

	private final static Distributor distributor = new Distributor();
	private final static ChannelConnector channelConnector = new ChannelConnector(
			distributor);

	private final GWTGameServerAsync gameServer = GWT
			.create(GWTGameServer.class);

	public void connect(final String user, final GWTGameEventHandler handler) {

		if (channelConnector.getStatus() == ChannelStatus.unconnected) {
			// make connection
			firstConnect(user, handler);
		}

		if (channelConnector.getStatus() == ChannelStatus.connecting) {
			// postpone until connected
			connectWhenConnectionEstablished(user, handler);
		}

		if (channelConnector.getStatus() == ChannelStatus.connected) {
			// connect reusing existing channel
			connectWithSynonym(user, handler,
					channelConnector.getConnectedUser());
		}
	}

	private void firstConnect(final String user, GWTGameEventHandler handler) {
		GameEvent loginEvent = Events.event("user", user);
		connectWithEvent(user, handler, loginEvent);
	}

	private void connectWithSynonym(final String user,
			GWTGameEventHandler handler, final String synonym) {
		GameEvent loginEvent = Events.event("user", user);
		loginEvent.addAttribute(new GameEventAttribute("synonym", synonym));
		connectWithEvent(user, handler, loginEvent);

	}

	private void connectWhenConnectionEstablished(final String user,
			final GWTGameEventHandler handler) {

		channelConnector.addPendingConnect(new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				connect(user, handler);
			}
		});
	}

	public void connectWithEvent(final String user,
			GWTGameEventHandler handler, GameEvent login) {
		distributor.addGameEventListener(user, handler);

		gameServer.connect(GameEventMarshaller.marshal(login),
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						distributor.sendError(user, caught.getMessage());
					}

					public void onSuccess(String jsonEvent) {
						GameEvent event = GameEventMarshaller
								.deMarshal(jsonEvent);
						if (event.hasAttribute(ALREADY_CONNECTED)) {
							distributor.toUser(user, Events.channelConnected());
						} else {
							channelConnector.createNamedChannelListener(user,
									event.getAttribute("channelName")
											.getValue());
						}
					}
				});
	}

	public void notify(GameEvent event) {
		gameServer.notify(GameEventMarshaller.marshal(event), ackHandler());
	}

	@SuppressWarnings("rawtypes")
	private AsyncCallback ackHandler() {

		return new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				distributor.distributeAll(Events.event(CLIENT_ERROR,
						caught.getMessage()));
			}

			@Override
			public void onSuccess(Object result) {
			}
		};
	}

}
