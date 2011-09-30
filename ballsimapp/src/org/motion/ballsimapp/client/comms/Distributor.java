package org.motion.ballsimapp.client.comms;

import static org.motion.ballsimapp.shared.Events.*;

import java.util.HashMap;
import java.util.Map;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.motion.ballsimapp.shared.Events;

public class Distributor {

	private static Map<String,GWTGameEventHandler> handlers = new HashMap<String,GWTGameEventHandler>();

	/**
	 * Send events to appropriate client. 
	 */
	public void distribute(GameEvent event)
	{
		GameEventAttribute attribute = event.getAttribute(TARGET);		
		if (attribute != null)
		{
			GWTGameEventHandler targetHandler = handlers.get(attribute.getValue());
			if (targetHandler != null)
			{
				targetHandler.handleEvent(event);
			}
		}
	}

	public void distributeAll(GameEvent event)
	{
		for (String user : handlers.keySet())
		{
			handlers.get(user).handleEvent(event);
		}
	}
	
	public void sendError(String user,String message)
	{
		handlers.get(user).handleEvent(Events.event(CLIENT_ERROR, message));
	}

	public void sendInfo(String user,String message)
	{
		handlers.get(user).handleEvent(Events.event(ALREADY_CONNECTED,message));
	}

	public void addGameEventListener(String user,GWTGameEventHandler handler)
	{
		handlers.put(user, handler);		
	}

}
