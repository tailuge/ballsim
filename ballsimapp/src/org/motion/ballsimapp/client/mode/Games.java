package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.GAMES_DESCRIPTIONS;
import static org.motion.ballsimapp.shared.Events.GAMES_IDS;
import static org.motion.ballsimapp.shared.Events.REQUESTED_WATCH_GAMES;

import java.util.Map;
import java.util.TreeMap;

import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

public class Games {

	Map<String,String> games = new TreeMap<String,String>();

	public boolean handle(GameEvent event) {

		if (Events.isState(event, REQUESTED_WATCH_GAMES)) {
			String[] ids = event.getAttribute(GAMES_IDS).getValue()
					.split(",");
			String[] descriptions = event.getAttribute(GAMES_DESCRIPTIONS).getValue()
					.split(",");
			
			for (int index=0; index < ids.length; index++) {
				if (!ids[index].isEmpty())
					games.put(ids[index],descriptions[index]);
			}
			return true;
		}
		return false;
	}

	public Map<String,String> active() {
		return games;
	}

	public String getAnyGameId()
	{
		return (String)games.keySet().toArray()[0];
	}
}
