package org.oxtail.game.billiards.simplepool.model;

import static org.oxtail.game.billiards.simplepool.model.SimplePoolGameState.GameOver;
import static org.oxtail.game.billiards.simplepool.model.SimplePoolGameState.TurnContinued;
import static org.oxtail.game.billiards.simplepool.model.SimplePoolGameState.TurnOver;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.MoveEvaluator;

/**
 * Move evaluator where balls are never replaced on fouls
 */
public class NoBallReplaceMoverEvaluator
		implements
		MoveEvaluator<SimplePoolGameState, Game<SimplePoolTable>, SimplePoolTable, SimplePoolMove> {

	@Override
	public SimplePoolGameState evaluate(Game<SimplePoolTable> game,
			SimplePoolMove shot) {
		SimplePoolGameState gameState = TurnOver;
		if (shot.somethingPotted())
			gameState = game.getCurrentPlayingSpace().isBallsLeftOnTable() ? TurnContinued
					: GameOver;
		return shot.isFoul() ? gameState.forFoul() : gameState;
	}

}
