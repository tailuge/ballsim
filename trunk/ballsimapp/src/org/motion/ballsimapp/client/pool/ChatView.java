package org.motion.ballsimapp.client.pool;

import org.motion.ballsimapp.client.comms.GWTGameEventHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public final class ChatView {

	protected final TextArea messageArea = new TextArea();
	protected final TextBox chatText = new TextBox();
	protected final Button chatButton = new Button(".");

	protected GWTGameEventHandler eventHandler;

	protected static final String newline = "\n";

	public ChatView(int width, String layoutId) {
		layout(width,layoutId);
	}

	private void layout(int width, String layoutId)
	{
		messageArea.setWidth(width + "px");
		messageArea.setHeight(width / 3 + "px");
		chatText.setWidth(width - width / 9 + "px");
		chatButton.setWidth(width / 10 + "px");
		RootPanel.get(layoutId + ".message").add(messageArea);
		RootPanel.get(layoutId + ".chat").add(chatText);
		RootPanel.get(layoutId + ".chat").add(chatButton);	
		
		chatButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventHandler.handleEvent(BilliardsEventFactory.sendChat(chatText.getText()));
				chatText.setText("");
			}
		});

	}
	
	public void setEventHandler(GWTGameEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	public void appendMessage(String message) {
		messageArea.setText(message + newline + messageArea.getText());
	}

}
