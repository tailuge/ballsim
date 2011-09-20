package org.communications.layer.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.communications.layer.client.GWTGameServer;
import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventAttribute;
import org.communications.layer.shared.GameEventCallback;
import org.communications.layer.shared.GameEventUtil;

import com.google.appengine.api.channel.ChannelFailureException;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GWTGameServerImpl extends RemoteServiceServlet implements
		GameEventCallback, GWTGameServer {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(GWTGameServerImpl.class
			.getName());

	private static final Map<String,String> channelMap = new HashMap<String, String>();
	

	/** Called by Server via callback */
	@Override
	public void onEvent(GameEvent event) {
		log.warning("Callback from server received " + event);
		System.out.println("onEvent");
		try {
			ChannelService channelService = ChannelServiceFactory
					.getChannelService();
			String target = event.getAttribute("target").getValue();

			if (channelMap.containsKey(target))
			{
				log.warning("Sending message for "+target+" on channel for "+channelMap.get(target));
				target = channelMap.get(target);
			}
			
			channelService.sendMessage(new ChannelMessage(target,GameEventMarshaller.marshal(event)));				
			log.warning("Message sent to: "+target);
		} catch (ChannelFailureException channelFailureException) {
			channelFailureException.printStackTrace();
		} catch (Exception otherException) {
			otherException.printStackTrace();
		}
	}

	/**
	 * Called by Client
	 */
	@Override
	public void notify(String data) throws IllegalArgumentException {
		notify(GameEventMarshaller.deMarshal(data));
	}
	
	public void notify(GameEvent event) {

		System.out.println("notify");
		
		for (GameEventAttribute a : event.getAttributes()) {
			System.out.println(a.getName() + ":" + a.getValue());
		}
		
		// temporary loop back
		onEvent(event);
		
		return;
	}

	/**
	 * Called by client on first contact with json GameEvent
	 * 
	 * @return connection details
	 */
	public String connect(String json) throws IllegalArgumentException {
		GameEvent event = GameEventMarshaller.deMarshal(json);
		return GameEventMarshaller.marshal(connect(event));
	}

	
	public GameEvent connect(GameEvent event) throws IllegalArgumentException {

		String user = event.getAttribute("user").getValue();
		if (event.hasAttribute("synonym"))
		{
			channelMap.put(user,event.getAttribute("synonym").getValue());
		}
		String channelName = createChannel(user);
		GameEvent connectEvent = GameEventUtil.simpleEvent("channelName",
				channelName);
		
		return connectEvent;
	}

	private String createChannel(String userId) {
		try {
			ChannelService channelService = ChannelServiceFactory
					.getChannelService();
			return channelService.createChannel(userId);
		} catch (ChannelFailureException channelFailureException) {
			return null;
		} catch (Exception otherException) {
			otherException.printStackTrace();
			return null;
		}
	}


}
