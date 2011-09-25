package org.motion.ballsimapp.client.comms;

import static org.motion.ballsimapp.shared.Events.*;

import org.motion.ballsimapp.client.GWTGameServer;
import org.motion.ballsimapp.client.GWTGameServerAsync;
import org.motion.ballsimapp.client.comms.ChannelFactory.ChannelCreatedCallback;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.motion.ballsimapp.shared.Events;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Only one channel can be opened per client therefore this class
 * acts as a distributer of messages arriving at that channel to
 * registered listeners based on the 'target' name in the message
 * Only needed to support multiple clients per browser during development.
 */
public class GWTGameClient {

	private final static Distributor distributor = new Distributor();
	
	private static String connectedUser = "";
	
	private final GWTGameServerAsync gameServer = GWT
			.create(GWTGameServer.class);


	public void connect(final String user, GWTGameEventHandler handler) {
		
		distributor.addGameEventListener(user, handler);

		final boolean connected = !connectedUser.isEmpty();
		
		GameEvent login = connect(user);
		
		// only allow first user to connect (in single browser)
		if (connectedUser.isEmpty())
		{
			connectedUser = user;
		}
		
		gameServer.connect(GameEventMarshaller.marshal(login),
				new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				distributor.sendError(user,caught.getMessage());
			}

			public void onSuccess(String jsonEvent) {
				GameEvent event = GameEventMarshaller.deMarshal(jsonEvent);
				if (!connected)
				{
					createNamedChannelListener(user,event.getAttribute("channelName").getValue());
				}
				else
				{
					distributor.sendError(user,"browser already connected");					
				}
			}
		});
	}

	private GameEvent connect(String user)
	{
		GameEvent loginEvent = Events.event("user", user);
		if (!connectedUser.isEmpty())
		{
			loginEvent.addAttribute(new GameEventAttribute("synonym",connectedUser));
		}
		return loginEvent;
	}

	private void createNamedChannelListener(final String user, final String channelName) {
		
		ChannelFactory.createChannel(channelName,

		new ChannelCreatedCallback() {
			@Override
			public void onChannelCreated(Channel channel) {
				channel.open(new SocketListener() {
					@Override
					public void onOpen() {
						distributor.distributeAll(Events.channelConnected());
					}

					@Override
					public void onMessage(String message) {
						System.out.println("Recv:"+message);
						distributor.distribute(GameEventMarshaller.deMarshal(message));
					}

					@Override
					public void onError(SocketError error) {
						distributor.distributeAll(Events.event(CLIENT_ERROR,error.getDescription()));
					}

					@Override
					public void onClose() {
						distributor.distributeAll(Events.event(CLIENT_ERROR,"Channel closed"));
					}
				});
			}
		});
	}
	
	public void notify(GameEvent event) {		
		gameServer.notify(GameEventMarshaller.marshal(event),ackHandler());		
	}
	
	
	@SuppressWarnings("rawtypes")
	private AsyncCallback ackHandler() {
		
		return new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				distributor.distributeAll(Events.event(CLIENT_ERROR, caught.getMessage()));
			}

			@Override
			public void onSuccess(Object result) {
			}
		};
	}


}
