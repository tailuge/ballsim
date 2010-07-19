package org.motion.ballsim;

import java.awt.Color;
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

	public final Color colour;
	
	public void add(Event e)
	{
		assert(lastEvent().t<e.t);		
		events.addLast(e);
	}
	
	public Ball(Event init)
	{
		events.addLast(init);
		colour = getColour();
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

	public void resetToFirst()
	{
		setFirstEvent(events.getFirst());
	}
	
	public void setFirstEvent(Event e)
	{
		events.clear();
		events.add(e);	
	}
	
	private static Color[] colours = new Color[] {Color.white,Color.red,Color.yellow};
	private static int nextColour = 0;

	private static Color getColour()
	{
		return colours[nextColour++ % colours.length];
	}
}
