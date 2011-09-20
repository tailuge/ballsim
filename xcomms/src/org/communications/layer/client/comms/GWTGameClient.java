package org.communications.layer.client.comms;

import org.communications.layer.client.comms.ChannelFactory.ChannelCreatedCallback;
import org.communications.layer.client.GWTGameServer;
import org.communications.layer.client.GWTGameServerAsync;
import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventAttribute;
import org.communications.layer.shared.GameEventUtil;

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


	public void login(final String user, GWTGameEventHandler handler) {
		
		distributor.addGameEventListener(user, handler);
		
		gameServer.connect(GameEventMarshaller.marshal(connect(user)),
				new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				distributor.sendError(user,caught.getMessage());
			}

			public void onSuccess(String jsonEvent) {
				GameEvent event = GameEventMarshaller.deMarshal(jsonEvent);
				distributor.distribute(distributor.target(user,event));
				if (connectedUser.isEmpty())
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

	public GameEvent connect(String user)
	{
		GameEvent loginEvent = GameEventUtil.simpleEvent("user", user);
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
						distributor.distributeAll(distributor.info("Channel opened"));
						connectedUser = user;
					}

					@Override
					public void onMessage(String message) {
						System.out.println("Recv:"+message);
						distributor.distribute(GameEventMarshaller.deMarshal(message));
					}

					@Override
					public void onError(SocketError error) {
						distributor.distributeAll(distributor.error(error.getDescription()));
					}

					@Override
					public void onClose() {
						distributor.distributeAll(distributor.info("Channel closed"));
					}
				});
			}
		});
	}
	
	public void notify(String sender, String target, String message) {		
		GameEvent event = GameEventUtil.simpleEvent("message",message);
		event.addAttribute(new GameEventAttribute("target",target));
		gameServer.notify(GameEventMarshaller.marshal(event),ackHandler(sender));		
	}
	
	
	@SuppressWarnings("rawtypes")
	private AsyncCallback ackHandler(final String user) {
		
		return new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				distributor.sendError(user,caught.getMessage());
			}

			@Override
			public void onSuccess(Object result) {
			}
		};
	}


}
