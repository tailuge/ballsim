package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.AWAITING_GAME;
import static org.motion.ballsimapp.shared.Events.BEGIN_AIMING;
import static org.motion.ballsimapp.shared.Events.BEGIN_VIEWING;
import static org.motion.ballsimapp.shared.Events.GAME_ID;
import static org.motion.ballsimapp.shared.Events.GAME_RACK_SEED;
import static org.motion.ballsimapp.shared.Events.GAME_RACK_TYPE;

import org.motion.ballsim.physics.util.Rack;
import org.motion.ballsimapp.client.mode.pool.ViewingMode;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.InfoView;
import org.motion.ballsimapp.client.pool.TableView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class RequestGameMode extends BilliardsMode {

	public RequestGameMode(BilliardsModel model, TableView tableView, InfoView infoView) {
		super(model, tableView, infoView);
		infoView.appendMessage("requesting game");
		model.gameId = "";
		model.notify(Events.requestGame(infoView.getPlayerId()));			
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		if (Events.isState(event,AWAITING_GAME))
		{			
			infoView.appendMessage("awaiting game...");
			return this;
		}

		if (Events.isState(event,BEGIN_AIMING))
		{			
			processRack(event);
			model.gameId = event.getAttribute(GAME_ID).getValue();
			model.opponentId = event.getAttribute("player.notinplay").getValue(); 
			infoView.clearMessage();
			infoView.appendMessage(model.playerId + " vs " + model.opponentId);
			return selectAimingMode(event);
		}

		if (Events.isState(event,BEGIN_VIEWING))
		{			
			processRack(event);
			model.gameId = event.getAttribute(GAME_ID).getValue();
			model.opponentId = event.getAttribute("player.inplay").getValue();
			infoView.clearMessage();
			infoView.appendMessage(model.playerId + " vs " + model.opponentId);
			return new ViewingMode(model,tableView,infoView);
		}

		GWT.log("RequestGameMode handled unexpected event:" + event);

		return this;
	}

	private void processRack(GameEvent event)
	{
		if (event.hasAttribute(GAME_RACK_TYPE))
			Rack.rack(model.table,event.getAttribute(GAME_RACK_TYPE).getValue(),event.getAttribute(GAME_RACK_SEED).getValue());		
	}
}
