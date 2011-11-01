package org.motion.ballsimapp.client.pool;

import org.motion.ballsimapp.client.comms.GWTGameEventHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class InfoViewLayout {

	protected final Button loginButton = new Button("Login");
	protected final TextBox playerId = new TextBox();
	protected final PasswordTextBox password = new PasswordTextBox();
	protected final String layoutId;

	protected GWTGameEventHandler eventHandler;

	public InfoViewLayout(int width, String layoutId) {
		this.playerId.setWidth(width / 3 + "px");
		this.password.setWidth(width / 3 + "px");
		this.password.setText("secret");
		this.layoutId = layoutId;
		loginButton.setEnabled(true);
		addElementsToRoot();
	}

	protected void addElementsToRoot() {
		RootPanel.get(layoutId + ".login").add(playerId);
		RootPanel.get(layoutId + ".login").add(password);
		RootPanel.get(layoutId + ".login").add(loginButton);
	}

}
