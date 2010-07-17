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
		// try 360/maxIter shots
		
		for(int i=0; i<maxIter; i++)
		{
			Vector3D dir = new Vector3D(Math.PI*(double)i/(double)maxIter,0);
			ball.lastEvent().vel = dir.scalarMultiply(150);
			if (rule.rank(table, ball) > 0)
				return table;
		}
		
		
		return table;
	}
}
