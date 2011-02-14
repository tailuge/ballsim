package org.motion.ballsim.game;

public class NineBallRules implements IGameRule {

	private GameState current;
	private Outcome outcome;
		
	public GameState initial() {
		
		return null;
	}

	public GameState next(GameState current_, Outcome outcome_) 
	{
		current = current_;
		outcome = outcome_;
	
		
		return current;
	}

	public boolean isLegalOutcome()
	{
		// is the correct first ball hit
		
		if (current.p1.target != outcome.firstBallHit)
			return false;

		// is cue ball still on table
		
		return true;
	}
}
