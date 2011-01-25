package org.motion.ballsim.motion;


import org.motion.ballsim.gwtsafe.Function;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.search.Table;
import org.motion.ballsim.util.BallEvent;
import org.motion.ballsim.util.UtilVector3D;


/**
 * @author luke
 *
 * Static class that computes Event at intersection of ball with cushion
 */
public class Cushion 
{

	public final static double yp = 30*Ball.R;
	public final static double yn = -yp;
	public final static double xp = 15*Ball.R;
	public final static double xn = -xp;

	/**
	 * Collides with cushion when equation of motion intersects cushion
	 * 
	 * need to get the coefficients of quadratic who's solution is:
	 *  cush = pos0 + vel0*t + 1/2 acc * t^2
	 * 
	 * @param e - input event
	 * @param axis - unit vector of X or Y indicating perpendicular to cushion
	 * @param cush - position of cushion
	 * @param maxt - time beyond which equation of motion invalid
	 * @return reflected cushion collision event if it occurs within maxt
	 */
	private static Event hits(
			final Event e, 
			Vector3D axis, 
			Function<Double,Boolean> onTable, 
			double cush, 
			double maxt)
	{	
		double A = UtilVector3D.projectionOnAxis(e.acceleration(),axis)*0.5;
		double B = UtilVector3D.projectionOnAxis(e.vel,axis);
		double C = UtilVector3D.projectionOnAxis(e.pos,axis) - cush;

		double tCollision = org.motion.ballsim.gwtsafe.Quadratic.leastPositiveRoot(A, B, C);
		
		if ((tCollision <= 0) || (tCollision>maxt))			
			return null;
				
		assert( tCollision > 0);
		
		tCollision = org.motion.ballsim.gwtsafe.Quadratic.latestTrueTime(onTable, tCollision);
		
		assert( tCollision > 0);
		assert( tCollision < maxt);
		
		return reflect( e.advanceDelta(tCollision) , axis);
	}

	/**
	 * Given a ball at collision point determine its new state
	 * after reflection in the cushion
	 * 
	 * @param e
	 * @param axis
	 * @return
	 */
	private static Event reflect(Event e, Vector3D axis)
	{
		Event reflected = new Event(e);
		reflected.vel = UtilVector3D.reflectAlongAxis(e.vel, axis);
		reflected.state = State.deriveStateOf(reflected);
		reflected.type = EventType.Cushion;
		
		//Vector3D sideSpinAffectOnVel = Vector3D.crossProduct(axis,e.sidespin);
		
		return reflected;
	}

	/**
	 * Given an event determines if a cushion is hit before maxt
	 * and returns the new event representing the reflection in the cushion
     *
	 * @param e
	 * @param maxt
	 * @return
	 */
	public static Event hit(Event e, double maxt) 
	{		
		assert(Cushion.onTable(e));
		Event next = null;
		next = sooner(next,hits(e, Vector3D.PLUS_I, onX(e), xp, maxt));
		next = sooner(next,hits(e, Vector3D.PLUS_I, onX(e), xn, maxt));
		next = sooner(next,hits(e, Vector3D.PLUS_J, onY(e), yp, maxt));
		next = sooner(next,hits(e, Vector3D.PLUS_J, onY(e), yn, maxt));	
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
		public Boolean apply(Double arg) {
			return onTableY(e.advanceDelta(arg));
			}
		};
	}

	private static Function<Double,Boolean> onX(final Event e)
	{
		return new Function<Double,Boolean>() {
		public Boolean apply(Double arg) {
			return onTableX(e.advanceDelta(arg));
			}
		};
	}

	
	public static BallEvent nextCushionHit(Table table, double maxt) 
	{
		BallEvent next = null;
		for(Ball ball : table.balls())
		{
			Event e = ball.lastEvent();
			if (e.state == State.Stationary)
				continue;
			
			Event eCushion = Cushion.hit(e, maxt);
			if (eCushion == null)
				continue;

			if ((next == null) || (eCushion.t < next.event.t))
			{
				next = new BallEvent(ball,eCushion);
				assert(next.event.t > e.t);
				assert(Cushion.onTable(next.event));
			}		
		}		

		
		if ((next != null) && (next.event.t < maxt))
			return next;
		return null;
	}
	
	public static boolean validPosition(Table table) 
	{
		for(Ball a : table.balls())
		{
			if (!Cushion.onTable(a.lastEvent()))
				return false;
		}
		return true;
	}
	
}
