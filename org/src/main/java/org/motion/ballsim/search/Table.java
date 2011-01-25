package org.motion.ballsim.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.motion.ballsim.motion.Ball;
import org.motion.ballsim.motion.Collision;
import org.motion.ballsim.motion.Cushion;
import org.motion.ballsim.motion.Event;
import org.motion.ballsim.motion.State;
import org.motion.ballsim.util.BallEvent;
import org.motion.ballsim.util.BallEventPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * @author august
 *
 * Table holds a list of balls. By calling the generate method all future events
 * for these balls are computed. This allows an initial position to be evaluated
 * to the point all balls are at rest.
 * 
 */
public class Table 
{
	private final static Logger logger = LoggerFactory.getLogger(Table.class);
	
	public final static double froll = 10;
	public final static double fslide = 40;
	
	private Map<Integer,Ball> ballMap = Maps.newHashMap();

	/**
	 * Given all balls that are on the table
	 * check each to see when the next rolling or
	 * stationary transition will occur
	 * 
	 * @return
	 */
	public BallEvent nextNatural() 
	{
		BallEvent next = null;
		for(Ball ball : balls())
		{
			Event e = ball.lastEvent();
			if (e.state == State.Stationary)
				continue;
			
			Event eNext = e.next();
			if ((next == null) || (eNext.t < next.event.t))
			{
				next = new BallEvent(ball, eNext);
				assert( next.event.t > e.t);
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

	public Collection<BallEvent> getAllBallEvents() 
	{
		Collection<BallEvent> all = new ArrayList<BallEvent>();

		for(Ball ball : balls())
		{
			for(Event e : ball.getAllEvents())
			{
				all.add(new BallEvent(ball,e));
			}
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
				
		BallEvent next = nextNatural();
		if (next == null)
			return false;
		
		// use bounds of this to look for next cushion collision
		
		BallEvent nextCushion = Cushion.nextCushionHit(this, next.event.t);		
		if ((nextCushion != null) && (nextCushion.event.t < next.event.t))
			next = nextCushion;
			
		// use bounds of these to look for next ball/ball collision

		BallEventPair nextCollision = Collision.nextBallCollision(this, next.event.t);

		// add the soonest of these outcomes
		
		if ((nextCollision == null) || (next.event.t < nextCollision.first.event.t))
		{
			logger.info("Single event");
			logger.info("Ball {} : {}", next.ball.id, next.event.format());
			logger.info("nextCollision {}",nextCollision);
			assert(Cushion.onTable(next.event));
			next.ball.add(next.event);
		}
		else
		{
			logger.info("Collision event: {}",nextCollision);
			logger.info("Collision event time: {}",nextCollision.first.event.t);
			logger.info("Discarded single event: {}",next);
			logger.info("Discarded single event time: {}",next.event.t);
			logger.info("Collision 1: {}",nextCollision.first.event.format());
			logger.info("Collision 2: {}",nextCollision.second.event.format());
			assert(Cushion.onTable(nextCollision.first.event));
			assert(Cushion.onTable(nextCollision.second.event));
			nextCollision.first.ball.add(nextCollision.first.event);
			nextCollision.second.ball.add(nextCollision.second.event);
		}
		
		logger.info("Table:{}",this);
		
		assert(Cushion.validPosition(this));
		assert(Collision.validPosition(this));
		
		return true;
	}


	public void reset()
	{
		for(Ball ball : balls())
		{
			ball.resetToFirst();
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
