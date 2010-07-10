package org.motion.ballsim;

import java.util.Map.Entry;

/**
 * @author luke
 * 
 * Given an initial state (table), updates the table with 
 * all subsequent events until no more would occur.
 *
 */
public class Sequence 
{

	private Table table;
	
	public Sequence(Table table_)
	{
		table = table_;
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
		
		EventBallMap nextNatural = table.nextNatural();
		EventBallMap next = nextNatural;
		
		// use bounds of this to look for next cushion collision
		
//		EventBallMap nextCushion;
		
		// use bounds of these to look for next ball/ball collision

//		EventBallMap nextCollision;

		
		if (next.isEmpty())
			return false;
		
		for(Entry<Event,Ball> entry:next.entrySet())
		{
			entry.getValue().getEvents().add(entry.getKey());
		}
		return true;
	}

}
