package org.communications.layer.server;

import org.communications.layer.client.GWTGameServer;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class GWTGameServerImpl extends RemoteServiceServlet implements GWTGameServer 
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private GameServer server;
    
    /** called by server callback */
  //  @Override
   // public void onEvent(GameEvent event) {
            // for (player in event) look up channel and notify 
    //}
  
   /** called by client 
 * @return */

    public String notify(String event) {
		return "ok";
    } 
}
