package ballsim.org;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.geometry.Vector3D;


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
	
	
	public void initialise(Vector3D pos_, Vector3D vel_)
	{
		Event e = new Event(pos_,vel_,new Vector3D(0,0,0),new Vector3D(0,0,0), new StateTime(), EventType.InitialHit);
		events.add(e);
	}

	public int computeNaturalEvents()
	{
		// if state is rolling, compute stationary time
		
		return 0;
	}

	public Vector3D getPos(double t) 
	{
		return events.get(0).pos;
	}

	public Vector3D getVel(double t) 
	{
		return events.get(0).vel;
	}

	public Vector3D getSpin(double t) 
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
	public static Vector3D getRollingAccelerationVector(Event e)
	{
		Vector3D result = new Vector3D(e.vel);
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
		
		Vector3D result = new Vector3D(e.vel);
		Vector3D acc = getRollingAccelerationVector(e);
		acc.scale(t);
		result.add(acc);
		return e;
	}
	
}
