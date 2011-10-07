package org.motion.ballsimapp.client.mode;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class RequestGamesMode extends BilliardsMode {

	private Games games = new Games();
	
	public RequestGamesMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
		view.appendMessage("requesting to be a spectator");
		model.gameId = "";
		model.notify(Events.requestGames(view.getPlayerId()));			
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		
		if (games.handle(event))
		{			
			view.appendMessage("active games" + games.active());
			return this;
		}

		
		GWT.log("RequestGamesMode handled unexpected event:" + event);

		return this;
	}

}
