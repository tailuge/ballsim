package org.motion.ballsim.plotter;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.Ball;
import org.motion.ballsim.Event;
import org.motion.ballsim.gwtsafe.Vector3D;

public class BallSpot 
{

	static private boolean isVisible(Vector3D angularPos)
	{
		return angularPos.getZ() > 0.0;
	}
	
	static public List<Vector3D> getVisibleSpots(Event e)
	{
		List<Vector3D> result = new ArrayList<Vector3D>();
		
		if (isVisible(e.angularPos))
		{
			result.add(e.pos.add(e.angularPos.scalarMultiply(Ball.R)));
		}
		else
		{
			result.add(e.pos.subtract(e.angularPos.scalarMultiply(Ball.R)));			
		}
				
		return result;
	}
}
