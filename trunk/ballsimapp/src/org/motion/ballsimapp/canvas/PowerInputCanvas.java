package org.motion.ballsimapp.canvas;

import org.motion.ballsimapp.client.pool.handlers.AimChange;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class PowerInputCanvas {

	private Canvas canvas;
	private Context2d context;
	private final CssColor barColor = CssColor.make("rgba(50,200,80,0.8)");
	private final CssColor scaleColor = CssColor.make("rgba(100,100,100,0.8)");

	private final int width, height;

	private double power;
	private final AimChange aimChangeHandler;

	public PowerInputCanvas(int width_, int height_, AimChange aimChangeHandler) {
		height = height_;
		width = width_;
		this.aimChangeHandler = aimChangeHandler;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
		updateCanvas();
	}

	public void updateCanvas() {
		plotScale();
		plotPowerBar((int) ((double)width * power));
	}

	public Canvas getInitialisedCanvas() {
		canvas = Canvas.createIfSupported();

		canvas.setWidth(width + "px");
		canvas.setHeight(height + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);

		context = canvas.getContext2d();
		initHandlers();
		setPower(0.75);
		return canvas;
	}

	private void initHandlers() {

		new ActiveMouseMoveHandler(canvas,
				new ActiveMouseMoveHandler.MouseEvent() {

					@Override
					public void handle(int mouseX, int mouseY) {
						setPower((double)mouseX / (double)width);
						aimChangeHandler.handleAimChanged();
					}
				});
	}
	

	private void plotPowerBar(int x) {
		context.beginPath();
		context.setLineWidth(1);
		context.setFillStyle(barColor);
		context.fillRect(0, 0, x, height);
		context.closePath();
		context.fill();
	}

	private void plotScale() {
		context.beginPath();
		context.setLineWidth(1);
		context.setFillStyle(scaleColor);
		context.fillRect(0, 0, width, height);
		context.closePath();
		context.fill();
	}
}
