package a.b.c.client;

import gwt.g3d.client.Surface3D;

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

public class Inputs extends Render implements MouseDownHandler, MouseUpHandler,
		MouseMoveHandler, MouseWheelHandler {

	protected double inputAngle = 0;
	protected double speed = 0.5;
	
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

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		inputAngle = ((double) event.getX() / 500.0) * 2.0 * Math.PI;
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		if (event.getNativeButton() == NativeEvent.BUTTON_RIGHT)
		{
			event.stopPropagation();
			
		}
	}

	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		speed += event.isNorth() ? 0.1 : -0.1;
		if (speed < 0.1) speed = 0.1;
		if (speed > 1.0) speed = 1.0;
//		Window.alert(speed+"");
	}

}
