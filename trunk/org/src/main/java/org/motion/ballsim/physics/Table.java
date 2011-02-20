package org.motion.ballsim.physics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.motion.ballsim.gwtsafe.Vector3D;

import org.motion.ballsim.util.Assert;
import org.motion.ballsim.util.Logger;


/**
 * @author luke
 *
 * Table holds a list of balls. By calling the generate method all future events
 * for these balls are computed. This allows an initial position to be evaluated
 * to the point all balls are at rest.
 * 
 */
public class Table 
{
	private final static Logger logger = new Logger("Table",false);
	
	public static double maxVel = 60.0;
	public static double maxAngVel = 2.0;
	public static double accelRoll = -0.8;
	public static double accelSlide = -15.0;

	private Map<Integer,Ball> ballMap = new HashMap<Integer,Ball>();

	public boolean hasPockets;
	
	public Table()
	{
		hasPockets = false;
	}
	
	public Table(boolean hasPockets_)
	{
		hasPockets = hasPockets_;
	}
	
	/**
	 * Given all balls that are on the table
	 * check each to see when the next rolling or
	 * stationary transition will occur
	 * 
	 * @return
	 */
	public Event nextNatural() 
	{
		Event next = null;
		for(Ball ball : balls())
		{
			Event e = ball.lastEvent();
			if (e.state.isMotionlessEndState())
				continue;
			
			Event eNext = e.next();
			if ((next == null) || (eNext.t < next.t))
			{
				next = eNext;
				Assert.isTrue( next.t > e.t);
			}
		}

		return next;
	}




	
	public String toString()
	{	
		return balls().toString();
	}

	public Collection<Event> getAllEvents() 
	{
		Collection<Event> all = new ArrayList<Event>();

		for(Ball ball : balls())
		{
			all.addAll(ball.getAllEvents());
		}
		
		return all;
	}


	/**
	 *  given a table with balls (possibly in motion) this method
	 *  expands the event list until all balls are at rest.
	 *  
	 */
	public int generateSequence()
	{
		int count=0;
		
		while (generateNext())
			count++;
		return count;
	}
	
	/**
	 * Compare times of all possible next events and
	 * add only the first to occur in the time line.
	 * 
	 * @return true if there was a new event added
	 */
	public boolean generateNext()
	{
		// next natural event
		logger.info("Initial conditions for iteration:");
		for(Ball ball : balls())
		{
			logger.info("Ball {} : {}", ball.id,ball.lastEvent().format());
		}		
				
		Event next = nextNatural();
		if (next == null)
			return false;
		
		// use bounds of this to look for next cushion collision
		
		Event nextCushion = Cushion.nextCushionHit(this, next.t);		
		if ((nextCushion != null) && (nextCushion.t < next.t))
			next = nextCushion;
		
		// use bounds of this to look for pocket collisions 
		
		if (hasPockets)
		{
			Event nextKnuckle = Pocket.nextKnuckleCollision(this, next.t);
			if ((nextKnuckle != null) && (nextKnuckle.t < next.t))
				next = nextKnuckle;
			
			Event nextPocket = Pocket.nextPot(this, next.t);
			if ((nextPocket != null) && (nextPocket.t < next.t))
				next = nextPocket;			
		}
		
		// use bounds of these to look for next ball/ball collision

		EventPair nextCollision = Collision.nextBallCollision(this, next.t);

		// add the soonest of these outcomes
		
		if ((nextCollision == null) || (next.t < nextCollision.first.t))
		{
			logger.info("Single event");
			logger.info("Ball {} : {}", next.ballId, next.format());
			logger.info("nextCollision {}",nextCollision);
			Assert.isTrue(Cushion.onTable(next));
			this.ball(next.ballId).add(next);
		}
		else
		{
			logger.info("Collision event: {}",nextCollision);
			logger.info("Collision event time: {}",nextCollision.first.t);
			logger.info("Discarded single event: {}",next);
			logger.info("Discarded single event time: {}",next.t);
			logger.info("Collision 1: {}",nextCollision.first.format());
			logger.info("Collision 2: {}",nextCollision.second.format());
			Assert.isTrue(Cushion.onTable(nextCollision.first));
			Assert.isTrue(Cushion.onTable(nextCollision.second));
			this.ball(nextCollision.first.ballId).add(nextCollision.first);
			this.ball(nextCollision.second.ballId).add(nextCollision.second);
		}
		
		logger.info("Table:{}",this);
		
		Assert.isTrue(Cushion.validPosition(this));
		Assert.isTrue(Collision.validPosition(this));
		
		return true;
	}


	public void reset()
	{
		for(Ball ball : balls())
		{
			ball.resetToFirst();
		}
	}

	public void resetToCurrent(double t)
	{
		for(Ball ball : balls())
		{
			Event e = Interpolator.interpolate(ball, t);
			
			e.t=0;
			e.vel=Vector3D.ZERO;
			e.angularVel=Vector3D.ZERO;
			e.state = State.Stationary;
			
			ball.setFirstEvent(e);
		}
	}

	public double getMaxTime() 
	{
		double latest = 0;
		for(Ball a : balls())
		{
			if (a.lastEvent().t > latest)
				latest = a.lastEvent().t; 
		}		
		return latest;
	}
	

	public Collection<Ball> balls()
	{
		return ballMap.values();
	}
	
	public Ball ball(int id)
	{
		if (!ballMap.containsKey(id))
			ballMap.put(id, new Ball(id));
			
		return ballMap.get(id);
	}
}
