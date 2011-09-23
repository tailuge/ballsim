package org.motion.ballsimapp.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

public class ActiveMouseMoveHandler implements MouseMoveHandler,MouseDownHandler,MouseUpHandler,MouseOutHandler {

	public interface MouseEvent
	{
		void handle(int mouseX, int mouseY);
	}

	private final Canvas canvas;
	private final MouseEvent mouseEvent;
	private boolean active = false;
	
	public ActiveMouseMoveHandler(Canvas canvas, MouseEvent mouseEvent)
	{
		this.canvas = canvas;
		this.mouseEvent = mouseEvent;
		canvas.addMouseMoveHandler(this);
		canvas.addMouseDownHandler(this);
		canvas.addMouseUpHandler(this);
		canvas.addMouseOutHandler(this);
	}
	
	@Override
	public void onMouseMove(MouseMoveEvent event) {
		if (active) mouseEvent.handle(event.getRelativeX(canvas.getElement()),event.getRelativeY(canvas.getElement()));
	}
	
	@Override
	public void onMouseDown(MouseDownEvent event) {
		active = true;
		mouseEvent.handle(event.getRelativeX(canvas.getElement()),event.getRelativeY(canvas.getElement()));
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		active = false;
	}
	
	@Override
    public void onMouseOut(MouseOutEvent event) {
        active = false;
	}

}
