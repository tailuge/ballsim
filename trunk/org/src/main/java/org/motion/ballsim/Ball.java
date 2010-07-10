package org.motion.ballsim;

import java.util.ArrayList;
import java.util.List;


public class Ball 
{
	
	public static final double R = 1.0;
	public static final double mg = 10;
	public static final double accelRoll = -Table.froll * mg;
	public static final double accelSlide = -Table.fslide * mg;
	public static final double equilibriumTolerance = 0.0001;
	public static final double stationaryAngularTolerance = 0.0001;
	public static final double stationaryTolerance = 0.0001;
		
	private List<Event> events = new ArrayList<Event>();
	
	public List<Event> getEvents() 
	{
		return events;
	}

	public Ball(Event init)
	{
		getEvents().add(init);
	}

	public Event lastEvent() 
	{
		if (events.size()>0)
			return events.get(events.size()-1);
		
		return null;
	}
	
}
