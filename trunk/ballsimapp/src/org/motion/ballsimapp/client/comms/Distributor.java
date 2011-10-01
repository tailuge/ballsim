package org.motion.ballsimapp.client.comms;

import static org.motion.ballsimapp.shared.Events.*;

import java.util.HashMap;
import java.util.Map;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.motion.ballsimapp.shared.Events;

/**
 * Only one channel can be opened per client therefore this class
 * acts as a distributer of messages arriving at that channel to
 * registered listeners based on the 'target' name in the message
 * Only needed to support multiple clients per browser during development.
 */
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

	public void toUser(String user,GameEvent event)
	{
		System.out.println("Sending info to"+user+" : "+event);
		handlers.get(user).handleEvent(event);
	}

	public void addGameEventListener(String user,GWTGameEventHandler handler)
	{
		handlers.put(user, handler);		
	}

}
