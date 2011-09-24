package org.motion.ballsimapp.canvas;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.Interpolator;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.client.pool.handlers.AimChange;

public class TableCanvas extends TableRenderer implements
		ActiveMouseMoveHandler.MouseEvent {

	@SuppressWarnings("unused")
	private final ActiveMouseMoveHandler mouseHandler;
	private final AimChange aimChangeHandler;
	boolean aiming = true;

	public TableCanvas(int w, int h, AimChange aimChangeHandler) {
		super(w, h);
		this.aimChangeHandler = aimChangeHandler;
		mouseHandler = new ActiveMouseMoveHandler(canvas, this);
	}

	public void plotAtTime(Table table, double t) {
		clearBackBuffer();
		for (Ball ball : table.balls()) {
			Event e = Interpolator.interpolate(ball, t);
			PlotEvent.plotEvent(e, backBufferContext, scale);
		}
		moveBackBufferToFront(backBufferContext, context);
	}

	@Override
	public void handle(int mouseX, int mouseY) {
		updateAim(mouseX, mouseY);
		aimChangeHandler.handleAimChanged();
	}

	private void updateAim(int x, int y) {
		moveBackBufferToFront(backBufferContext, context);
		if (aiming) {
			aim.setAimToTarget(scale.mouseToWorld(x, y));
			aim.plotAim(context);
		} else {
			placer.setCueBallPosition(scale.mouseToWorld(x, y));
			placer.plotPlacer(context);
		}
	}

	public Vector3D getAimDirection() {
		return aim.getAimDirection();
	}

	public void setAimDirection(Vector3D aimDirection) {
		aiming = true;
		aim.setAimDirection(aimDirection);
		moveBackBufferToFront(backBufferContext, context);
		aim.plotAim(context);
	}

	// for aiming.. refactor.
	public void setCueBallPosition(Vector3D position) {
		aim.setCueBallPosition(position);
	}

	public Vector3D getCueBallPosition() {
		return placer.getCueBallPosition();
	}

	public void setPlacer(Vector3D cueBallPosition) {
		aiming = false;
		placer.setCueBallPosition(cueBallPosition);
		moveBackBufferToFront(backBufferContext, context);
		placer.plotPlacer(context);

	}

	public void place() {
		aiming = false;
	}

	public void aim() {
		aiming = true;
	}

}
