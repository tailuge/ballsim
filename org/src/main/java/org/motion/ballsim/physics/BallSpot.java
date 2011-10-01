package org.motion.ballsim.physics;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.gwtsafe.Rotation;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
/**
 * @author luke
 * 
 *         As a ball rolls we need to keep track of 2 points on its surface to correctly draw it.
 *         This class holds the methods to update positions under rotation and also generate
 *         the three points that are visible from above as the ball rolls.
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
	
	/**
	 * Scale to ball radius, plus factor so that pixels render inside perimeter
	 */
	private static final double scale = Ball.R * 0.9;
	
	static public List<Vector3D> getVisibleSpots(Event e)
	{
		List<Vector3D> result = new ArrayList<Vector3D>();
		Vector3D cross = Vector3D.crossProduct(e.angularPos, e.angularPosPerp);
		
		result.add(e.pos.add(scale * (isVisible(e.angularPos)?1:-1),e.angularPos));
		result.add(e.pos.add(scale * (isVisible(e.angularPosPerp)?1:-1),e.angularPosPerp));
		result.add(e.pos.add(scale * (isVisible(cross)?1:-1),cross));

		return result;
	}
}
