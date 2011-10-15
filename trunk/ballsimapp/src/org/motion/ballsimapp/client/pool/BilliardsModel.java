package org.motion.ballsimapp.client.pool;

import static org.motion.ballsimapp.shared.Events.GAME_ID;
import static org.motion.ballsimapp.shared.Events.PLAYER_ALIAS;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.game.Aim;
import org.motion.ballsimapp.client.comms.GWTGameClient;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;

public class BilliardsModel extends GWTGameClient {

	// billiards table and balls model (physics model)

	public Table table = new Table(true);

	GWTGameEventHandler eventHandler;

	public String lastTableShot = "";
	public String lastTableShotOutcome = "";
	
	final TimeFilter filter = new TimeFilter(this);
	
	public String playerId = "";
	public String opponentId = "";
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
		if (!playerId.isEmpty())
			event.addAttribute(new GameEventAttribute(PLAYER_ALIAS,playerId));
		if (!gameId.isEmpty())
			event.addAttribute(new GameEventAttribute(GAME_ID,gameId));			
		super.notify(event);
	}
	
	public void sendAimUpdate(Aim aim) {
		filter.throttledSend(BilliardsEventFactory.aimUpdate(aim));
	}

	public void sendHit(Aim aim) {
		filter.cancel();
		notify(BilliardsEventFactory.hitOutcome(table,aim));
	}

	public void sendPlaceBallUpdate(Aim input) {
		filter.throttledSend(BilliardsEventFactory.placeBallUpdate(input));
	}

	public void sendPlaceBall(Aim input) {
		filter.cancel();
		notify(BilliardsEventFactory.placeBallUpdate(input));
	}

	public void sendToEventLoop(GameEvent event)
	{
		eventHandler.handleEvent(event);
	}
}
