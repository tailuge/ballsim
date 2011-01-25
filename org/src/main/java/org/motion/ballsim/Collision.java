package org.motion.ballsim;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;


import org.motion.ballsim.gwtsafe.Function;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.motion.Event;
import org.motion.ballsim.motion.EventPair;
import org.motion.ballsim.motion.EventType;
import org.motion.ballsim.motion.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Collision 
{

	private final static Logger logger = LoggerFactory.getLogger(Collision.class);

	public static double[] quarticCoefficients(Event e1, Event e2)
	{
		// distance between two balls at collision is dx^2 + dy^2 = r^2
		// dx = e1.x - e2.x
		// coefficients of quadratic of dx in time
		
		double a = e1.acceleration().getX()*0.5 - e2.acceleration().getX()*0.5;
		double b = e1.vel.getX() - e2.vel.getX();
		double c = e1.pos.getX() - e2.pos.getX();

		// dx^2 gives following quartic coefficients
		
		double t4 = a*a;
		double t3 = 2*a*b;
		double t2 = 2*a*c + b*b;
		double t1 = 2*b*c;
		double t0 = c*c;
		
		// dy^2
		
		a = e1.acceleration().getY()*0.5 - e2.acceleration().getY()*0.5;
		b = e1.vel.getY() - e2.vel.getY();
		c = e1.pos.getY() - e2.pos.getY();

		// add these to give dx^2+dy^2
		
		t4 += a*a;
		t3 += 2*a*b;
		t2 += 2*a*c + b*b;
		t1 += 2*b*c;
		t0 += c*c;

		// subtract r^2
		
		t0 -= 4*Ball.R*Ball.R;
		
		// solve for roots giving zero. pick least +ve
				
		double[] coeffs = { t0, t1, t2, t3, t4 };
		return coeffs;
	}
	
	static double collisionTime(Event e1, Event e2, double maxt)
	{

		double coeffs[] = quarticCoefficients(e1, e2);
	    double root = org.motion.ballsim.gwtsafe.Quartic.smallestRoot( coeffs , maxt); 
	       
		// optimise
				
	    logger.info("quartic coeffs      :{}",Arrays.toString(coeffs));
	    logger.info("quartic             :{}",org.motion.ballsim.gwtsafe.Quartic.print(coeffs));
		logger.info("1st root            :{}",root);
		logger.info("quartic eval at root:{}",org.motion.ballsim.gwtsafe.Quartic.evalAt(coeffs,root));
		logger.info("seperation at root  :{}",startingSeperation(e1.advanceDelta(root),e2.advanceDelta(root)));
		
		return latestInstantBeforeCollision(e1,e2,root);
	}
	
	
	
	static EventPair collisionEvents(Event a, Event b, double t)
	{
		EventPair result = new EventPair(a.advanceDelta(t),b.advanceDelta(t));

		Event ca = result.first;
		Event cb = result.second;
		
		Vector3D collisionAxis = ca.pos.subtract(cb.pos).normalize();
		
		Vector3D av = ca.vel;
		Vector3D bv = cb.vel;
	
		Vector3D relativeVel = av.subtract(bv);
		
		Vector3D dv = collisionAxis.scalarMultiply(Vector3D.dotProduct(collisionAxis, relativeVel));
		
		ca.vel = ca.vel.subtract(dv);
		cb.vel = cb.vel.add(dv);
		
		ca.state = State.deriveStateOf(ca);
		cb.state = State.deriveStateOf(cb);
		
		ca.type = EventType.Collision;
		cb.type = EventType.Collision;
		
		return result;
	}
	
	
	static double startingSeperation(Event e1, Event e2)
	{
		return Vector3D.distance(e1.pos, e2.pos) - 2*Ball.R;
	}

	static double seperationAt(Event e1, Event e2,double t)
	{
		return Vector3D.distance(e1.advanceDelta(t-e1.t).pos, e2.advanceDelta(t-e2.t).pos) - 2*Ball.R;
	}

	private static double latestInstantBeforeCollision(final Event a,final Event b,double tCollision)
	{
		Function<Double,Double> func = new Function<Double, Double>() 
		{	
			public Double apply(Double arg) {
				return startingSeperation(a.advanceDelta(arg),b.advanceDelta(arg));
			}
		};

		double last = org.motion.ballsim.gwtsafe.Quadratic.optimise(func, tCollision);
		
		if (last>0)
			return last;
		
		return 0;
	}

	public static EventPair get(Event e1, Event e2, double maxt) 
	{

		if (e1.t < e2.t)
			e1 = e1.advanceDelta(e2.t-e1.t);
		else if (e2.t < e1.t)
			e2 = e2.advanceDelta(e1.t-e2.t);
		
		double tCol = collisionTime(e1, e2, maxt);
		
		logger.info("Collision 1: {}",e1.format());
		logger.info("Collision 2: {}",e2.format());

		logger.info("Collision time: {}",tCol);

		assert(Collision.startingSeperation(e1,e2) > 0);
		
		if (tCol > 0)
		{
			EventPair col = collisionEvents(e1,e2,tCol);
			assert(Collision.startingSeperation(col.first,col.second) > 0);			
			return col;
		}
		return null;
	}
	
	public static BallEventPair nextBallCollision(Table table, double maxt) 
	{
		BallEventPair next = null;

		Collection<Ball> tested = new HashSet<Ball>();
		
		for(Ball a : table.balls())
		{
			tested.add(a);
			for(Ball b : table.balls())
			{
				if (tested.contains(b)) continue;
				
				EventPair collision = Collision.get(a.lastEvent(), b.lastEvent(), maxt);

				if (collision == null)
					continue;
				if ((next == null) || (collision.first.t < next.first.event.t))
				{
					collision.first.otherBallId = collision.second.ballId;
					collision.second.otherBallId = collision.first.ballId;					
					next = new BallEventPair(a,collision.first,b,collision.second);
					assert(collision.first.otherBallId != 0);
					assert(collision.second.otherBallId != 0);
				}
			}
		}
		
		
		return next;
	}


	
	public static boolean validPosition(Table table) 
	{
		double maxTime = table.getMaxTime();
		
		Collection<Ball> tested = new HashSet<Ball>();
		
		for(Ball a : table.balls())
		{
			tested.add(a);
			
			for(Ball b : table.balls())
			{
				if (tested.contains(b)) continue;
				
				if (Collision.seperationAt(a.lastEvent(),b.lastEvent(),maxTime) < 0)
					return false;
					
			}
		}
		return true;
	}
}
