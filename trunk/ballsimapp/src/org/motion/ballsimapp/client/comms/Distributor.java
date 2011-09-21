package org.motion.ballsimapp.client.comms;

import java.util.HashMap;
import java.util.Map;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.motion.ballsimapp.shared.GameEventUtil;

public class Distributor {

	private static Map<String,GWTGameEventHandler> handlers = new HashMap<String,GWTGameEventHandler>();

	/**
	 * Send events to appropriate client. 
	 */
	public void distribute(GameEvent event)
	{
		GameEventAttribute attribute = event.getAttribute("target");		
		if (attribute != null)
		{
			GWTGameEventHandler targetHandler = handlers.get(attribute.getValue());
			if (targetHandler != null)
			{
				targetHandler.handle(event);
			}
		}
	}

	public void distributeAll(GameEvent event)
	{
		for (String user : handlers.keySet())
		{
			handlers.get(user).handle(event);
		}
	}
	
	public void sendError(String user,String message)
	{
		handlers.get(user).handle(error(message));
	}

	public void sendInfo(String user,String message)
	{
		handlers.get(user).handle(info(message));
	}

	public void addGameEventListener(String user,GWTGameEventHandler handler)
	{
		handlers.put(user, handler);		
	}
	
	public GameEvent error(String message)
	{
		return GameEventUtil.simpleEvent("error", message);
	}

	public GameEvent info(String message)
	{
		return GameEventUtil.simpleEvent("info", message);
	}


	public GameEvent target(String user,GameEvent event)
	{
		event.addAttribute(new GameEventAttribute("target",user));
		return event;
	}

}
