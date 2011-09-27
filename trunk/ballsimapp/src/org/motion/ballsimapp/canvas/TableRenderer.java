package org.motion.ballsimapp.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class TableRenderer {

	protected final PlotScale scale = new PlotScale();
	protected Canvas canvas;
	private Canvas backBuffer;
	private Canvas background;
	protected Context2d context;
	protected Context2d backBufferContext;
	private Context2d backgroundContext;
	protected final PlotAim aim;
	protected final PlotPlacer placer;
	protected final int width;
	protected final int height;
	private final CssColor redrawColor = CssColor.make("rgba(95,95,205,0.7)");

	public TableRenderer(int w, int h) {
		width = w;
		height = h;
		scale.setWindowInfo(width, height);
		aim = new PlotAim(scale);
		placer = new PlotPlacer(scale);
		initialiseCanvas();
		initialiseBackground();
		clearBackBuffer();
		moveBackBufferToFront(backBufferContext, context);
	}

	private void initialiseCanvas()
	{
		canvas = Canvas.createIfSupported();
		backBuffer = Canvas.createIfSupported();

		canvas.setWidth(width + "px");
		canvas.setHeight(height + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);

		backBuffer.setWidth(width + "px");
		backBuffer.setHeight(height + "px");
		backBuffer.setCoordinateSpaceWidth(width);
		backBuffer.setCoordinateSpaceHeight(height);

		context = canvas.getContext2d();
		backBufferContext = backBuffer.getContext2d();		
	}
	
	private void initialiseBackground() {
		background = Canvas.createIfSupported();
		background.setWidth(width + "px");
		background.setHeight(height + "px");
		background.setCoordinateSpaceWidth(width);
		background.setCoordinateSpaceHeight(height);
		backgroundContext = background.getContext2d();
		backgroundContext.setFillStyle(redrawColor);
		backgroundContext.fillRect(0, 0, width, height);
		PlotCushion.plot(backgroundContext, scale);
	}

	public Canvas getInitialisedCanvas() {
		return canvas;
	}

	protected void clearBackBuffer() {
		backBufferContext.drawImage(backgroundContext.getCanvas(), 0, 0);
	}

	public void moveBackBufferToFront(Context2d back, Context2d front) {
		front.drawImage(back.getCanvas(), 0, 0);
	}

}