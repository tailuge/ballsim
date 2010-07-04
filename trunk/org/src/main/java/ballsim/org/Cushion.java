package ballsim.org;

import org.apache.commons.math.geometry.Vector3D;

/**
 * @author luke
 *
 * Static class that computes Event at intersection of ball with cushion
 */
public class Cushion 
{

	/**
	 * 
	 * Returns event if ball described by event e hits cushion at x
	 * within period maxt.
	 * 
	 * @param e
	 * @param cushx
	 * @param maxt
	 * @return
	 */
	public static Event xCollisionsWith(Event e, double cushx, double maxt)
	{
		// need to get the coefficients of quadratic whos solution is 
		// ball pos-x = 0
		
		// quadratic is of form    cushx = pos0 + vel0*t + 1/2 acc * t^2
		// A = 1/2 * acc
		// B = vel0
		// C = pos0 - cushx
		
		double C = e.getAccelerationVector().getX();
		double B = e.vel.getX();
		double A = e.pos.getX() - cushx;

		Event collision = getCollisionEvent(e,A,B,C,maxt);

		// reflect in cushion
		if (collision != null)
			collision.vel = new Vector3D(-collision.vel.getX(),collision.vel.getY(),0);

		return collision;
	}
	
	
	public static Event yCollisionsWith(Event e, double cushy, double maxt)
	{
		double C = e.getAccelerationVector().getY();
		double B = e.vel.getY();
		double A = e.pos.getY() - cushy;

		Event collision = getCollisionEvent(e,A,B,C,maxt);

		// reflect in cushion
		if (collision != null)
			collision.vel = new Vector3D(collision.vel.getX(),-collision.vel.getY(),0);

		return collision;
	}

	private static Event getCollisionEvent(Event e, double A, double B, double C, double maxt)
	{
		double tCollision = Quadratic.getLeastPositiveRoot(A, B, C);
		
		if ((tCollision > 0) && (tCollision<maxt))
		{
			Event collision = e.advanceDelta(tCollision);
			collision.state = State.Sliding;
			collision.type = EventType.Cushion;
						
			// todo reduce spin
			// todo add rotation from sidespin.
			// todo increase sidespin from friction with cushion
			
			return collision;
		}
		
		return null;
		
	}

}
