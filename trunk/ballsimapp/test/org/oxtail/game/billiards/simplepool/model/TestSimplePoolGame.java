package org.oxtail.game.billiards.simplepool.model;

import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.motion.ballsimapp.shared.GameEvent;
import org.oxtail.game.billiards.simplepool.state.GameEventToSimplePoolMove;
import org.oxtail.game.model.Player;
import org.oxtail.game.server.event.GameEventHelper;

public class TestSimplePoolGame {

	private SimplePoolGame game;

	private GameEventToSimplePoolMove gameEventToSimplePoolMove = new GameEventToSimplePoolMove();

	@Before
	public void before() {
		Player player1 = new Player("bob");
		Player player2 = new Player("jim");
		game = new SimplePoolGame(player1, player2);
		game.setInPlay(player1);
	}

	private GameEventHelper newShot() {
		GameEvent event = new GameEvent();
		GameEventHelper helper = new GameEventHelper(event);
		helper.setValue("action", "shot");
		return helper;
	}

	private GameEventHelper newShotWithBallPotted(String pot1,
			String... restPotted) {
		GameEventHelper helper = newShot();
		helper.setValues("game.shot.ballspotted", pot1, restPotted);
		return helper;
	}

	private SimplePoolGameState evaluateShot(GameEventHelper helper) {
		return game.evaluateShot(gameEventToSimplePoolMove.apply(helper
				.getEvent()));
	}

	@Test
	public void testFoulEvaluationForPotCueBallOnly() {
		assertEquals(SimplePoolGameState.Foul,
				evaluateShot(newShotWithBallPotted("0")));
	}

	@Test
	public void testFoulEvaluationForPotCueBallAndObjectBalls() {
		assertEquals(SimplePoolGameState.Foul,
				evaluateShot(newShotWithBallPotted("0", "1")));
		assertEquals(SimplePoolGameState.Foul,
				evaluateShot(newShotWithBallPotted("1", "0")));
		assertEquals(SimplePoolGameState.FoulGameOver,
				evaluateShot(newShotWithBallPotted("0", "1", "2")));
		assertEquals(SimplePoolGameState.FoulGameOver,
				evaluateShot(newShotWithBallPotted("1", "0", "2")));

	}


	
}
