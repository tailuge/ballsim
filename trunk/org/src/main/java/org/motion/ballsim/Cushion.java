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

	public final static double yp = 20*Ball.R;
	public final static double yn = -yp;
	public final static double xp = 10*Ball.R;
	public final static double xn = -xp;

	/**
	 * Collides with cushion of equation of motion intersects cushion
	 * 
	 * need to get the coefficients of quadratic who's solution is:
	 *  cush = pos0 + vel0*t + 1/2 acc * t^2
	 * 
	 * @param e - input event
	 * @param getAxis - returns either X or Y component of a vector
	 * @param reflect - reflect event in either X or Y axis
	 * @param cush - position of cushion
	 * @param maxt - time beyond which equation of motion invalid
	 * @return reflected cushion collision event if it occurs within maxt
	 */
	public static Event collisionsWith(
			final Event e, 
			Function<Vector3D,Double> getAxis, 
			Function<Event,Event> reflect, 
			double cush, 
			double maxt)
	{	
		double A = getAxis.apply(e.getAccelerationVector())*0.5;
		double B = getAxis.apply(e.vel);
		double C = getAxis.apply(e.pos) - cush;

		double tCollision = Quadratic.getLeastPositiveRoot(A, B, C);
		
		if ((tCollision <= 0) || (tCollision>maxt))			
			return null;
		
		Function<Double,Boolean> onTable = new Function<Double,Boolean>()
		{
			@Override
			public Boolean apply(Double arg0) 
			{	
				return Cushion.onTable(e.advanceDelta(arg0));
			}			
		};
		
		tCollision = Quadratic.latestTrueTime(onTable,tCollision);

		assert(cush - getAxis.apply(e.pos) < 0.1);		
		assert(-0.1 < cush - getAxis.apply(e.pos));		
		return reflect.apply(e.advanceDelta(tCollision));
	}
	
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
			assert((cushx - collision.pos.getX() ) < 0.1);
		}
		
		return collision;
	}
	
	public static Event yCollisionsWith(Event e, double cushy, double maxt)
	{
		double A = e.getAccelerationVector().getY();
		double B = e.vel.getY();
		double C = e.pos.getY() - cushy;

		Event collision = getCollisionEvent(e,A,B,C,maxt);

		if (collision == null)
			return null;
		
		collision = latestEventYStillOnTableAtOrBeforeT(e,collision.t,cushy);

		// reflect in cushion
		if (collision != null)
		{
			collision.vel = new Vector3D(collision.vel.getX(),-collision.vel.getY(),0);
			collision.state = State.deriveStateOf(collision);
			collision.type = EventType.Cushion;
			assert((cushy - collision.pos.getY()) < 0.1);
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

	private static Event latestEventYStillOnTableAtOrBeforeT(final Event e, double t, final double cushion)
	{
		Function<Double,Double> func = new Function<Double, Double>() {
			
			@Override
			public Double apply(Double arg) {
				return e.advanceDelta(arg).pos.getY() - cushion;
			}
		};

		double last = Quadratic.optimise(func, t);
		
		if (last>0)
			return e.advanceDelta(last);
		
		return null;
	}



	private static Event getCollisionEvent(Event e, double A, double B, double C, double maxt)
	{
		double tCollision = Quadratic.getLeastPositiveRoot(A, B, C);

		if ((tCollision > 0) && (tCollision<maxt))
		{
			Event collision = e.advanceDelta(tCollision);
			collision.state = State.Sliding;
			collision.type = EventType.Cushion;

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
	

	public static boolean onTable(Event e)
	{
		return (e.pos.getX()<xp) && (e.pos.getX()>xn) && (e.pos.getY()<yp) && (e.pos.getY()>yn);
	}


}
