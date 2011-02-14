package org.motion.ballsim.game;

import java.util.ArrayList;
import java.util.List;


public class Outcome {
	
	final BallSet firstBallHit;
	final List<BallSet> ballsPotted = new ArrayList<BallSet>() ;
	final int cushionsHitBeforeSecondObjectBall;

	public Outcome(BallSet first,List<BallSet> potted,int cushions)
	{
		firstBallHit = first;
		ballsPotted.addAll(potted);
		cushionsHitBeforeSecondObjectBall = cushions;
	}
}
