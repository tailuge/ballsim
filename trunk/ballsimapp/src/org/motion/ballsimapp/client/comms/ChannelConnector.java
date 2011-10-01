package org.motion.ballsimapp.client.comms;

import static org.motion.ballsimapp.shared.Events.CLIENT_ERROR;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsimapp.client.comms.ChannelFactory.ChannelCreatedCallback;
import org.motion.ballsimapp.shared.Events;

import com.google.gwt.core.client.Scheduler;

/*
 * 
 */
public class ChannelConnector {

	private static ChannelStatus status = ChannelStatus.unconnected;
	private static List<Scheduler.ScheduledCommand> pendingConnects = new ArrayList<Scheduler.ScheduledCommand>();
	private static String connectedUser;

	private final Distributor distributor;

	public ChannelConnector(Distributor distributor) {
		this.distributor = distributor;
	}

	public ChannelStatus getStatus() {
		return status;
	}

	public String getConnectedUser() {
		return connectedUser;
	}

	public void createNamedChannelListener(final String user,
			final String channelName) {

		status = ChannelStatus.connecting;

		ChannelFactory.createChannel(channelName,

		new ChannelCreatedCallback() {
			@Override
			public void onChannelCreated(Channel channel) {
				channel.open(new SocketListener() {
					@Override
					public void onOpen() {
						channelConnected(user);
					}

					@Override
					public void onMessage(String message) {
						System.out.println("Recv:" + message);
						distributor.distribute(GameEventMarshaller
								.deMarshal(message));
					}

					@Override
					public void onError(SocketError error) {
						distributor.distributeAll(Events.event(CLIENT_ERROR,
								error.getDescription()));
					}

					@Override
					public void onClose() {
						distributor.distributeAll(Events.event(CLIENT_ERROR,
								"Channel closed"));
					}
				});
			}
		});
	}

	public void channelConnected(String user) {
		status = ChannelStatus.connected;
		connectedUser = user;
		distributor.toUser(user, Events.channelConnected());
		for (Scheduler.ScheduledCommand action : pendingConnects) {
			action.execute();
		}
	}

	public void addPendingConnect(Scheduler.ScheduledCommand action) {
		pendingConnects.add(action);
	}

}
