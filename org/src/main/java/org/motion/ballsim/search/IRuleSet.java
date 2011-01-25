package org.motion.ballsim.search;

import org.motion.ballsim.motion.Ball;

public interface IRuleSet 
{
	boolean valid(Table table);
	boolean scores(Table table, Ball ball);
	double rank(Table table, Ball ball);
}
