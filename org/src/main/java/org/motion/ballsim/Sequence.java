package org.motion.ballsim;


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
		
		Event next = table.nextNatural();
		if (next == null)
			return false;
		
		// use bounds of this to look for next cushion collision
		
		Event nextCushion = table.nextCushionHit(next.t);		
		if ((nextCushion != null) && (nextCushion.t < next.t))
			next = nextCushion;
			
		// use bounds of these to look for next ball/ball collision


		assert(Cushion.onTable(next));

		next.ball.add(next);
		
		System.out.println(table);
		return true;
	}

}
