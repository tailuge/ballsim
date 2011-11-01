package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.game.Aim;
import org.motion.ballsim.physics.util.Interpolate;
import org.motion.ballsimapp.canvas.Animation;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

public class BilliardsViewImpl extends BilliardsViewLayout implements TableView {

	private final Timer timer = new Timer() {
		public void run() {
			hit();
		}
	};

	public BilliardsViewImpl(int width, String layoutId) {
		super(width, layoutId);

		actionButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hit();
			}
		});
	}

	private void hit() {
		timer.cancel();
		actionButton.setEnabled(false);
		eventHandler.handleEvent(BilliardsEventFactory.inputComplete(new Aim(1,
				aim.getCueBallPosition(), aim.getAimDirection(),
				spin.getSpin(), power.getPower())));
	}

	@Override
	public void setEventHandler(GWTGameEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	@Override
	public void showTable(Table table) {
		Event cueBall = Interpolate.toTime(table.ball(1), 0);
		aim.setCueBallPosition(cueBall.pos);
		tableCanvas.plotAtTime(table, 0);
		}

	@Override
	public void aim(int timeout) {
		aiming = true;
		actionButton.setText("Hit");
		actionButton.setEnabled(true);
		aim.aim();
		timer.schedule(timeout * 1000);
	}

	@Override
	public void place(int timeout) {
		aiming = false;
		actionButton.setText("Place");
		actionButton.setEnabled(true);
		aim.place();
		timer.schedule(timeout * 1000);
	}

	@Override
	public void animate(Table table) {
		aim.hide();
		actionButton.setEnabled(false);
		@SuppressWarnings("unused")
		Animation showAnimation = new Animation(table, this, eventHandler);
	}

	@Override
	public void plotAtTime(Table table, double t) {
		tableCanvas.plotAtTime(table, t);
	}
	
	@Override
	public void setAim(Aim aim) {
		spin.setSpin(aim.spin);
		power.setPower(aim.speed);
		this.aim.setAim(aim);
	}

	@Override
	public void showAim() {
		aim.showAim();
	}

	@Override
	public void showPlacer() {
		aim.showPlacer();
	}

	@Override
	public void watch() {
		actionButton.setEnabled(false);
	}

	@Override
	public void setVisibility(boolean visibility) {
		actionButton.setVisible(visibility);
		RootPanel.get(layoutId + ".table").setVisible(visibility);
		RootPanel.get(layoutId + ".tableactive").setVisible(visibility);
		RootPanel.get(layoutId + ".inputspin").setVisible(visibility);
		RootPanel.get(layoutId + ".inputpower").setVisible(visibility);
		RootPanel.get(layoutId + ".tablebg").setVisible(visibility);
	}

}
