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

public class BilliardsViewImpl extends BilliardsViewLayout implements
		BilliardsView {

	private final ChatView chatView;
	private final Timer timer = new Timer() {
		public void run() {
			appendMessage("out of time !");
			hit();
		}
	};

	public BilliardsViewImpl(int width, String layoutId, String defaultId) {
		super(width, layoutId);

		chatView = new ChatView(width, layoutId);
		
		playerId.setText(defaultId);

		actionButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hit();
			}
		});

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

	private void hit() {
		timer.cancel();
		actionButton.setEnabled(false);
		eventHandler.handleEvent(BilliardsEventFactory.inputComplete(new Aim(1,
				aim.getCueBallPosition(), aim.getAimDirection(),
				spin.getSpin(), power.getPower())));
	}

	@Override
	public void appendMessage(String message) {
		chatView.appendMessage(message);
	}

	@Override
	public void setEventHandler(GWTGameEventHandler eventHandler) {
		this.eventHandler = eventHandler;
		chatView.setEventHandler(eventHandler);
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
	public void showAim() {
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

	@Override
	public void setVisibility(boolean visibility) {
		actionButton.setVisible(visibility);
		RootPanel.get(layoutId + ".table").setVisible(visibility);
		RootPanel.get(layoutId + ".tableactive").setVisible(visibility);
		RootPanel.get(layoutId + ".inputspin").setVisible(visibility);
		RootPanel.get(layoutId + ".inputpower").setVisible(visibility);
		RootPanel.get(layoutId + ".tablebg").setVisible(visibility);
	}

	@Override
	public void setChatEnable(boolean enable) {
		chatView.setChatEnable(enable);
	}

	@Override
	public void clearMessage() {
		chatView.clearMessage();
	}

}
