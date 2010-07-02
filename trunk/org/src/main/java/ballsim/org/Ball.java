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
	
	public void initialise(Vector3D pos_, Vector3D vel_)
	{
		Event e = new Event(pos_,vel_,new Vector3D(0,0,0),new Vector3D(0,0,0), new Vector3D(0,0,0),new StateTime(), EventType.InitialHit);
		events.add(e);
	}

	public List<Event> getEvents() 
	{
		return events;
	}

	
}
