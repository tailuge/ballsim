package org.motion.ballsimapp.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public class TableRenderer {

	protected final PlotScale scale = new PlotScale();
	protected Canvas canvas;
	protected Context2d context;
	protected final int width;
	protected final int height;

	public TableRenderer(int w, int h) {
		width = w;
		height = h;
		scale.setWindowInfo(width, height);
		initialiseCanvas();
	}

	private void initialiseCanvas() {
		canvas = Canvas.createIfSupported();

		canvas.setWidth(width + "px");
		canvas.setHeight(height + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);

		context = canvas.getContext2d();
	}


	public Canvas getInitialisedCanvas() {
		return canvas;
	}


}