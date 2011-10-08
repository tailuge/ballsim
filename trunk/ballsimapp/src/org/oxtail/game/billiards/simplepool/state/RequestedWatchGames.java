package org.oxtail.game.billiards.simplepool.state;

import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.model.Game;
import org.oxtail.game.model.Player;
import org.oxtail.game.server.event.GameEventHelper;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

public class RequestedWatchGames extends AbstractSimplePoolGameState {

	public RequestedWatchGames(
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context) {
		super(context);
	}

	@Action
	public void watchGame() {
		Player player = getInPlay();
		PlayerState.WatchingGame.set(player);
		GameEventHelper playerAttributes = new GameEventHelper(player.getPlayerAttributes());
		GameEventHelper inEvent = new GameEventHelper(getGameEvent());
		
		String watchId = inEvent.getString("game.watch.id");
		playerAttributes.setValue("game.watch.id",watchId);
		
		Game<?> game = getGameHome().findGame(watchId);
		GameEventHelper gameEvent = new GameEventHelper(game.getGameEvent());
		//
		GameEventHelper outEvent = new GameEventHelper(newStateEvent("watchinggame"));
		outEvent.setValue("game.watch.id", watchId);
		outEvent.setValue("game.table.state",gameEvent.getString("game.table.state"));
		//
		player.onEvent(outEvent.getEvent());
	}

	/**
	 * Invalid login event, move back to be logged in
	 */
	@Action
	public void login() {
		log.warning("Invalid action login received moving back to loggedin");
		forceToLogin(getInPlay());
	}

}
