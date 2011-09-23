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
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.client.pool.handlers.AimChange;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

public class BilliardsViewImpl implements BilliardsView, AimChange {

	// inputs

	final private SpinInputCanvas spin;
	final private PowerInputCanvas power;
	final Button hitButton = new Button("Hit");
	final TimeFilter timeFilter = new TimeFilter();

	// visual elements

	final private RootPanel root;
	final private TableCanvas tableCanvas;
	final TextArea messageArea = new TextArea();
	final Label playerName = new Label();
	final static String newline = "\n";
	private boolean aiming;

	// event sink

	GWTGameEventHandler eventHandler;

	public BilliardsViewImpl(int width, RootPanel root) {
		int height = width * 15 / 10;
		this.root = root;
		spin = new SpinInputCanvas(width / 8, width / 8, this);
		power = new PowerInputCanvas(width - width / 4, width / 10, this);
		tableCanvas = new TableCanvas(width, height, this);
		messageArea.setWidth(width * 3 + "px");
		messageArea.setHeight(width / 2 + "px");

		addElementsToRoot();

		hitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hitButton.setEnabled(false);
				if (aiming) {
					Aim aim = new Aim(tableCanvas.getAimDirection(), spin
							.getSpin(), power.getPower());
					GameEvent aimComplete = BilliardsMarshaller
							.eventFromAim(aim);
					aimComplete.addAttribute(new GameEventAttribute(
							"aimComplete", ""));

					eventHandler.handleEvent(aimComplete);
				} else {
					Vector3D pos = tableCanvas.getCueBallPosition();
					GameEvent placeComplete = BilliardsMarshaller
							.eventFromPlace(pos);
					placeComplete.addAttribute(new GameEventAttribute(
							"placeComplete", ""));
					eventHandler.handleEvent(placeComplete);					
				}
			}
		});
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
	public void setEventHandler(GWTGameEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	@Override
	public void showTable(Table table) {
		tableCanvas.plotAtTime(table, 0);
		Event cueBall = Interpolator.interpolate(table.ball(1), 0);
		tableCanvas.setCueBallPosition(cueBall.pos);
	}

	@Override
	public void aim(int timeout) {
		aiming = true;
		hitButton.setText("Hit");
		hitButton.setEnabled(true);
		tableCanvas.aim();
	}

	@Override
	public void place(int timeout) {
		aiming = false;
		hitButton.setText("Place");
		hitButton.setEnabled(true);
		tableCanvas.place();
	}

	@Override
	public void animate(Table table) {

		hitButton.setEnabled(false);

		@SuppressWarnings("unused")
		Animation showAnimation = new Animation(table, tableCanvas,
				eventHandler);

	}

	@Override
	public void handleAimChanged() {
		if (timeFilter.hasElapsed(2)) {
			Aim aim = new Aim(tableCanvas.getAimDirection(), spin.getSpin(),
					power.getPower());
			GameEvent aimComplete = BilliardsMarshaller.eventFromAim(aim);
			aimComplete.addAttribute(new GameEventAttribute("aimUpdate", ""));

			eventHandler.handleEvent(aimComplete);
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
