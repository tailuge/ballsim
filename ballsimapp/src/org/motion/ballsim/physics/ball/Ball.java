package org.motion.ballsim.physics.ball;

import java.util.Collection;
import java.util.LinkedList;

public final class Ball {

	public static final double R = 1.0;
	public static final double mg = 10;
	public static final double equilibriumTolerance = 0.0000001;
	public static final double stationaryAngularTolerance = 0.0000001;
	public static final double stationaryTolerance = 0.0000001;

	private LinkedList<Event> events = new LinkedList<Event>();

	public final int id;

	public void add(Event e) {
		e.ballId = id;

		if (!events.isEmpty())
		{
			if (lastEvent().t > e.t)
				System.err.println("time error "  + lastEvent().t + " > " + e.t);
		}
		events.addLast(e);
	}

	public Ball(int id) {
		this.id = id;
	}

	public Event lastEvent() {
		return events.getLast();
	}

	public Event firstEvent() {
		return events.getFirst();
	}

	public String toString() {
		String result = "";
		for(Event event:events)
		{
			result = result + event.toString() + "\n"; 
		}
		return result;
	}

	public Collection<Event> getAllEvents() {
		return events;
	}

	public void resetToFirst() {
		setFirstEvent(events.getFirst());
	}

	public void setFirstEvent(Event e) {
		events.clear();
		add(e);
	}

}
