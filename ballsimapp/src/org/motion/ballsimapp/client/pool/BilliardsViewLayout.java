package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsimapp.canvas.AimInputCanvas;
import org.motion.ballsimapp.canvas.PowerInputCanvas;
import org.motion.ballsimapp.canvas.SpinInputCanvas;
import org.motion.ballsimapp.canvas.TableCanvas;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.client.pool.handlers.AimChange;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class BilliardsViewLayout implements AimChange {

	protected final SpinInputCanvas spin;
	protected final PowerInputCanvas power;
	protected final AimInputCanvas aim;
	protected final Button actionButton = new Button("Hit");
	protected final Button loginButton = new Button("Login");
	protected final TableCanvas tableCanvas;
	protected final TextArea messageArea = new TextArea();
	protected final TextBox playerId = new TextBox();
	protected final PasswordTextBox password = new PasswordTextBox();
	protected static final String newline = "\n";

	protected GWTGameEventHandler eventHandler;

	protected boolean aiming;

	public BilliardsViewLayout(int width, String playerId) {
		
		int height = width * 2;
		int inputHeight = height/8;
		spin = new SpinInputCanvas(inputHeight, inputHeight, this);
		power = new PowerInputCanvas(width - 2*inputHeight, inputHeight, this);
		aim = new AimInputCanvas(width, height, this);
		tableCanvas = new TableCanvas(width, height);
		messageArea.setWidth(width * 2  + "px");
		messageArea.setHeight(width/2  + "px");
		this.playerId.setWidth(width/3  + "px");
		this.password.setWidth(width/3  + "px");
		this.playerId.setText(playerId);
		this.password.setText("secret");
		actionButton.setWidth(inputHeight+"px");
		actionButton.setHeight(inputHeight+"px");
		loginButton.setEnabled(false);
		addElementsToRoot();

		RootPanel.get(playerId+".tablefg").setSize(width+"px", height+"px");
		RootPanel.get(playerId+".tablebg").setSize(width+"px", height+"px");

		actionButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actionButton.setEnabled(false);
				eventHandler.handleEvent(BilliardsEventFactory
							.inputComplete(new Aim(1,aim.getCueBallPosition(),aim.getAimDirection(),
									spin.getSpin(), power.getPower())));
			}
		});
		

	}

	protected void addElementsToRoot() {
		final String base = playerId.getText() + ".";
		RootPanel.get(base+"login").add(playerId);
		RootPanel.get(base+"login").add(password);
		RootPanel.get(base+"login").add(loginButton);
		RootPanel.get(base+"table").add(tableCanvas.getInitialisedCanvas());
		RootPanel.get(base+"tableactive").add(aim.canvas);
		RootPanel.get(base+"inputspin").add(spin.getInitialisedCanvas());
		RootPanel.get(base+"inputpower").add(power.getInitialisedCanvas());
		RootPanel.get(base+"inputhit").add(actionButton);
		RootPanel.get(base+"message").add(messageArea);
	}

	@Override
	public void handleAimChanged() {

		if (aiming)
			aim.setAimFromCursor();
		else
			aim.setPlacerFromCursor();
			
		Aim input = new Aim(1,aim.getCueBallPosition(),aim.getAimDirection(),spin.getSpin(), power.getPower());		
			
		eventHandler.handleEvent(BilliardsEventFactory.cursorInput(input));
	
	}
}