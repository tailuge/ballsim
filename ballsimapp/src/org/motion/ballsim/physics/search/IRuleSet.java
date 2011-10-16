package org.motion.ballsim.physics.search;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;

public interface IRuleSet 
{
	boolean valid(Table table);
	boolean scores(Table table, Ball ball);
	double rank(Table table, Ball ball);
}
