package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.*;

import java.util.Collection;

import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

public class Games {

	public boolean handle(GameEvent event) {
		
		if (Events.isState(event,GAME_LIST))
		{			
		}
		return false;
	}

	public Collection<String> active() {
		return null;
	}

	
}
