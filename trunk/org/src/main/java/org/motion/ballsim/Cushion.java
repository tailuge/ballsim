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
	 * Collides with cushion when equation of motion intersects cushion
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
			Function<Vector3D,Vector3D> reflect, 
			Function<Double,Boolean> onTable, 
			double cush, 
			double maxt)
	{	
		double A = getAxis.apply(e.getAccelerationVector())*0.5;
		double B = getAxis.apply(e.vel);
		double C = getAxis.apply(e.pos) - cush;

		double tCollision = Quadratic.getLeastPositiveRoot(A, B, C);
		
		if ((tCollision <= 0) || (tCollision>maxt))			
			return null;
				
		assert( tCollision > 0);
		
		tCollision = Quadratic.latestTrueTime(onTable,tCollision);

		assert( tCollision > 0);
		assert( tCollision < maxt);
		
		Event reflected = e.advanceDelta(tCollision);
		reflected.vel = reflect.apply(reflected.vel);
		reflected.state = State.deriveStateOf(reflected);
		reflected.type = EventType.Cushion;

//		assert(cush - getAxis.apply(reflected.pos) < 0.1);		
//		assert(-0.1 < cush - getAxis.apply(reflected.pos));		

		return reflected;
	}
	
	public static Event getNext(Event e, double maxt) 
	{		
		System.out.println(e);
		Event next = null;
		next = updateIfSooner(next,collisionsWith(e, UtilVector3D.getX, UtilVector3D.reflectX, getOnTableX(e), xp, maxt));
		next = updateIfSooner(next,collisionsWith(e, UtilVector3D.getX, UtilVector3D.reflectX, getOnTableX(e), xn, maxt));
		next = updateIfSooner(next,collisionsWith(e, UtilVector3D.getY, UtilVector3D.reflectY, getOnTableY(e), yp, maxt));
		next = updateIfSooner(next,collisionsWith(e, UtilVector3D.getY, UtilVector3D.reflectY, getOnTableY(e), yn, maxt));	
		return next;
	}
	
	private static Event updateIfSooner(Event current, Event proposed)
	{
		if ((proposed==null) || (proposed.t < 0))
			return current;		
		if (current==null)
			return proposed;		
		if (proposed.t < current.t)
			return proposed;
		
		return current;
	}

	public static boolean onTable(Event e)
	{
		return onTableX(e) && onTableY(e); 
	}

	public static boolean onTableX(Event e)
	{
		return (e.pos.getX()<xp) && (e.pos.getX()>xn); 
	}

	public static boolean onTableY(Event e)
	{
		return (e.pos.getY()<yp) && (e.pos.getY()>yn); 
	}

	public static Function<Double,Boolean> getOnTableY(final Event e)
	{
		return new Function<Double,Boolean>() {
		@Override
		public Boolean apply(Double arg) {
			return onTableY(e.advanceDelta(arg));
			}
		};
	}

	public static Function<Double,Boolean> getOnTableX(final Event e)
	{
		return new Function<Double,Boolean>() {
		@Override
		public Boolean apply(Double arg) {
			return onTableX(e.advanceDelta(arg));
			}
		};
	}

}
