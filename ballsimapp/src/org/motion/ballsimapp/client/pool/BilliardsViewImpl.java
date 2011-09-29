package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.Interpolator;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.canvas.Animation;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class BilliardsViewImpl extends BilliardsViewLayout implements
		BilliardsView {

	public BilliardsViewImpl(int width, String playerId) {
		super(width, playerId);
		actionButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actionButton.setEnabled(false);
				eventHandler.handleEvent(BilliardsEventFactory
							.inputComplete(new Aim(1,aim.getCueBallPosition(),aim.getAimDirection(),
									spin.getSpin(), power.getPower())));
			}
		});
	}


	@Override
	public void appendMessage(String message) {
		messageArea.setText(messageArea.getText() + newline + message);
	}

	@Override
	public void setEventHandler(GWTGameEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	@Override
	public void showTable(Table table) {
		Event cueBall = Interpolator.interpolate(table.ball(1), 0);
		aim.setCueBallPosition(cueBall.pos);
		tableCanvas.plotAtTime(table, 0);
	}

	@Override
	public void aim(int timeout) {
		aiming = true;
		actionButton.setText("Hit");
		actionButton.setEnabled(true);
		aim.aim();
	}

	@Override
	public void place(int timeout) {
		aiming = false;
		actionButton.setText("Place");
		actionButton.setEnabled(true);
		aim.place();
	}

	@Override
	public void animate(Table table) {
		actionButton.setEnabled(false);
		@SuppressWarnings("unused")
		Animation showAnimation = new Animation(table, tableCanvas,
				eventHandler);

	}

	@Override
	public void setAim(Aim aim) {		
		spin.setSpin(aim.spin);
		power.setPower(aim.speed);
		this.aim.setAim(aim);
	}

	@Override
	public void showAim() 
	{
		aim.showAim();
	}

	@Override
	public void showPlacer() {
		aim.showPlacer();
	}

	@Override
	public String getPlayerId() {
		return playerId.getText();
	}

	@Override
	public String getPassword() {
		return password.getText();
	}

	@Override
	public void watch() {
		actionButton.setEnabled(false);
	}

}