package org.oxtail.game.billiards.nineball.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.model.Player;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.AbstractStatemachine;
import org.oxtail.game.state.GameEventContext;

public class NineBallStatemachine extends AbstractStatemachine {

	private GameHome gameHome;
	
	@Override
	public void execute(GameEvent gameEvent) {
//		NineBallAction action = NineBallAction.valueOf(gameEvent.getAction());
//		String playerAlias = gameEvent.getEventFrom();
//		Player player = gameHome.findPlayer(playerAlias);
//		if (gameEvent.hasGameId()) {
//	//		executeForGame(gameEvent,player);
//		}
//		else {
//			executeForPlayer(player);
//		}
	}

	private void executeForPlayer(Player player) {
		GameEventContext context = new GameEventContext();
		context.setHome(gameHome);
		context.setInPlay(player);
		
	}
	
	
	
}
