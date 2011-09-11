package org.communications.layer.server;

import org.communications.layer.client.GreetingService;
import org.communications.layer.shared.FieldVerifier;

import com.google.appengine.api.channel.ChannelFailureException;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

  private static ChannelService channelService = ChannelServiceFactory.getChannelService();

	public String greetServer(String input) throws IllegalArgumentException {
		if (!FieldVerifier.isValidName(input)) {
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		return createChannel(input);
	}

	public String createChannel(String userId){
		try{
		  return channelService.createChannel(userId);
		} catch(ChannelFailureException channelFailureException){
		  return null;
		} catch(Exception otherException){
		  return null;
		}
		}

	@Override
	public String echo(String channel, String data)
			throws IllegalArgumentException {

		channelService.sendMessage(new ChannelMessage(channel, "from server:"+data));
		return "ok";
	}
	

}