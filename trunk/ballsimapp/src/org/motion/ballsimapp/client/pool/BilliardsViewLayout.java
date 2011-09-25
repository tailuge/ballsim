package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
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
	protected final Button actionButton = new Button("Hit");
	protected final RootPanel root;
	protected final TableCanvas tableCanvas;
	protected final TextArea messageArea = new TextArea();
	protected final TextBox playerId = new TextBox();
	protected final PasswordTextBox password = new PasswordTextBox();
	protected static final String newline = "\n";

	protected GWTGameEventHandler eventHandler;

	protected boolean aiming;

	public BilliardsViewLayout(int width, String playerId) {
		
		int height = width * 15 / 10;
		this.root = RootPanel.get(playerId);
		spin = new SpinInputCanvas(width / 8, width / 8, this);
		power = new PowerInputCanvas(width - width / 4, width / 10, this);
		tableCanvas = new TableCanvas(width, height, this);
		messageArea.setWidth(width  + "px");
		messageArea.setHeight(width / 2 + "px");
		this.playerId.setWidth(width/3  + "px");
		this.password.setWidth(width/3  + "px");
		this.playerId.setText(playerId);
		this.password.setText("secret");

		addElementsToRoot();
		
		actionButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actionButton.setEnabled(false);
				if (aiming) {
					eventHandler.handleEvent(BilliardsEventFactory
							.aimComplete(new Aim(tableCanvas.getAimDirection(),
									spin.getSpin(), power.getPower())));
				} else {
					eventHandler.handleEvent(BilliardsEventFactory
							.placeBallComplete(tableCanvas.getCueBallPosition()));
				}
			}
		});
	}

	protected void addElementsToRoot() {
		root.add(playerId);
		root.add(password);
		root.add(tableCanvas.getInitialisedCanvas());
		root.add(spin.getInitialisedCanvas());
		root.add(power.getInitialisedCanvas());
		root.add(actionButton);
		root.add(messageArea);
	}

	@Override
	public void handleAimChanged() {
		if (aiming) {
			eventHandler.handleEvent(BilliardsEventFactory.aimUpdate(new Aim(
					tableCanvas.getAimDirection(), spin.getSpin(), power
							.getPower())));
		} else {
			eventHandler.handleEvent(BilliardsEventFactory
					.placeBallUpdate(tableCanvas.getCueBallPosition()));
		}
	}
}