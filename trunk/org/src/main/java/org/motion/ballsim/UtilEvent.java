package org.motion.ballsim;

import java.util.Collection;

import org.apache.commons.math.geometry.Vector3D;

import com.google.common.collect.Lists;

public class UtilEvent 
{

	private static final Vector3D zero = Vector3D.ZERO;

	public static Event hit(Vector3D pos, Vector3D dir, double speed, double cueHeight)
	{
		Event e = new Event(
				pos,
				dir.scalarMultiply(speed),
				zero,
				UtilVector3D.crossUp(dir.scalarMultiply(speed/Ball.R)).scalarMultiply(cueHeight),
				zero,
				State.Sliding,
				0,
				EventType.InitialHit,
				0,
				0
				);
		e.state = State.deriveStateOf(e);
		return e;
	}
	
	public static Collection<Event> generateRadialEvents(Vector3D  pos, int segments, double speed, double height)
	{
	
		Collection<Event> radialEvents = Lists.newArrayList();
		for(int i=0; i<segments; i++)
		{
			Vector3D dir = new Vector3D(2.0 * Math.PI * (double)i/(double)segments,0);
			Event e = UtilEvent.hit(pos, dir, speed, height);
			radialEvents.add(e); 			
		}
		
		return radialEvents;
	}
	
	public static Collection<Event> generateImpactingEvents(Vector3D  pos, Vector3D target, int segments, double speed, double height)
	{
		Collection<Event> radialEvents = Lists.newArrayList();

		Vector3D lineOfCenters = target.subtract(pos);
		Vector3D perpendicular = UtilVector3D.crossUp(lineOfCenters).normalize();
		double arcLength = 1.2;
		for(double skew = -arcLength; skew <= arcLength; skew += 2.0*arcLength/(double)segments)
		{
			Vector3D dir = lineOfCenters.add(perpendicular.scalarMultiply(skew * 2 * Ball.R)).normalize();
			Event e = UtilEvent.hit(pos, dir, speed, height);
			radialEvents.add(e);
		}
		
		return radialEvents;
	}	

}
