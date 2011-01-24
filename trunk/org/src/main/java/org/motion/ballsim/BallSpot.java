package org.motion.ballsim;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.gwtsafe.Rotation;
import org.motion.ballsim.gwtsafe.Vector3D;
/**
 * @author luke
 * 
 *         As a ball rolls we need to keep track of 2 points on its surface to correctly draw it.
 *         This class holds the methods to update positions under rotation and also generate
 *         the six points that are visible as the ball rolls.
 * 
 */
public final class BallSpot 
{

	public static Vector3D progressTo(Vector3D position, Vector3D velocity,Vector3D acceleration, double t) 
	{
		return rotationMatrixTo(velocity,acceleration,t).applyTo(position).normalize();
	}
	
	static Rotation rotationMatrixTo(Vector3D velocity,
			Vector3D acceleration, double t)
	{
		Rotation rot = Rotation.IDENTITY;
		Rotation acc = Rotation.IDENTITY;

		try 
		{
			rot = new Rotation(velocity, velocity.getNorm() * t);
		}
		catch (Exception e)
		{
		}
		
		try 
		{
			acc = new Rotation(acceleration, acceleration.getNorm()* t * t / 2.0);
		}
		catch (Exception e)
		{
		}
				
		return rot.applyTo(acc);		
	}
	
	static private boolean isVisible(Vector3D angularPos)
	{
		return angularPos.getZ() < 0.0;
	}
	
	private static final double scale = Ball.R * 0.9;
	
	static public List<Vector3D> getVisibleSpots(Event e)
	{
		List<Vector3D> result = new ArrayList<Vector3D>();
		
		if (isVisible(e.angularPos))
			result.add(e.pos.add(e.angularPos.scalarMultiply(scale)));
		else
			result.add(e.pos.subtract(e.angularPos.scalarMultiply(scale)));			

		if (isVisible(e.angularPosPerp))
			result.add(e.pos.add(e.angularPosPerp.scalarMultiply(scale)));
		else
			result.add(e.pos.subtract(e.angularPosPerp.scalarMultiply(scale)));			

		Vector3D cross = Vector3D.crossProduct(e.angularPos, e.angularPosPerp);

		if (isVisible(cross))
			result.add(e.pos.add(cross.scalarMultiply(scale)));
		else
			result.add(e.pos.subtract(cross.scalarMultiply(scale)));			

		return result;
	}
}
