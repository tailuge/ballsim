package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.WATCHING_GAME;

import org.motion.ballsimapp.client.mode.pool.ViewingMode;
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
			
			// for now pick first and request to watch it
			
			for(String id : games.active())
			{
				model.notify(Events.requestWatchGame(id));
				break;
			}
			
			return this;
		}

		if (Events.isState(event, WATCHING_GAME))
		{
			return new ViewingMode(model,view);
		}

		GWT.log("RequestGamesMode handled unexpected event:" + event);

		return this;
	}

}
