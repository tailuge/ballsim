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
		events.addLast(e);
	}
	
	public Ball(Event init)
	{
		init.ball = this;
		add(init);
	}

	public Event lastEvent() 
	{
		return events.getLast();
	}
	
	public String toString()
	{
		return events.toString();
	}

	public Collection<Event> getAllEvents() 
	{
		return events;
	}
}
