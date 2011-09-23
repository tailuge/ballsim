package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.Interpolator;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.canvas.Animation;
import org.motion.ballsimapp.canvas.PowerInputCanvas;
import org.motion.ballsimapp.canvas.SpinInputCanvas;
import org.motion.ballsimapp.canvas.TableCanvas;
import org.motion.ballsimapp.client.pool.handlers.AimChange;
import org.motion.ballsimapp.client.pool.handlers.AimNotify;
import org.motion.ballsimapp.client.pool.handlers.ViewNotify;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

public class BilliardsViewImpl implements BilliardsView, AimChange {

	RootPanel root;

	// inputs

	private SpinInputCanvas spin;
	private PowerInputCanvas power;
	final Button hitButton = new Button("Hit");
	final TimeFilter timeFilter = new TimeFilter();

	// outputs

	private TableCanvas tableCanvas;
	final TextArea messageArea = new TextArea();
	final Label playerName = new Label();
	final static String newline = "\n";

	// callbacks

	AimNotify aimHandler;
	ViewNotify animationComplete;

	public BilliardsViewImpl(int width, RootPanel root) {
		int height = width * 15 / 10;
		this.root = root;
		spin = new SpinInputCanvas(width / 8, width / 8, this);
		power = new PowerInputCanvas(width - width / 4, width / 10, this);
		tableCanvas = new TableCanvas(width, height, this);
		messageArea.setWidth(width * 3 + "px");
		messageArea.setHeight(width / 2 + "px");

		addElementsToRoot();
	}

	private void addElementsToRoot() {
		root.add(playerName);
		root.add(tableCanvas.getInitialisedCanvas());
		root.add(spin.getInitialisedCanvas());
		root.add(power.getInitialisedCanvas());
		root.add(hitButton);
		root.add(messageArea);
	}

	@Override
	public void setPlayer(String playerId) {
		playerName.setText(playerId);
	}

	@Override
	public void appendMessage(String message) {
		messageArea.setText(messageArea.getText() + newline + message);
	}

	@Override
	public void setAnimationCompleteHandler(final ViewNotify animationComplete) {
		this.animationComplete = animationComplete;
	}

	@Override
	public void showTable(Table table) {
		tableCanvas.plotAtTime(table, 0);
		Event cueBall = Interpolator.interpolate(table.ball(1), 0);
		tableCanvas.setCueBallPosition(cueBall.pos);
	}

	@Override
	public void aim(int timeout) {
		hitButton.setText("Hit");
		hitButton.setEnabled(true);		
	}

	@Override
	public void place(int timeout) {
		hitButton.setText("Place");
		hitButton.setEnabled(true);		
	}

	@Override
	public void animate(Table table) {

		hitButton.setEnabled(false);

		@SuppressWarnings("unused")
		Animation showAnimation = new Animation(table, tableCanvas,
				animationComplete);

	}

	@Override
	public void setAimHandler(final AimNotify aimHandler) {
		this.aimHandler = aimHandler;
		hitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hitButton.setEnabled(false);
				Aim aim = new Aim(tableCanvas.getAimDirection(), spin.getSpin(), power
						.getPower());
				aimHandler.handleAimComplete(aim);
			}
		});
	}

	@Override
	public void handleAimChanged() {
		if (timeFilter.hasElapsed(2)) {
			Aim aim = new Aim(tableCanvas.getAimDirection(), spin.getSpin(),
					power.getPower());
			aimHandler.handleAimUpdate(aim);
		}
	}

	@Override
	public void setAim(Aim aim) {
		spin.setSpin(aim.spin);
		power.setPower(aim.speed);
		tableCanvas.setAimDirection(aim.dir);
	}

	@Override
	public void setPlacer(Vector3D pos) {
		tableCanvas.setPlacer(pos);
	}

}
