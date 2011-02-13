package org.motion.ballsim.game;

public interface IGameRule {
	
	GameState initial();
	GameState next(GameState current, Outcome outcome);

}
