package org.motion.ballsimapp.logic;


public interface IRuleSet 
{
	boolean valid(Table table);
	boolean scores(Table table, Ball ball);
	double rank(Table table, Ball ball);
}
