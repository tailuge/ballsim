package org.motion.ballsim.game;


public class Outcome {
	
	final BallSet firstBallHit;
	final int cushionsHitBeforeSecondObjectBall;
	final BallSet[] ballsPotted;

	public Outcome(BallSet first,int cushions,BallSet[] potted)
	{
		firstBallHit = first;
		cushionsHitBeforeSecondObjectBall = cushions;
		ballsPotted = potted;
	}
}
