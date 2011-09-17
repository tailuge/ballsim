package org.communications.layer.server;

import org.communications.layer.client.GWTGameServer;
import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventAttribute;
import org.communications.layer.shared.GameEventCallback;

import com.google.appengine.api.channel.ChannelFailureException;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class GWTGameServerImpl extends RemoteServiceServlet implements GameEventCallback,GWTGameServer 
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	    
    /** called by server callback */
    @Override
    public void onEvent(GameEvent event) {
            // for (player in event) look up channel and notify 
    }
  
   /** called by client 
    * @return */

    public void notify(GameEvent event) {
    	
    	for(GameEventAttribute a : event.getAttributes())
    	{
    		System.out.println(a.getName() + ":" + a.getValue());
    	}
    	
		return;
    } 
    
    /** called by client on first contact
     * @return connection details */    
    
	public GameEvent connect(GameEvent event) throws IllegalArgumentException {
		
	   	for(GameEventAttribute a : event.getAttributes())
    	{
    		System.out.println(a.getName() + ":" + a.getValue());
    	}
	   	
 		String user = event.getAttributes().get(0).getValue();
 		String channelName = createChannel(user);
	   	
 		return GameEvent.simpleEvent("channelName", channelName);
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
