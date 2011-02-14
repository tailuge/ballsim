package org.motion.ballsim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.motion.ballsim.game.Activity;
import org.motion.ballsim.game.BallSet;
import org.motion.ballsim.game.GameState;
import org.motion.ballsim.game.IGameRule;
import org.motion.ballsim.game.NineBallRules;
import org.motion.ballsim.game.NineBallSet;
import org.motion.ballsim.game.Outcome;
import org.motion.ballsim.game.PlayerState;


public class NineBallRulesTest {
	
	
	@Test
	public final void testPotProgression()
	{
		// player 1 to pot the 1 ball
		GameState current = typicalStartState();
		
		// outcome of shot was potted ball 		
		Outcome outcome = new Outcome(NineBallSet.ONEBALL,potOf(NineBallSet.ONEBALL),0);

		// get next state
		IGameRule nineBall = new NineBallRules();
		GameState next = nineBall.next(current, outcome);
		
		// assertions e.g. same player, next target, etc. 
		assertNotNull("GameState ok",next);		
		assertEquals("p1 still aiming",Activity.Aiming,next.p1.activity);
	}
	
	private final GameState typicalStartState()
	{
		// player 1 to pot the 1 ball
		PlayerState p1 = new PlayerState(Activity.Aiming,NineBallSet.CUEBALL,NineBallSet.ONEBALL);
		PlayerState p2 = new PlayerState(Activity.Watching,NineBallSet.CUEBALL,NineBallSet.ONEBALL);
		return new GameState(p1,p2);		
	}
	
	private final List<BallSet> potOf(BallSet ball)
	{
		return new ArrayList<BallSet>(Arrays.asList(ball));
	}
	
}
