package ballsim.org;

import org.apache.commons.math.geometry.Vector3D;


public class Event 
{

	public Vector3D pos;
	public Vector3D vel;
	public Vector3D spin;
	public Vector3D sidespin;
	public StateTime statetime;
	public EventType type;
	
	public Event(Vector3D pos, Vector3D vel, Vector3D spin, Vector3D sidespin, StateTime statetime, EventType type) 
	{
		this.pos = pos;
		this.vel = vel;
		this.spin = spin;
		this.sidespin = sidespin;
		this.statetime = statetime;
		this.type = type;
	}

	public Event(Event e) 
	{
		this.pos = e.pos;
		this.vel = e.vel;
		this.spin = e.spin;
		this.sidespin = e.sidespin;
		this.statetime = e.statetime;
		this.type = e.type;
	}
	
}
