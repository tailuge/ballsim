package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.AWAITING_GAME;
import static org.motion.ballsimapp.shared.Events.BEGIN_AIMING;
import static org.motion.ballsimapp.shared.Events.BEGIN_VIEWING;
import static org.motion.ballsimapp.shared.Events.GAME_ID;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class RequestGameMode extends BilliardsMode {

	public RequestGameMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
		view.appendMessage("requesting game");
		model.notify(Events.requestGame(view.getPlayerId()));			
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		if (Events.isState(event,AWAITING_GAME))
		{			
			view.appendMessage("awaiting game...");
			return this;
		}

		if (Events.isState(event,BEGIN_AIMING))
		{			
			model.gameId = event.getAttribute(GAME_ID).getValue();
			view.appendMessage("game started (break)");
			return new AimingMode(model,view);
		}

		if (Events.isState(event,BEGIN_VIEWING))
		{			
			model.gameId = event.getAttribute(GAME_ID).getValue();
			view.appendMessage("game started (other player to break)");
			return new ViewingMode(model,view);
		}

		GWT.log("RequestGameMode handled unexpected event:" + event);

		return this;
	}

}
