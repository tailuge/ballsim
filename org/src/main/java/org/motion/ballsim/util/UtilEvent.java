package org.motion.ballsim.util;

import java.util.ArrayList;
import java.util.Collection;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.ball.State;
import org.motion.ballsim.physics.ball.Transition;

public class UtilEvent 
{

	private static final Vector3D zero = Vector3D.ZERO;

	public static Event hit(Vector3D pos, Vector3D dir, double speed, double cueHeight)
	{
		Event e = new Event(
				pos,
				dir.scalarMultiply(speed*Table.maxVel),
				Vector3D.PLUS_K,
				Vector3D.PLUS_J,
				UtilVector3D.crossUp(dir.scalarMultiply(speed/Ball.R)).scalarMultiply(-cueHeight*Table.maxAngVel),
				zero,
				State.Sliding,
				0,
				Transition.InitialHit,
				0,
				0
				);
		e.state = State.deriveStateOf(e);
		return e;
	}
	
	public static Event stationary(Vector3D pos) 
	{
		return new Event(pos, Vector3D.ZERO, Vector3D.PLUS_K,Vector3D.PLUS_J,
				Vector3D.ZERO, Vector3D.ZERO, State.Stationary, 0,
				Transition.FinishedRoll,0,0);
	}
	public static Collection<Event> generateRadialEvents(Vector3D  pos, int segments, double speed, double height)
	{
	
		Collection<Event> radialEvents = new ArrayList<Event>();
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
		Collection<Event> radialEvents = new ArrayList<Event>();

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
