package ballsim.org;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luke
 *
 * Given a list of events construct interpolated events useful for
 * plotting. 
 */
public class Interpolator 
{

	List<Event> interpolated = new ArrayList<Event>();
		
	public Interpolator(List<Event> events, double delta)
	{
		interpolate(events,delta);		
	}

	public Interpolator(List<Event> events, int steps)
	{
		// find max time of event and divide
		
		double maxt = 0;
		for(Event e : events)
		{
			if (e.t > maxt)
				maxt = e.t;
		}
		
		interpolate(events,maxt/steps);
	}

	/**
	 * Currently assumes events in order and single ball, will need to fix that eventually.
     *
	 * @param events
	 * @param delta
	 */
	public void interpolate(List<Event> events, double delta)
	{
		double time = 0;
		Event previous = null;
		for(Event e : events)
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
		}
		interpolated.add(events.get(events.size()-1));
	}
	
	public List<Event> getInterpolated()
	{
		return interpolated;
	}
}
