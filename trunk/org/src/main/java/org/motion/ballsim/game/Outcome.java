package org.motion.ballsim.game;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.physics.Table;


public class Outcome {
	
	final public BallSet firstBallHit;
	final public List<BallSet> ballsPotted = new ArrayList<BallSet>() ;
	final public int cushionsHitBeforeSecondObjectBall;

	public Outcome(BallSet first,List<BallSet> potted,int cushions)
	{
		firstBallHit = first;
		ballsPotted.addAll(potted);
		cushionsHitBeforeSecondObjectBall = cushions;
	}
	
	// not for here
	public Outcome(Table table)
	{
		firstBallHit = NineBallSet.ONEBALL;
		cushionsHitBeforeSecondObjectBall = 0;		
	}
}
