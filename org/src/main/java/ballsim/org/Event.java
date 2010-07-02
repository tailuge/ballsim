package ballsim.org;

import org.apache.commons.math.geometry.Vector3D;


public class Event 
{

	public Vector3D pos;
	public Vector3D vel;
	public Vector3D angularPos;
	public Vector3D angularVel;
	public Vector3D spin;
	public Vector3D sidespin;
	public StateTime statetime;
	public EventType type;
	
	public Event(
			Vector3D pos, 
			Vector3D vel, 
			Vector3D angularPos, 
			Vector3D angularVel, 
			Vector3D sidespin, 
			StateTime statetime, 
			EventType type) 
	{
		this.pos = pos;
		this.vel = vel;
		this.angularPos = angularPos;
		this.angularVel = angularVel;
		this.sidespin = sidespin;
		this.statetime = statetime;
		this.type = type;
	}

	public Event(Event e) 
	{
		this.pos = e.pos;
		this.vel = e.vel;
		this.angularPos = e.angularPos;
		this.angularVel = e.angularVel;
		this.spin = e.spin;
		this.sidespin = e.sidespin;
		this.statetime = e.statetime;
		this.type = e.type;
	}
	
	// when rolling acceleration opposes roll
	public Vector3D getRollingAccelerationVector()
	{
		return vel.normalize().scalarMultiply(Ball.accelRoll);
	}
	
	public Event advanceRollingDelta(double t)
	{

		Event e0 = new Event(this);
		
		// v = v0 + a * t   when v = 0
		
		vel = e0.vel.add(getRollingAccelerationVector().scalarMultiply(t));

		// p = p0 + v0*t + a*t*t/2

		pos = e0.pos.add(e0.vel.scalarMultiply(t)).add(getRollingAccelerationVector()).scalarMultiply(t*t/2.0);
		
		return this;
	}
	
	
	public double timeToStopRolling()
	{

		// v = v0 + a * t   when v = 0
		// where acceleration is rolling friction
		// gives t = -v0/a
		
		return -vel.getNorm() / Ball.accelRoll;		
	}
	
	

	public Event stationaryEvent()
	{	
		assert(statetime.state == State.Rolling);
		
		double t = timeToStopRolling();

		Event stationary = new Event(this);

		stationary.statetime.state = State.Stationary;
		
		return stationary.advanceRollingDelta(t);
	}
}
