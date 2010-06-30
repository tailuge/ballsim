package motion.org;

import javax.vecmath.Vector3d;

public class Event 
{

	public Vector3d pos;
	public Vector3d vel;
	public Vector3d spin;
	public Vector3d sidespin;
	public StateTime statetime;
	public EventType type;
	
	public Event(Vector3d pos, Vector3d vel, Vector3d spin, Vector3d sidespin, StateTime statetime, EventType type) 
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
