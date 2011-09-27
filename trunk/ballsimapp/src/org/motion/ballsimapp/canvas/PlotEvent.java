package org.motion.ballsimapp.canvas;

import java.util.List;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.BallSpot;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.State;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class PlotEvent {

	private static CssColor[] colours = new CssColor[] {
			CssColor.make("rgba(255,255,255,1)"),
			CssColor.make("rgba(255,0,0,1)"),
			CssColor.make("rgba(255,255,0,1)"),
			CssColor.make("rgba(0,150,0,1)"),
			CssColor.make("rgba(0,0,150,1)"),
			CssColor.make("rgba(0,150,150,1)"),
			CssColor.make("rgba(150,150,0,1)"),
			CssColor.make("rgba(200,100,0,1)"),
			CssColor.make("rgba(50,50,100,1)"),
			CssColor.make("rgba(0,150,255,1)"),
			};
	private static CssColor black = CssColor.make("rgba(5,5,5,0.7)");
	private static CssColor white = CssColor.make("rgba(255,255,255,0.3)");

	private static CssColor getColour(int id) {
		return colours[id - 1];
	}

	public static void plotEvent(Event e, Context2d context, PlotScale scale) {
		double x = scale.screenX(e.pos.getX());
		double y = scale.screenY(e.pos.getY());

		context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(black);
		context.setFillStyle(black);
		context.arc(x + 1, y + 1, scale.scaled(Ball.R) + 0.5, 0, Math.PI * 2.0,
				true);
		context.closePath();
		context.fill();

		context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(black);
		context.setFillStyle(getColour(e.ballId));
		context.arc(x, y, scale.scaled(Ball.R), 0, Math.PI * 2.0, true);
		context.closePath();
		context.stroke();
		context.fill();

		context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(white);
		context.setFillStyle(white);
		context.arc(x-scale.scaled(Ball.R/2), y-scale.scaled(Ball.R/2), scale.scaled(Ball.R/3), 0, Math.PI * 2.0, true);
		context.closePath();
		context.stroke();
		context.fill();

		plotSpots(e, context, scale);
		// plotDebug(e,context,scale);
	}

	@SuppressWarnings("unused")
	private static void plotDebug(Event e, Context2d context, PlotScale scale) {
		double x = scale.screenX(e.pos.getX());
		double y = scale.screenY(e.pos.getY());

		context.beginPath();
		String s = "" + e.vel.getNorm();
		context.setStrokeStyle(black);
		context.strokeText("speed:" + (s + "      ").substring(0, 5), x + 10, y);
		if (e.state == State.Sliding)
			context.setStrokeStyle(CssColor.make("rgba(255,0,0,1)"));
		context.strokeText("" + e.state, x + 10, y + 11);
		context.closePath();

	}

	private static void plotSpots(Event e, Context2d context, PlotScale scale) {
		context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(black);
		context.setFillStyle(black);
		List<Vector3D> spots = BallSpot.getVisibleSpots(e);

		for (Vector3D spot : spots) {
			int x = scale.screenX(spot.getX());
			int y = scale.screenY(spot.getY());
			context.rect(x, y, 1, 1);
		}

		context.closePath();
		context.fill();

	}

}
