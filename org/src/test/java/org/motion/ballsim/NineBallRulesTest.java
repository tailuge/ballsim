package org.motion.ballsim;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.motion.ballsim.game.BallSet;
import org.motion.ballsim.game.GameState;
import org.motion.ballsim.game.IGameRule;
import org.motion.ballsim.game.NineBallRules;
import org.motion.ballsim.game.NineBallSet;
import org.motion.ballsim.game.Outcome;
import org.motion.ballsim.game.PlayerActivity;
import org.motion.ballsim.game.PlayerState;


public class NineBallRulesTest {
	
	
	@Test
	public final void testPotProgression()
	{
		// player 1 to pot the 1 ball
		GameState current = typicalStartState();
		
		// outcome of shot was potted ball 		
		Outcome outcome = new Outcome(NineBallSet.ONEBALL,0,new BallSet[]{NineBallSet.ONEBALL});

		// get next state
		IGameRule nineBall = new NineBallRules();
		GameState next = nineBall.next(current, outcome);
		
		// assertions e.g. same player, next target, etc. 
		assertNotNull("GameState ok",next);		
	}
	
	private final GameState typicalStartState()
	{
		// player 1 to pot the 1 ball
		PlayerState p1 = new PlayerState(PlayerActivity.Aiming,NineBallSet.CUEBALL,NineBallSet.ONEBALL);
		PlayerState p2 = new PlayerState(PlayerActivity.Watching,NineBallSet.CUEBALL,NineBallSet.ONEBALL);
		return new GameState(p1,p2);		
	}
	
}
