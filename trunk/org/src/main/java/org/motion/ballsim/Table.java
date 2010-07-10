package org.motion.ballsim;

import java.util.ArrayList;
import java.util.List;

public class Table 
{

	public final static double yp = 20;
	public final static double yn = -yp;
	public final static double xp = 10;
	public final static double xn = -xp;
	
	public final static double froll = 10;
	public final static double fslide = 40;
	
	public List<Ball> balls = new ArrayList<Ball>();

	/**
	 * Given all balls that are on the table
	 * check each to see when the next rolling or
	 * stationary transition will occur
	 * 
	 * @return
	 */
	public EventBallMap nextNatural() 
	{
		Event next = null;
		Ball nextBall = null;
		for(Ball ball : balls)
		{
			Event e = ball.lastEvent();
			if (e.state == State.Stationary)
				continue;
			
			e = e.next();
			if ((next == null) || (e.t < next.t))
			{
				next = e;
				nextBall = ball;
			}
		}

		EventBallMap result = new EventBallMap();
		if (next != null)
			result.put(next,nextBall);

		return result;
	}
}
