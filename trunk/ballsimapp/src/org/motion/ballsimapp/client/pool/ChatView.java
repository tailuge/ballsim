package org.motion.ballsimapp.client.pool;

import org.motion.ballsimapp.client.comms.GWTGameEventHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public final class ChatView {

	protected final TextArea messageArea = new TextArea();
	protected final TextBox chatText = new TextBox();
	protected final Button chatButton = new Button("chat");

	protected GWTGameEventHandler eventHandler;

	protected static final String newline = "\n";

	public ChatView(int width, String layoutId) {
		layout(width, layoutId);
		setChatEnable(false);
	}

	private void layout(int width, String layoutId) {
		messageArea.setWidth(width - 8 + "px");
		messageArea.setHeight(width / 3 + "px");
		chatText.setWidth(width - width / 8 - 10 + "px");
		chatButton.setWidth(width / 8 + "px");

		RootPanel.get(layoutId + ".message").add(messageArea);
		RootPanel.get(layoutId + ".chat").add(chatText);
		RootPanel.get(layoutId + ".chat").add(chatButton);

		messageArea.addStyleName("messageArea");

		chatButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventHandler.handleEvent(BilliardsEventFactory
						.sendChat(chatText.getText()));
				chatText.setText("");
			}
		});

		chatText.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					eventHandler.handleEvent(BilliardsEventFactory
							.sendChat(chatText.getText()));
					chatText.setText("");
				}
			}
		});

	}

	public void setEventHandler(GWTGameEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	public void appendMessage(String message) {
		messageArea.setText(message + newline + messageArea.getText());
	}

	public void setChatEnable(boolean enable) {
		chatButton.setEnabled(enable);
	}

}
