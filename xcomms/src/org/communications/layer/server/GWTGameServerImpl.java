package org.communications.layer.server;

import java.util.logging.Logger;

import org.communications.layer.client.GWTGameServer;
import org.communications.layer.proxy.ChatProxy;
import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventAttribute;
import org.communications.layer.shared.GameEventCallback;

import com.google.appengine.api.channel.ChannelFailureException;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GWTGameServerImpl extends RemoteServiceServlet implements
		GameEventCallback, GWTGameServer {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(ChatProxy.class
			.getName());

	private ChatProxy proxy;

	public GWTGameServerImpl() {
		proxy = new ChatProxy(this);
	}

	/** Called by Server via callback */
	@Override
	public void onEvent(GameEvent event) {
		log.warning("Callback from server received "+event);
	}

	/**
	 * Called by Client
	 */
	public void notify(GameEvent event) {

		for (GameEventAttribute a : event.getAttributes()) {
			System.out.println(a.getName() + ":" + a.getValue());
		}
		return;
	}

	/**
	 * Called by client on first contact
	 * 
	 * @return connection details
	 */
	public GameEvent connect(GameEvent event) throws IllegalArgumentException {
		for (GameEventAttribute a : event.getAttributes()) {
			System.out.println(a.getName() + ":" + a.getValue());
		}
		String user = event.getAttributes().get(0).getValue();
		String channelName = createChannel(user);
		GameEvent connectEvent = GameEvent.simpleEvent("channelName",
				channelName);
		proxy.notify(connectEvent);
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
