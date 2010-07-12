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
	private static Event hits(
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

		double tCollision = Quadratic.leastPositiveRoot(A, B, C);
		
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

		return reflected;
	}
	
	public static Event hit(Event e, double maxt) 
	{		
		System.out.println(e);
		Event next = null;
		next = sooner(next,hits(e, UtilVector3D.getX, UtilVector3D.reflectX, onX(e), xp, maxt));
		next = sooner(next,hits(e, UtilVector3D.getX, UtilVector3D.reflectX, onX(e), xn, maxt));
		next = sooner(next,hits(e, UtilVector3D.getY, UtilVector3D.reflectY, onY(e), yp, maxt));
		next = sooner(next,hits(e, UtilVector3D.getY, UtilVector3D.reflectY, onY(e), yn, maxt));	
		if(next!=null)
			System.out.println("t:"+next.t+" t-e:"+(next.t-e.t));
		assert((next==null) || Cushion.onTable(next));
		return next;
	}
	
	private static Event sooner(Event current, Event proposed)
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

	private static boolean onTableX(Event e)
	{
		return (e.pos.getX()<xp) && (e.pos.getX()>xn); 
	}

	private static boolean onTableY(Event e)
	{
		return (e.pos.getY()<yp) && (e.pos.getY()>yn); 
	}

	private static Function<Double,Boolean> onY(final Event e)
	{
		return new Function<Double,Boolean>() {
		@Override
		public Boolean apply(Double arg) {
			return onTableY(e.advanceDelta(arg));
			}
		};
	}

	private static Function<Double,Boolean> onX(final Event e)
	{
		return new Function<Double,Boolean>() {
		@Override
		public Boolean apply(Double arg) {
			return onTableX(e.advanceDelta(arg));
			}
		};
	}

}
