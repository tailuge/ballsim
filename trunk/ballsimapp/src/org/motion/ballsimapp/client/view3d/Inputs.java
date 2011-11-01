package org.motion.ballsimapp.client.view3d;

import org.motion.ballsim.physics.game.Aim;
import org.motion.ballsim.physics.gwtsafe.Vector3D;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.client.pool.BilliardsEventFactory;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class Inputs extends Render implements MouseDownHandler, MouseUpHandler,
		MouseMoveHandler, MouseWheelHandler {

	protected double inputAngle = 0;
	protected double inputSpeed = 0.5;
	protected Vector3D inputPos = Vector3D.ZERO;
	protected Vector3D inputDir = Vector3D.PLUS_J;
	protected Vector3D inputSpin = Vector3D.ZERO;
	protected double cueSwing = 0;

	private double swingBegin = 0;
	private boolean active = false;
	private int initialX = 0;
	private int initialY = 0;
	private double initialAngle = 0;

	GWTGameEventHandler eventHandler;

	/** Mouse handler registration */
	private HandlerRegistration mouseDownRegistration, mouseUpRegistration,
			mouseMoveRegistration, mouseWheelHandler;

	public Inputs(int width, String layoutId) {
		super(width, layoutId);
		mouseDownRegistration = surface.addMouseDownHandler(this);
		mouseUpRegistration = surface.addMouseUpHandler(this);
		mouseMoveRegistration = surface.addMouseMoveHandler(this);
		mouseWheelHandler = surface.addMouseWheelHandler(this);
	}

	public void dispose() {
		mouseDownRegistration.removeHandler();
		mouseUpRegistration.removeHandler();
		mouseMoveRegistration.removeHandler();
		mouseWheelHandler.removeHandler();
	}

	protected void updateThrust(double t) {
		if (swingBegin == 0)
			swingBegin = t;

		cueSwing = Math.sin((t - swingBegin) * 5.0 * inputSpeed);
		cueSwing *= cueSwing;
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		active = true;
		initialX = event.getX();
		initialY = event.getY();
		initialAngle = inputAngle;
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		if (!active)
			return;

		if (event.getNativeButton() == NativeEvent.BUTTON_MIDDLE)
			return;
		
		if (event.getNativeButton() == NativeEvent.BUTTON_RIGHT) {
			DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor",
					"all-scroll");
			double horizontal = 3.5 * (double) (event.getX() - initialX)
					/ width;
			double vertical = 3.5 * (double) (event.getY() - initialY) / width;
			if (vertical > 1)
				vertical = 1;
			if (vertical < -1)
				vertical = -1;
			if (horizontal > 1)
				horizontal = 1;
			if (horizontal < -1)
				horizontal = -1;
			inputSpin = new Vector3D(horizontal, vertical, 0);
			swingBegin = 0;
		} else {
			DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor",
					"crosshair");

			int offset = event.getX() - initialX;
			inputAngle = initialAngle + 0.1 * Math.signum(offset)
					* (Math.pow(Math.abs(offset), 1.7) / width) * Math.PI;
			inputDir = new Vector3D(Math.sin(inputAngle), Math.cos(inputAngle),
					0);
			swingBegin = 0;
		}
		
		eventHandler.handleEvent(BilliardsEventFactory.cursorInput(getAim()));

	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		active = false;
		DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
		if (event.getNativeButton() == NativeEvent.BUTTON_MIDDLE) {
			eventHandler.handleEvent(BilliardsEventFactory.inputComplete(getAim()));
		}
	}

	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		swingBegin = 0;
		inputSpeed += event.isNorth() ? 0.1 : -0.1;
		if (inputSpeed < 0.1)
			inputSpeed = 0.1;
		if (inputSpeed > 1.0)
			inputSpeed = 1.0;
	}

	private Aim getAim()
	{
		return new Aim(0, inputPos, inputDir, inputSpin, inputSpeed);
	}
}
