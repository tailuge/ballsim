package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsimapp.canvas.AimInputCanvas;
import org.motion.ballsimapp.canvas.PowerInputCanvas;
import org.motion.ballsimapp.canvas.SpinInputCanvas;
import org.motion.ballsimapp.canvas.TableCanvas;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.client.pool.handlers.AimChange;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class BilliardsViewLayout implements AimChange {

	protected final SpinInputCanvas spin;
	protected final PowerInputCanvas power;
	protected final AimInputCanvas aim;
	protected final Button actionButton = new Button("Hit");
	protected final Button loginButton = new Button("Login");
	protected final TableCanvas tableCanvas;
	protected final TextBox playerId = new TextBox();
	protected final PasswordTextBox password = new PasswordTextBox();

	protected GWTGameEventHandler eventHandler;

	protected final String layoutId;
	
	protected boolean aiming;

	public BilliardsViewLayout(int width, String layoutId) {
		
		int height = width * 2;
		int inputHeight = height/10;
		this.layoutId = layoutId;
		
		spin = new SpinInputCanvas(inputHeight, inputHeight, this);
		power = new PowerInputCanvas(width - 2*inputHeight, inputHeight, this);
		aim = new AimInputCanvas(width, height, this);
		tableCanvas = new TableCanvas(width, height);
		this.playerId.setWidth(width/3  + "px");
		this.password.setWidth(width/3  + "px");
		this.password.setText("secret");
		actionButton.setWidth(inputHeight+"px");
		actionButton.setHeight(inputHeight+"px");
		loginButton.setEnabled(true);
		
		addElementsToRoot();

//		RootPanel.get(layoutId+".tablefg").setSize(width+"px", height+"px");
		RootPanel.get(layoutId+".tablebg").setSize(width+"px", height+"px");	
		RootPanel.get(layoutId+".pad").setSize(width+"px", height+"px");	

	}

	protected void addElementsToRoot() {
		RootPanel.get(layoutId+".login").add(playerId);
		RootPanel.get(layoutId+".login").add(password);
		RootPanel.get(layoutId+".login").add(loginButton);
		RootPanel.get(layoutId+".table").add(tableCanvas.getInitialisedCanvas());
		RootPanel.get(layoutId+".tableactive").add(aim.canvas);
		RootPanel.get(layoutId+".inputspin").add(spin.getInitialisedCanvas());
		RootPanel.get(layoutId+".inputpower").add(power.getInitialisedCanvas());
		RootPanel.get(layoutId+".inputhit").add(actionButton);
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