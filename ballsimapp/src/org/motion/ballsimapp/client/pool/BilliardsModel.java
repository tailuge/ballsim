package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.Interpolator;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.util.UtilEvent;
import org.motion.ballsimapp.client.comms.GWTGameClient;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;

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

	public void sendAimUpdate(Aim aim)
	{
		GameEvent aimEvent = BilliardsMarshaller.eventFromAim(aim);
		aimEvent.addAttribute(new GameEventAttribute("target","spectator"));
		notify(aimEvent);
	}

	public void hit(Aim aim)
	{
		Event cueBall = Interpolator.interpolate(table.ball(1), 0);
		Event hit = UtilEvent.hit(cueBall.pos, aim.dir, aim.speed, aim.spin.getY());
		table.ball(1).setFirstEvent(hit);
		table.generateSequence();
	}


	private GWTGameEventHandler getEventHandler()
	{
		return new GWTGameEventHandler() {

			@Override
			public void handle(GameEvent event) {
				// for now just display message
				messageHandler.handle(playerId+"-recv:"+event.toString());
			}
			
		};
	}
}
