package org.motion.ballsimapp.canvas;

import java.util.ArrayList;
import java.util.List;

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
		
		List<Event> events = new ArrayList<Event>();
		
		for (Ball ball : table.balls()) 
			events.add(Interpolate.toTime(ball, t));

		PlotEvent.shadows(events, context, scale);

		for (Event event: events)
			PlotEvent.ball(event, context, scale);

		PlotEvent.spots(events, context, scale);
		
		PlotEvent.shines(events, context, scale);
	}



}
