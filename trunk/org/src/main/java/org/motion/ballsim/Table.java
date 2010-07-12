package org.motion.ballsim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Table 
{

	
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
	public Event nextNatural() 
	{
		Event next = null;
		for(Ball ball : balls)
		{
			Event e = ball.lastEvent();
			if (e.state == State.Stationary)
				continue;
			
			Event eNext = e.next();
			if ((next == null) || (eNext.t < next.t))
			{
				next = eNext;
				assert( next.t > e.t);
			}
		}

		return next;
	}

	public Event nextCushionBefore(double maxt) 
	{
		Event next = null;
		for(Ball ball : balls)
		{
			Event e = ball.lastEvent();
			if (e.state == State.Stationary)
				continue;
			
			Event eCushion = Cushion.hit(e, maxt);
			if (eCushion == null)
				continue;

			if ((next == null) || (eCushion.t < next.t))
			{
				next = eCushion;
				assert(next.t > e.t);
				assert(Cushion.onTable(next));
			}		
		}		

		
		if ((next != null) && (next.t < maxt))
			return next;
		return null;
	}
	
	public String toString()
	{	
		return balls.toString();
	}

	public Collection<Event> getAllEvents() 
	{
		Collection<Event> all = new ArrayList<Event>();

		for(Ball ball : balls)
		{
			all.addAll(ball.getAllEvents());
		}
		
		return all;
	}
}
