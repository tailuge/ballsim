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

	public Table table = new Table(false);
	
	String playerId;

	GWTGameEventHandler eventHandler;
	
	public BilliardsModel(String playerId) {
		this.playerId = playerId;
	}
	
	public void setEventHandler(GWTGameEventHandler eventHandler)
	{
		this.eventHandler = eventHandler;
	}
	
	public void tempInitTable()
	{
		table.ball(1).setFirstEvent(UtilEvent.stationary(Vector3D.ZERO));
		table.ball(2).setFirstEvent(UtilEvent.stationary(new Vector3D(-Ball.R*0.46,+Ball.R*18,0)));
		table.ball(3).setFirstEvent(UtilEvent.stationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));					
	}
	
	
	public void login(final String user) {
		login(user, eventHandler);
	}

	public void sendAimUpdate(Aim aim)
	{
		GameEvent aimEvent = BilliardsMarshaller.eventFromAim(aim);
		aimEvent.addAttribute(new GameEventAttribute("target","frank"));
		aimEvent.addAttribute(new GameEventAttribute("aimUpdate",""));
		notify(aimEvent);
	}

	public void hit(Aim aim)
	{
		Event cueBall = Interpolator.interpolate(table.ball(1), 0);
		Event hit = UtilEvent.hit(cueBall.pos, aim.dir, aim.speed, aim.spin.getY());
		table.ball(1).setFirstEvent(hit);
		table.generateSequence();
	}



}
