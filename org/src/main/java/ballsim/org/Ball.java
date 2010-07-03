package ballsim.org;

import java.util.ArrayList;
import java.util.List;


public class Ball 
{
	
	public static final double R = 1.0;
	public static final double mg = 10;
	public static final double accelRoll = -Table.froll * mg;
	public static final double equilibriumTolerance = 0.0001;
	public static final double stationaryAngularTolerance = 0.0001;
	public static final double stationaryTolerance = 0.0001;
		
	private List<Event> events = new ArrayList<Event>();
	
	public List<Event> getEvents() 
	{
		return events;
	}

	
}