package org.motion.ballsim.plotter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.motion.ballsim.Ball;
import org.motion.ballsim.Event;
import org.motion.ballsim.Table;

/**
 * @author luke
 *
 * Given a list of events construct interpolated events useful for
 * plotting. 
 */
public class Interpolator 
{

	List<Event> interpolated = new ArrayList<Event>();
		
//	public Interpolator(Collection<Event> collection, double delta)
//	{
//		interpolate(collection,delta);		
//	}

	public Interpolator(Table table, int steps)
	{
		// find max time of event and divide
		
		double maxt = 0;
		for(Event e : table.getAllEvents())
		{
			if (e.t > maxt)
				maxt = e.t;
		}
		
		// for each ball interpolate
		
		for (Ball ball:table.balls)
		{
			interpolate(ball.getAllEvents(),maxt/(double)steps);
		}
		
	}

	/**
	 * Currently assumes events in order and single ball, will need to fix that eventually.
     *
	 * @param collection
	 * @param delta
	 */
	private void interpolate(Collection<Event> collection, double delta)
	{
		double time = 0;
		Event previous = null;
		for(Event e : collection)
		{
			if(previous != null)
			{
				while(time < e.t)
				{
					interpolated.add(previous.advanceDelta(time-previous.t));
					time += delta;
				}
			}
			
			previous = e;
			interpolated.add(previous);
		}
		
	}
	
	public List<Event> getInterpolated()
	{
		return interpolated;
	}
}
