package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.REQUESTED_WATCH_GAMES;
import static org.motion.ballsimapp.shared.Events.GAMES_IDS;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

public class Games {

	Set<String> games = new HashSet<String>();

	public boolean handle(GameEvent event) {

		if (Events.isState(event, REQUESTED_WATCH_GAMES)) {
			for (String id : event.getAttribute(GAMES_IDS).getValue()
					.split(",")) {
				if (!id.isEmpty())
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
