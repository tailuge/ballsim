package org.oxtail.game.state;

import java.util.List;

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

	protected PlayerProxy player() {
		return new PlayerProxy(context.getInPlay());
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

}
