package org.motion.ballsimapp.canvas;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsimapp.client.pool.handlers.AimChange;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class SpinInputCanvas {

	private Canvas canvas;
	private Context2d context;
	private final CssColor redColor = CssColor.make("rgba(0,0,255,0.6)");
	private final CssColor whiteColor = CssColor.make("rgba(255,255,255,0.8)");

	private final int width, height;

	private Vector3D spin;
	private final AimChange aimChangeHandler;

	public Vector3D getSpin() {
		return spin;
	}

	public void setSpin(Vector3D spin) {
		this.spin = spin;
		updateCanvas();
	}

	private void updateCanvas() {
		plotSpinBall();
		plotSpinAim((int) (spin.getX() * width)+width/2, (int) (spin.getY() * height)+height/2);
	}

	public SpinInputCanvas(int width_, int height_, AimChange aimChangeHandler) {
		height = height_;
		width = width_;
		this.aimChangeHandler = aimChangeHandler;
	}

	public Canvas getInitialisedCanvas() {
		canvas = Canvas.createIfSupported();

		canvas.setWidth(width + "px");
		canvas.setHeight(height + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);

		context = canvas.getContext2d();
		initHandlers();
		setSpin(Vector3D.ZERO);
		return canvas;
	}

	private void initHandlers() {

		new ActiveMouseMoveHandler(canvas,
				new ActiveMouseMoveHandler.MouseEvent() {

					@Override
					public void handle(int mouseX, int mouseY) {
						updateIfLegalSpin(mouseX, mouseY);
						aimChangeHandler.handleAimChanged();
					}
				});
	}

	private void updateIfLegalSpin(int x, int y) {
		int dx = (x - width / 2);
		int dy = (y - height / 2);

		if (Math.sqrt(dx * dx + dy * dy) < width / 2.1) {
			setSpin(new Vector3D((double) dx / (double) width, (double) dy
					/ (double) height, 0.0));
			aimChangeHandler.handleAimChanged();
		}
	}

	private void plotSpinAim(int x, int y) {
		context.beginPath();
		context.setLineWidth(1);
		context.setFillStyle(redColor);
		context.arc(x, y, width / 10, 0, Math.PI * 2.0, true);
		context.closePath();
		context.fill();
	}

	private void plotSpinBall() {
		context.beginPath();
		context.setLineWidth(1);
		context.setFillStyle(whiteColor);
		context.arc(width / 2, height / 2, width / 2, 0, Math.PI * 2.0, true);
		context.closePath();
		context.fill();
	}
}
