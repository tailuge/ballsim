package org.oxtail.game.state;

import java.util.List;

import org.oxtail.game.home.GameHome;
import org.oxtail.game.model.Game;
import org.oxtail.game.model.Move;
import org.oxtail.game.model.Player;
import org.oxtail.game.model.PlayingSpace;
import org.oxtail.game.model.StateId;

import com.google.common.collect.Lists;

/**
 * Base state for Games and Players
 * 
 * @author liam knox
 */
public abstract class AbstractGameState<G extends Game<S>, M extends Move, S extends PlayingSpace> {

	protected final GameEventContext<G, M, S> context;
	
	public AbstractGameState(GameEventContext<G, M, S> context) {
		this.context = context;
	}

	protected StateId getStateId() {
		return new StateId(getClass());
	}

	/** Called by the state machine after the state execution is performed */
	protected void afterStateExecution() {

	}

	protected Player getInPlay() {
		return context.getInPlay();
	}
	
	protected PlayerProxy inPlay() {
		return new PlayerProxy(context.getInPlay());
	}

	protected PlayerProxy notInPlay() {
		return new PlayerProxy(context.getNotInPlay());
	}
	
	private List<PlayerProxy> toProxies(Iterable<Player> players) {
		List<PlayerProxy> proxies = Lists.newArrayList();
		for (Player p : players)
			proxies.add(new PlayerProxy(p));
		return proxies;
	}

	protected PlayersProxy others() {
		return new PlayersProxy(toProxies(context.getOthers()));
	}

	protected G getGame() {
		return context.getGame();
	}
	
	protected M getMove() {
		return context.getMove();
	}

	protected GameStatemachine getStatemachine() {
		return context.getStatemachine();
	}

	protected GameHome getGameHome() {
		return context.getGameHome();
	}

	
}
