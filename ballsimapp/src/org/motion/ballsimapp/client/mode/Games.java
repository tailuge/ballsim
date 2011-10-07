package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.GAME_LIST;
import static org.motion.ballsimapp.shared.Events.GAME_LIST_IDS;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

public class Games {

	Set<String> games = new HashSet<String>();

	public boolean handle(GameEvent event) {

		if (Events.isState(event, GAME_LIST)) {
			for (String id : event.getAttribute(GAME_LIST_IDS).getValue()
					.split(",")) {
				games.add(id);
			}
			return true;
		}
		return false;
	}

	public Collection<String> active() {
		return games;
	}

}
