package org.communications.layer.server;

import org.communications.layer.client.GWTGameServer;
import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventCallback;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class GWTGameServerImpl extends RemoteServiceServlet implements GameEventCallback,GWTGameServer 
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private GameServer server;
    
    /** called by server callback */
    @Override
    public void onEvent(GameEvent event) {
            // for (player in event) look up channel and notify 
    }
  
   /** called by client 
    * @return */

    public void notify(GameEvent event) {
		return;
    } 
}
