package org.communications.layer.client.chat;

import org.communications.layer.client.Channel;
import org.communications.layer.client.ChannelFactory;
import org.communications.layer.client.ChannelFactory.ChannelCreatedCallback;
import org.communications.layer.client.GWTGameServer;
import org.communications.layer.client.GWTGameServerAsync;
import org.communications.layer.client.GameEventMarshaller;
import org.communications.layer.client.SocketError;
import org.communications.layer.client.SocketListener;
import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventAttribute;
import org.communications.layer.shared.GameEventUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ChatModel {

	private final GWTGameServerAsync gameServer = GWT
			.create(GWTGameServer.class);

	TextInputNotify networkMessageHandler;
	
	public void setNetworkMessageHandler(TextInputNotify networkMessageHandler) {
		
		this.networkMessageHandler = networkMessageHandler;
	}
	

	public void sendMessage(String target,String text) {		
		GameEvent event = GameEventUtil.simpleEvent("message",text);
		event.addAttribute(new GameEventAttribute("target",target));
		gameServer.notify(GameEventMarshaller.marshal(event),showErrorCallback());		
	}
	
	
	public void login(final String user) {
		gameServer.connect(GameEventMarshaller.marshal(GameEventUtil.simpleEvent("user",user)),
				new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				networkMessageHandler.handle("","problem:" + caught.getMessage());
			}

			public void onSuccess(String jsonEvent) {
				networkMessageHandler.handle("","login confirmed: "+jsonEvent);
				GameEvent event = GameEventMarshaller.deMarshal(jsonEvent);
				String channelId = event.getAttribute("channelName").getValue();
				createNamedChannelListener(channelId);
			}
		});
	}
	
	private void createNamedChannelListener(final String channelName) {
		
		ChannelFactory.createChannel(channelName,

		new ChannelCreatedCallback() {
			@Override
			public void onChannelCreated(Channel channel) {
				channel.open(new SocketListener() {
					@Override
					public void onOpen() {
						networkMessageHandler.handle("",channelName + " Channel opened!");
					}

					@Override
					public void onMessage(String message) {
						networkMessageHandler.handle("",message);
					}

					@Override
					public void onError(SocketError error) {
						networkMessageHandler.handle("","Error: " + error.getDescription());
					}

					@Override
					public void onClose() {
						networkMessageHandler.handle("","Channel closed!");
					}
				});
			}
		});

	}
	
	
	private AsyncCallback<String> showErrorCallback() {
		
		return new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				networkMessageHandler.handle("","Could not send to server:" + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
			}
		};
	}

}
