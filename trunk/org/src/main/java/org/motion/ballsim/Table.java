package org.motion.ballsim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Table 
{
	private final static Logger logger = LoggerFactory.getLogger(Table.class);
	
	public final static double froll = 10;
	public final static double fslide = 40;
	
	public List<Ball> balls = new ArrayList<Ball>();

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
		for(Ball ball : balls)
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

	public BallEvent nextCushionHit(double maxt) 
	{
		BallEvent next = null;
		for(Ball ball : balls)
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

	public BallEventPair nextBallCollision(double maxt) 
	{
		BallEventPair next = null;

		Collection<Ball> tested = new HashSet<Ball>();
		
		for(Ball a : balls)
		{
			tested.add(a);
			for(Ball b : balls)
			{
				if (tested.contains(b)) continue;
				
				EventPair collision = Collision.get(a.lastEvent(), b.lastEvent(), maxt);

				if (collision == null)
					continue;
				if ((next == null) || (collision.first.t < next.first.event.t))
					next = new BallEventPair(a,collision.first,b,collision.second);

			}
		}
		return next;
	}
	
	public String toString()
	{	
		return balls.toString();
	}

	public Collection<Event> getAllEvents() 
	{
		Collection<Event> all = new ArrayList<Event>();

		for(Ball ball : balls)
		{
			all.addAll(ball.getAllEvents());
		}
		
		return all;
	}

	public Collection<BallEvent> getAllBallEvents() 
	{
		Collection<BallEvent> all = new ArrayList<BallEvent>();

		for(Ball ball : balls)
		{
			for(Event e : ball.getAllEvents())
			{
				all.add(new BallEvent(ball,e));
			}
		}
		
		return all;
	}

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
		
		BallEvent next = nextNatural();
		if (next == null)
			return false;
		
		// use bounds of this to look for next cushion collision
		
		BallEvent nextCushion = nextCushionHit(next.event.t);		
		if ((nextCushion != null) && (nextCushion.event.t < next.event.t))
			next = nextCushion;
			
		// use bounds of these to look for next ball/ball collision

		BallEventPair nextCollision = nextBallCollision(next.event.t);

		// add the soonest of these outcomes
		
		if ((nextCollision == null) || (next.event.t < nextCollision.first.event.t))
		{
			logger.info("Single event");
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
		
		System.out.println(">");
		System.out.println(this);
		System.out.println("<");
		return true;
	}

	public boolean validStartingPosition() 
	{
		Collection<Ball> tested = new HashSet<Ball>();
		
		for(Ball a : balls)
		{
			tested.add(a);

			if (!Cushion.onTable(a.lastEvent()))
				return false;
			
			for(Ball b : balls)
			{
				if (tested.contains(b)) continue;
				
				if (Collision.startingSeperation(a.lastEvent(),b.lastEvent())<2*Ball.R)
					return false;
					
			}
		}
		return true;
	}
}
