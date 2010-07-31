package org.motion.ballsimapp.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;




public class Table 
{
	
	public final static double froll = 10;
	public final static double fslide = 40;
	
	private Map<Integer,Ball> ballMap = new HashMap<Integer,Ball>();

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
		
		BallEvent nextCushion = Cushion.nextCushionHit(this, next.event.t);		
		if ((nextCushion != null) && (nextCushion.event.t < next.event.t))
			next = nextCushion;
			
		// use bounds of these to look for next ball/ball collision

		BallEventPair nextCollision = Collision.nextBallCollision(this, next.event.t);

		// add the soonest of these outcomes
		
		if ((nextCollision == null) || (next.event.t < nextCollision.first.event.t))
		{
			assert(Cushion.onTable(next.event));
			next.ball.add(next.event);
		}
		else
		{
			assert(Cushion.onTable(nextCollision.first.event));
			assert(Cushion.onTable(nextCollision.second.event));
			nextCollision.first.ball.add(nextCollision.first.event);
			nextCollision.second.ball.add(nextCollision.second.event);
		}
		
		
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
	
	public Table getClone()
	{
		Table t = new Table();
		
		return t;
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
