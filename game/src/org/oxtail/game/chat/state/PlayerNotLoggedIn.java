package org.oxtail.game.chat.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.event.GameEventAttribute;
import org.oxtail.game.model.Player;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

/**
 * State representing the Game starting, a player comes in and is paired off if
 * another is available
 */
@SuppressWarnings("rawtypes")
public class PlayerNotLoggedIn extends AbstractChatGameState {

	public PlayerNotLoggedIn(GameEventContext context) {
		super(context);
	}

	@Action
	public void login() {
		Player player = getInPlay();
		PlayerState.LoggedIn.set(player);
		notifyLoggedIn(player);
	}

	@Action
	public void logout() {
		Player player = getInPlay();
		PlayerState.LoggedOut.set(player);
		notifyLoggedOut(player);
	}

	private void notifyLoggedIn(Player player) {
		GameEvent event = new GameEvent();
		event.addAttribute(new GameEventAttribute("state", "loggedin"));
		player.onEvent(event);
	}

	private void notifyLoggedOut(Player player) {
		GameEvent event = new GameEvent();
		event.addAttribute(new GameEventAttribute("state", "loggedout"));
		player.onEvent(event);
	}

}
