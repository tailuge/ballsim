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
	public Event nextNatural() 
	{
		Event next = null;
		for(Ball ball : balls)
		{
			Event e = ball.lastEvent();
			if (e.state == State.Stationary)
				continue;
			
			Event eNext = e.next();
			if ((next == null) || (eNext.t < next.t))
			{
				next = eNext;
				assert( next.t > e.t);
			}
		}

		return next;
	}

	public Event nextCushionHit(double maxt) 
	{
		Event next = null;
		for(Ball ball : balls)
		{
			Event e = ball.lastEvent();
			if (e.state == State.Stationary)
				continue;
			
			Event eCushion = Cushion.hit(e, maxt);
			if (eCushion == null)
				continue;

			if ((next == null) || (eCushion.t < next.t))
			{
				next = eCushion;
				assert(next.t > e.t);
				assert(Cushion.onTable(next));
			}		
		}		

		
		if ((next != null) && (next.t < maxt))
			return next;
		return null;
	}

	public EventPair nextBallCollision(double maxt) 
	{
		EventPair next = null;

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
				if (next == null)
					next = collision;
				if (collision.getFirst().t < next.getFirst().t)
					next = collision;

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
		
		Event next = nextNatural();
		if (next == null)
			return false;
		
		// use bounds of this to look for next cushion collision
		
		Event nextCushion = nextCushionHit(next.t);		
		if ((nextCushion != null) && (nextCushion.t < next.t))
			next = nextCushion;
			
		// use bounds of these to look for next ball/ball collision

		EventPair nextCollision = nextBallCollision(next.t);

		// add the soonest of these outcomes
		
		if ((nextCollision == null) || (next.t < nextCollision.getFirst().t))
		{
			logger.info("Single event");
			assert(Cushion.onTable(next));
			next.ball.add(next);
		}
		else
		{
			logger.info("Collision event: {}",nextCollision);
			logger.info("Collision event time: {}",nextCollision.getFirst().t);
			logger.info("Discarded single event: {}",next);
			logger.info("Discarded single event time: {}",next.t);
			logger.info("Collision 1: {}",nextCollision.getFirst().format());
			logger.info("Collision 2: {}",nextCollision.getSecond().format());
			assert(Cushion.onTable(nextCollision.getFirst()));
			assert(Cushion.onTable(nextCollision.getSecond()));
			nextCollision.getFirst().ball.add(nextCollision.getFirst());
			nextCollision.getSecond().ball.add(nextCollision.getSecond());
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
