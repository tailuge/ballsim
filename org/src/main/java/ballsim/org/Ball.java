package ballsim.org;

import java.util.ArrayList;
import java.util.List;


public class Ball 
{
	
	public final static double R = 1.0;
	public final static double mg = 10;
	public final static double accelRoll = -Table.froll * mg;
		
	private List<Event> events = new ArrayList<Event>();
	

	public List<Event> getEvents() 
	{
		return events;
	}

	
}
