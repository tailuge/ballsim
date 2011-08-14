package org.oxtail.game.billiards.nineball.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.model.Player;
import org.oxtail.game.state.AbstractStatemachine;
import org.oxtail.game.state.GameEventContext;

public class NineBallStatemachine extends AbstractStatemachine {

	private GameHome gameHome;

	@Override
	public void execute(GameEvent gameEvent) {
		String action = gameEvent.getAction();
		String playerAlias = gameEvent.getEventFrom();
		Player player = gameHome.findPlayer(playerAlias);
		if (gameEvent.hasGameId()) {
			// executeForGame(gameEvent,player);
		} else {
			executeForPlayer(player, action);
		}
	}

	/**
	 * Execute the state action based on current player state
	 */
	private void executeForPlayer(Player player, String action) {
		GameEventContext context = new GameEventContext();
		context.setHome(gameHome);
		context.setInPlay(player);
		executeForState(player.getStateId(), action, context);
	}

}
