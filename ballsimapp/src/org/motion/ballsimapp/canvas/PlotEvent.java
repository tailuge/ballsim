package org.motion.ballsimapp.canvas;

import java.util.List;

import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.BallSpot;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.ball.State;
import org.motion.ballsim.physics.gwtsafe.Vector3D;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class PlotEvent {

	private static CssColor[] colours = new CssColor[] {
			CssColor.make("rgba(255,255,255,1)"),
			CssColor.make("rgba(255,0,0,1)"),
			CssColor.make("rgba(255,255,0,1)"),
			CssColor.make("rgba(0,150,0,1)"), CssColor.make("rgba(0,0,150,1)"),
			CssColor.make("rgba(0,150,150,1)"),
			CssColor.make("rgba(150,150,0,1)"),
			CssColor.make("rgba(200,100,0,1)"),
			CssColor.make("rgba(50,50,100,1)"),
			CssColor.make("rgba(0,150,255,1)"), };
	private static CssColor black = CssColor.make("rgba(5,5,5,0.7)");
	private static CssColor white = CssColor.make("rgba(255,255,255,0.5)");
	private static CssColor shadow = CssColor.make("rgba(0,0,0,0.5)");

	private static CssColor getColour(int id) {
		return colours[id - 1];
	}

	public static void shadows(List<Event> events, Context2d context,
			PlotScale scale) {

		context.beginPath();
		context.setStrokeStyle(shadow);
		context.setFillStyle(shadow);

		for (Event e : events) {
			if (e.state == State.InPocket)
				continue;

			double offset = scale.scaled(Ball.R / 3.5);
			
			context.arc(((double) scale.screenX(e.pos.getX())) + offset,
					((double) scale.screenY(e.pos.getY())) + offset,
					scale.scaled(Ball.R) + 0.5, 0, Math.PI * 2.0, true);
		}

		context.closePath();
		context.fill();
	}

	public static void shines(List<Event> events, Context2d context,
			PlotScale scale) {

		context.setLineWidth(1);
		context.setStrokeStyle(white);
		context.setFillStyle(white);

		for (Event e : events) {
			if (e.state == State.InPocket)
				continue;

			context.beginPath();

			context.arc(
					(double) scale.screenX(e.pos.getX())
							- scale.scaled(Ball.R / 2),
					(double) scale.screenY(e.pos.getY())
							- scale.scaled(Ball.R / 2),
					scale.scaled(Ball.R / 4), 0, Math.PI * 2.0, true);

			context.fill();
			context.closePath();

		}

		
		
	}

	public static void ball(Event e, Context2d context, PlotScale scale) {
		double x = scale.screenX(e.pos.getX());
		double y = scale.screenY(e.pos.getY());

		if (e.state == State.InPocket)
			return;

		context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(black);
		context.setFillStyle(getColour(e.ballId));
		context.arc(x, y, scale.scaled(Ball.R), 0, Math.PI * 2.0, true);
		context.closePath();
		context.stroke();
		context.fill();

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

	public static void spots(List<Event> events, Context2d context, PlotScale scale) {
		context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(black);
		context.setFillStyle(black);
		
		for (Event e : events) {
			if (e.state == State.InPocket)
				continue;

		List<Vector3D> spots = BallSpot.getVisibleSpots(e);

		for (Vector3D spot : spots) {
			int x = scale.screenX(spot.getX());
			int y = scale.screenY(spot.getY());
			context.rect(x, y, 1.5, 1.5);
		}

		}
		
		context.closePath();
		context.fill();

	}

}
