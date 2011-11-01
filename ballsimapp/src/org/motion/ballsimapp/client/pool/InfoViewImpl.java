package org.motion.ballsimapp.client.pool;

import org.motion.ballsimapp.client.comms.GWTGameEventHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class InfoViewImpl extends InfoViewLayout implements InfoView {


	private final ChatView chatView;
	
	public InfoViewImpl(int width, String layoutId, String defaultId) {
	
		super(width,layoutId);
		
		chatView = new ChatView(width, layoutId);
		
		playerId.setText(defaultId);

		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				login();
			}
		});

	}

	private void login() {
		loginButton.setEnabled(false);
		playerId.setEnabled(false);
		eventHandler.handleEvent(BilliardsEventFactory.beginLogin());
	}
	
	@Override
	public void setEventHandler(GWTGameEventHandler eventHandler) {
		this.eventHandler = eventHandler;
		chatView.setEventHandler(eventHandler);
	}

	@Override
	public void setVisibility(boolean visible) {
		// TODO Auto-generated method stub

	}


	@Override
	public void appendMessage(String message) {
		chatView.appendMessage(message);
	}
	
	@Override
	public void setChatEnable(boolean enable) {
		chatView.setChatEnable(enable);
	}

	@Override
	public void clearMessage() {
		chatView.clearMessage();
	}

	@Override
	public String getPlayerId() {
		return playerId.getText();
	}

	@Override
	public String getPassword() {
		return password.getText();
	}

}
