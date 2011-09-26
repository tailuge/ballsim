package org.motion.ballsimapp.client.pool;

import static org.motion.ballsimapp.shared.Events.GAME_ID;
import static org.motion.ballsimapp.shared.Events.PLAYER_ALIAS;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.client.comms.GWTGameClient;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;

public class BilliardsModel extends GWTGameClient {

	// billiards table and balls model (physics model)

	public Table table = new Table(true);

	GWTGameEventHandler eventHandler;

	TimeFilter filter = new TimeFilter();

	private String playerId;
	public String gameId = "";
	
	public void setEventHandler(GWTGameEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	public void connect(String playerId) {
		this.playerId = playerId;
		connect(playerId, eventHandler);
	}

	public void notify(GameEvent event)
	{
		event.addAttribute(new GameEventAttribute(PLAYER_ALIAS,playerId));
		if (!gameId.isEmpty())
			event.addAttribute(new GameEventAttribute(GAME_ID,gameId));			
		super.notify(event);
	}
	
	public void sendAimUpdate(Aim aim) {
		if (filter.hasElapsed(2)) {
			notify(BilliardsEventFactory.aimUpdate(aim));
		}
	}

	public void sendHit(Aim aim) {
		table.generateSequence(aim);
		notify(BilliardsEventFactory.hitOutcome(table,aim));
	}

	public void sendLimitedPlaceBallUpdate(Vector3D pos) {
		table.placeBall(pos);
		if (filter.hasElapsed(2)) 
			sendPlaceBallUpdate(pos);
	}

	public void sendPlaceBallUpdate(Vector3D pos) {
		table.placeBall(pos);
		notify(BilliardsEventFactory.placeBallUpdate(pos));
	}

}
