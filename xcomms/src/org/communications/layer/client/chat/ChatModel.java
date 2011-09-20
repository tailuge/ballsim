package org.communications.layer.client.chat;

import org.communications.layer.shared.GameEvent;


public class ChatModel extends GWTGameClient {

	NetworkMessageNotify networkMessageHandler;
	
	public void setNetworkMessageHandler(NetworkMessageNotify networkMessageHandler) {		
		this.networkMessageHandler = networkMessageHandler;
	}
	

	public void sendMessage(String sender, String target, String text) {	
		notify(sender, target, text);
	}
	
	
	public void login(final String user) {
		login(user, getEventHandler());
	}

	private GWTGameEventHandler getEventHandler()
	{
		return new GWTGameEventHandler() {

			@Override
			public void handle(GameEvent event) {
				networkMessageHandler.handle(event.toString());
			}
			
		};
	}
}
