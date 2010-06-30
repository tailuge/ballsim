package main.org;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3d;

public class Ball 
{
	
	public final static double r = 1.0;
	public final static double mg = 10;
	public final static double accelRoll = -Table.froll * mg;
		
	private List<Event> events = new ArrayList<Event>();
	
	void advance(double t)
	{
		return;
	}

	public StateTime timeUntilNextState()
	{
		return new StateTime();
	}
	
	
	public void initialise(Vector3d pos_, Vector3d vel_)
	{
		Event e = new Event(pos_,vel_,new Vector3d(0,0,0),new Vector3d(0,0,0), new StateTime(), EventType.InitialHit);
		events.add(e);
	}

	public int computeNaturalEvents()
	{
		// if state is rolling, compute stationary time
		
		return 0;
	}

	public Vector3d getPos(double t) 
	{
		return events.get(0).pos;
	}

	public Vector3d getVel(double t) 
	{
		return events.get(0).vel;
	}

	public Vector3d getSpin(double t) 
	{
		return events.get(0).spin;
	}

	public State getState(double t) 
	{
		return events.get(0).statetime.state;
	}

	public List<Event> getEvents() 
	{
		return events;
	}

	
	// initial state - if not in equilibrium, set as sliding
	// given sliding state - progress to rolling
	// given rolling progress to stationary
	// given either progress to cushion
	// given any progress to collision
	
	
	public static double timeToStopRolling(final Event e)
	{

		// v = v0 + a * t   when v = 0
		// acceleration is rolling friction
		// t = -v0/a
		
		double magnitude = e.vel.length();
		return -magnitude / accelRoll;		
	}
	

	public static Event stationaryEvent(final Event e)
	{
	
		assert(e.statetime.state == State.Rolling);
		double t = timeToStopRolling(e);
		Event result = new Event(e);
		result = advanceRollingDelta(result,t);
		result.statetime.state = State.Stationary;
		return result;
	}

	// when rolling acceleration opposes roll
	public static Vector3d getRollingAccelerationVector(Event e)
	{
		Vector3d result = new Vector3d(e.vel);
		result.normalize();
		result.scale(accelRoll);
		return result;
	}
	
	public static Event advanceRollingDelta(Event e, double t)
	{
		// v = v0 + a * t   when v = 0
		// p = p0 + ut + 1/2att
		
		//update pos
		
		// update vel
		
		Vector3d result = new Vector3d(e.vel);
		Vector3d acc = getRollingAccelerationVector(e);
		acc.scale(t);
		result.add(acc);
		return e;
	}
	
}
