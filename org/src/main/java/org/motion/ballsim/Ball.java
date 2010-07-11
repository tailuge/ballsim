package org.motion.ballsim;

import java.util.Collection;
import java.util.LinkedList;


public class Ball 
{
	
	public static final double R = 1.0;
	public static final double mg = 10;
	public static final double accelRoll = -Table.froll * mg;
	public static final double accelSlide = -Table.fslide * mg;
	public static final double equilibriumTolerance = 0.0001;
	public static final double stationaryAngularTolerance = 0.0001;
	public static final double stationaryTolerance = 0.0001;
		
	private LinkedList<Event> events = new LinkedList<Event>();
	
	public void add(Event e)
	{
		if (!events.isEmpty())
		{
			double last = lastEvent().t;
			double et = e.t;
			if (last > et)
			{
				System.out.println("last:"+last +" et:"+ et);
				System.exit(1); // broken
			}
		}
		events.addLast(e);
	}
	
	public Ball(Event init)
	{
		init.ball = this;
		add(init);
	}

	public Event lastEvent() 
	{
		Event result = null;
		for(Event e: events)
		{
			if ((result==null) || (e.t > result.t))
				result = e;
		}
		return result;
	}
	
	public String toString()
	{
		String result = "Ball:";
		for(Event e: events)
		{
			result += e.t + "\n";
		}
		return events.toString();
	}

	public Collection<Event> getAllEvents() 
	{
		return events;
	}
}
