package org.motion.ballsimapp.client;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.util.UtilEvent;

public class GameModel {
	
	// table position and game state

	Table table;
	
	String playerId;

	public GameModel(String playerId) {
		this.playerId = playerId;
	}
	
	public void temp()
	{
		table = new Table(true);
		table.ball(1).setFirstEvent(UtilEvent.hit(Vector3D.ZERO, Vector3D.PLUS_J, Table.maxVel*0.6, 0.0));
		table.ball(2).setFirstEvent(UtilEvent.stationary(new Vector3D(-Ball.R*0.46,+Ball.R*18,0)));
		table.ball(3).setFirstEvent(UtilEvent.stationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));					
		table.generateSequence();
	}
	
	
}
