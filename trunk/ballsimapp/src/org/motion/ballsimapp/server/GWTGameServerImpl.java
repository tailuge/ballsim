package org.motion.ballsimapp.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.motion.ballsimapp.client.GWTGameServer;
import org.motion.ballsimapp.proxy.GameServerProxy;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventCallback;
import org.motion.ballsimapp.shared.Events;

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

	private static final Map<String, String> channelMap = new HashMap<String, String>();

	private GameServerProxy proxy;

	public GWTGameServerImpl() {
		proxy = new GameServerProxy(this);
	}

	public GWTGameServerImpl(Object delegate) {
		super(delegate);
	}

	/** Called by Server via callback */
	@Override
	public void onEvent(GameEvent event) {
		log.warning("Callback from server received " + event);
		try {
			ChannelService channelService = ChannelServiceFactory
					.getChannelService();

			String target = event.getAttribute("target").getValue();
			log.warning("Sending message for " + target + " on channel for "
					+ channelMap.get(target));
			target = channelMap.get(target);

			channelService.sendMessage(new ChannelMessage(target,
					GameEventMarshaller.marshal(event)));
		} catch (ChannelFailureException e) {
			log.log(Level.SEVERE,
					"GWTGameServer failed to communicate on channel, channel failure execption ",
					e);
		} catch (Exception e) {
			log.log(Level.SEVERE,
					"GWTGameServer failed to communicate on channel, unknown execption ",
					e);
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
		log.info("GWTGameServer.notify: " + event);
		proxy.notify(event);
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

		log.info("GWTGameServer.Connecting: " + event);
		String user = event.getAttribute("user").getValue();
		// when debugging 2 clients in one browser we need to route messages
		// down a single channel connection.
		if (event.hasAttribute("synonym")) {
			channelMap.put(user, event.getAttribute("synonym").getValue());
			return Events.event(Events.ALREADY_CONNECTED, "");			
		} else {
			channelMap.put(user, user);
		}

		String channelName = createChannel(user);
		GameEvent connectEvent = Events.event("channelName", channelName);

		return connectEvent;
	}

	private String createChannel(String userId) {
		try {
			ChannelService channelService = ChannelServiceFactory
					.getChannelService();
			return channelService.createChannel(userId);
		} catch (ChannelFailureException e) {
			log.log(Level.SEVERE,
					"GWTGameServer. failed to create channel, channel failure execption ",
					e);
			return null;
		} catch (Exception e) {
			log.log(Level.SEVERE,
					"GWTGameServer. failed to create channel, unknown execption ",
					e);
			return null;
		}
	}

}
