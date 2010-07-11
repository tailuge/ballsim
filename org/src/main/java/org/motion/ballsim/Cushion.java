package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;

import com.google.common.base.Function;

/**
 * @author luke
 *
 * Static class that computes Event at intersection of ball with cushion
 */
public class Cushion 
{

	public final static double yp = 20;
	public final static double yn = -yp;
	public final static double xp = 10;
	public final static double xn = -xp;

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
		
		double A = e.getAccelerationVector().getX()*0.5;
		double B = e.vel.getX();
		double C = e.pos.getX() - cushx;

		Event collision = getCollisionEvent(e,A,B,C,maxt);
		
		if (collision == null)
			return null;
		
		collision = latestEventXStillOnTableAtOrBeforeT(e,collision.t,cushx);

		// reflect in cushion
		if (collision != null)
		{
			collision.vel = new Vector3D(-collision.vel.getX(),collision.vel.getY(),0);
			collision.state = State.deriveStateOf(collision);
			collision.type = EventType.Cushion;
		}
		
		return collision;
	}
	

	/**
	 * Given a starting event and a candidate solution time t where
	 * ball will hit cushion, search linearly backwards from t
	 * until ball is on same side of cushion as when it started.
	 * 
	 * todo: fix it to work for x or y
	 * 
	 * @param e
	 * @param t
	 * @param cushion
	 * @return
	 */
	private static Event latestEventXStillOnTableAtOrBeforeT(final Event e, double t, final double cushion)
	{
		Function<Double,Double> func = new Function<Double, Double>() {
			
			@Override
			public Double apply(Double arg) {
				return e.advanceDelta(arg).pos.getX() - cushion;
			}
		};

		double last = Quadratic.optimise(func, t);
		
		if (last>0)
			return e.advanceDelta(last);
		
		return null;
	}

	
	public static Event yCollisionsWith(Event e, double cushy, double maxt)
	{
		double A = e.getAccelerationVector().getY();
		double B = e.vel.getY();
		double C = e.pos.getY() - cushy;

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
			
			e.t += tCollision;
			return collision;
		}
		
		return null;
		
	}


	public static Event getNext(Event e, double maxt) 
	{
		
		Event eCush = null;
		Event next = null;
		eCush = xCollisionsWith(e, xp, maxt);
		if ( (next==null)  || 
			((next !=null) && (eCush != null) && (eCush.t < next.t)))
				next = eCush;

		eCush = xCollisionsWith(e, xn, maxt);
		if ( (next==null)  || 
				((next !=null) && (eCush != null) && (eCush.t < next.t)))
					next = eCush;

		eCush = yCollisionsWith(e, yp, maxt);
		if ( (next==null)  || 
				((next !=null) && (eCush != null) && (eCush.t < next.t)))
					next = eCush;

		eCush = yCollisionsWith(e, yn, maxt);
		if ( (next==null)  || 
				((next !=null) && (eCush != null) && (eCush.t < next.t)))
					next = eCush;

		return next;
	}
	




}
