package org.motion.ballsimapp.canvas;

import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.util.Interpolate;
import org.motion.ballsim.physics.Table;

public class TableCanvas extends TableRenderer {

	
	public TableCanvas(int w, int h) {
		super(w, h);
	}

	public void plotAtTime(Table table, double t) {

		context.clearRect(0, 0, width, height);
		//PlotCushion.plot(context, scale);
		
		for (Ball ball : table.balls()) {
			Event e = Interpolate.toTime(ball, t);
			PlotEvent.plotEvent(e, context, scale);
		}
	}



}
