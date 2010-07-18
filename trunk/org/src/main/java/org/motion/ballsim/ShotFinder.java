package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;

public class ShotFinder {

	private IRuleSet rule;

	private final Table table;
	
	public ShotFinder(IRuleSet rule, Table table)
	{
		this.table = table;
		this.rule = rule;
	}
	
	public Table FindBest(Ball ball, int maxIter)
	{
		// try 2pi/maxIter shots in a circle
		// should probably try for ball first variations
		
		Event best = null;
		double bestRank = 0;
		
		for(int i=0; i<maxIter; i++)
		{
			table.reset();
			
			Vector3D dir = new Vector3D(0.5+Math.PI/2.0+2*Math.PI*(double)i/(double)maxIter,0);
			Event e = ball.lastEvent(); 
			ball.lastEvent().vel = dir.scalarMultiply(280);
			ball.lastEvent().angularVel = dir.scalarMultiply(1);
			ball.lastEvent().state = State.Sliding;
			table.generateSequence();
			
			double rank = rule.rank(table, ball);
			
			if (rank == 1) 
				return table;

			if (rank > bestRank)
			{
				best = new Event(e);
				bestRank = rank;
			}
			
		}
		

		table.reset();
		if (best!=null)
		{
		ball.lastEvent().vel = best.vel;
		ball.lastEvent().state = State.Sliding;
		}
		table.generateSequence();

		return table;
	}
}
