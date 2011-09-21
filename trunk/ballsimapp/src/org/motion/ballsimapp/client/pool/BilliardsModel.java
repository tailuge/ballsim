package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.util.UtilEvent;
import org.motion.ballsimapp.client.comms.GWTGameClient;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.shared.GameEvent;

public class BilliardsModel extends GWTGameClient {
	
	// table position and game state

	Table table;
	
	String playerId;

	MessageNotify messageHandler;
	
	public BilliardsModel(String playerId) {
		this.playerId = playerId;
	}
	
	public void setMessageHandler(MessageNotify messageHandler)
	{
		this.messageHandler = messageHandler;
	}
	
	public void temp()
	{
		table = new Table(true);
		table.ball(1).setFirstEvent(UtilEvent.hit(Vector3D.ZERO, Vector3D.PLUS_J, Table.maxVel*0.6, 0.0));
		table.ball(2).setFirstEvent(UtilEvent.stationary(new Vector3D(-Ball.R*0.46,+Ball.R*18,0)));
		table.ball(3).setFirstEvent(UtilEvent.stationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));					
		table.generateSequence();
	}
	
	
	public void login(final String user) {
		login(user, getEventHandler());
	}

	public void sendAim(String message)
	{
		notify(playerId,"spectator","aim");
	}
	
	private GWTGameEventHandler getEventHandler()
	{
		return new GWTGameEventHandler() {

			@Override
			public void handle(GameEvent event) {
				// for now just display message
				System.out.println("Recv:"+playerId+":"+event);
				messageHandler.handle(event.toString());
			}
			
		};
	}
}
