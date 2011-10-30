package a.b.c.client;

import gwt.g3d.client.Surface3D;

import org.motion.ballsim.physics.gwtsafe.Vector3D;

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
	
	/** Mouse handler registration */
	private HandlerRegistration mouseDownRegistration, mouseUpRegistration,
			mouseMoveRegistration, mouseWheelHandler;

	public Inputs(Surface3D surface) {
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

	final double width = 500;
	final double height = 500;
	
	protected void updateThrust(double t)
	{
		if (swingBegin == 0)
			swingBegin = t;
		
		cueSwing = Math.sin((t-swingBegin) * 5.0 * inputSpeed);
		cueSwing *= cueSwing;
	}
	
	@Override
	public void onMouseMove(MouseMoveEvent event) {
		if (!active)
			return;
		
		if (event.isShiftKeyDown())
		{
			double horizontal = 1.5 * (double) (event.getX()-width/2.0) / width;
			double vertical = 1.5 * (double) (event.getY()-height/2.0) / height;
			if (vertical > 1) vertical = 1;
			if (vertical < -1) vertical = -1;
			if (horizontal > 1) horizontal = 1;
			if (horizontal < -1) horizontal = -1;
			inputSpin = new Vector3D(horizontal,vertical,0);
			swingBegin = 0;
			return;
		}
		
		inputAngle = ((double) event.getX() / width) * 2.5 * Math.PI;
		inputDir = new Vector3D(Math.sin(inputAngle),Math.cos(inputAngle), 0);
		swingBegin = 0;
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		active = false;
		DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		active = true;
	    DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "crosshair");

		if (event.getNativeButton() == NativeEvent.BUTTON_RIGHT)
		{
			DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "all-scroll");
		}
	}

	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		swingBegin = 0;
		inputSpeed += event.isNorth() ? 0.1 : -0.1;
		if (inputSpeed < 0.1) inputSpeed = 0.1;
		if (inputSpeed > 1.0) inputSpeed = 1.0;
	}

}
