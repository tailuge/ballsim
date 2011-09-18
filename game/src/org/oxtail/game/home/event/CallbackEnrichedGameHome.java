package org.oxtail.game.home.event;

import org.oxtail.game.event.GameEventCallback;
import org.oxtail.game.home.ForwardingGameHome;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.model.Player;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Delegates a GameHome and provides player enrichment with the
 * {@link GameEventCallback}
 */
public class CallbackEnrichedGameHome extends ForwardingGameHome implements
		Function<Player, Player> {

	private final GameHome delegate;
	private final GameEventCallback callback;

	public CallbackEnrichedGameHome(GameHome delegate,
			GameEventCallback callback) {
		this.delegate = delegate;
		this.callback = callback;
	}

	@Override
	protected GameHome delegate() {
		return delegate;
	}

	@Override
	public Player findPlayer(String playerAlias) {
		return apply(super.findPlayer(playerAlias));
	}

	@Override
	public Iterable<Player> findPlayers(Predicate<Player> playerFilter) {
		return Iterables.transform(super.findPlayers(playerFilter), this);
	}

	@Override
	public Player apply(Player player) {
		player.setCallbackDelegate(callback);
		return player;
	}

}
