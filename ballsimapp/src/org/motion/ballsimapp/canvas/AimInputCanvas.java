package org.motion.ballsimapp.canvas;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsimapp.client.pool.handlers.AimChange;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public class AimInputCanvas implements ActiveMouseMoveHandler.MouseEvent {

	final public Canvas canvas;
	final private Context2d context;
	final private PlotScale scale = new PlotScale();
	protected final PlotAim aim;
	protected final PlotPlacer placer;

	@SuppressWarnings("unused")
	private final ActiveMouseMoveHandler mouseHandler;
	private final AimChange aimChangeHandler;


	
	private Vector3D cursor;

	public AimInputCanvas(int w, int h, AimChange aimChangeHandler) {
		this.aimChangeHandler = aimChangeHandler;
		canvas = Canvas.createIfSupported();
		context = canvas.getContext2d();
		scale.setWindowInfo(w, h);
		canvas.setWidth(w + "px");
		canvas.setHeight(h + "px");
		canvas.setCoordinateSpaceWidth(w);
		canvas.setCoordinateSpaceHeight(h);
		aim = new PlotAim(scale);
		placer = new PlotPlacer(scale);
		mouseHandler = new ActiveMouseMoveHandler(canvas, this);
	}

	@Override
	public void handle(int mouseX, int mouseY) {
		cursor = scale.mouseToWorld(mouseX, mouseY);
		aimChangeHandler.handleAimChanged();
	}

	public void setAim(Aim aimInfo)
	{
		aim.setCueBallPosition(aimInfo.pos);
		aim.setAimDirection(aimInfo.dir);
		placer.setCueBallPosition(aimInfo.pos);
	}

	public void setAimFromCursor()
	{
		aim.setAimToTarget(cursor);
	}

	public void setPlacerFromCursor()
	{
		aim.setCueBallPosition(cursor);
		placer.setCueBallPosition(cursor);
	}

	public void showAim()
	{
		context.clearRect(0, 0, scale.w, scale.h);
		aim.plotAim(context);		
	}
	
	public void showPlacer()
	{
		context.clearRect(0, 0, scale.w, scale.h);
		placer.plotPlacer(context);
	}
	
	public Vector3D getCursor() {
		return cursor;
	}

	public Vector3D getAimDirection() {
		return aim.getAimDirection();
	}

	public void setAimDirection(Vector3D aimDirection) {
		aim.setAimDirection(aimDirection);
	}

	// for aiming.. refactor.
	public void setCueBallPosition(Vector3D position) {
		placer.setCueBallPosition(position); // fix		
		aim.setCueBallPosition(position);
	}

	public Vector3D getCueBallPosition() {
		return placer.getCueBallPosition();
	}

	public void setPlacer(Vector3D cueBallPosition) {
		placer.setCueBallPosition(cueBallPosition);
		placer.plotPlacer(context);

	}

	public void place() {
		canvas.setVisible(true);
		placer.plotPlacer(context);
	}

	public void aim() {
		canvas.setVisible(true);
		aim.plotAim(context);
	}
}
