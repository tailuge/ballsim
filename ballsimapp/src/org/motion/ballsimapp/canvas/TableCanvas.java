package org.motion.ballsimapp.canvas;

import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.Interpolator;
import org.motion.ballsim.physics.Table;

public class TableCanvas extends TableRenderer {

	public TableCanvas(int w, int h) {
		super(w, h);
	}

	public void plotAtTime(Table table, double t) {

		context.clearRect(0, 0, width, height);

		for (Ball ball : table.balls()) {
			Event e = Interpolator.interpolate(ball, t);
			PlotEvent.plotEvent(e, context, scale);
		}
	}



}
