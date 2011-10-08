package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.TABLE_STATE;
import static org.motion.ballsimapp.shared.Events.WATCHING;

import org.motion.ballsimapp.client.mode.pool.ViewingMode;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class RequestGamesMode extends BilliardsMode {

	private Games games = new Games();
	private String description = "";
	
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
			view.appendMessage("active games " + games.active());
			
			// for now pick first and request to watch it
			
			if (!games.active().isEmpty())
			{
				String id = games.getAnyGameId();
				description = games.active().get(id);
				view.appendMessage("request to watch game " + description);
				model.notify(Events.requestWatchGame(id));
			}
			
			return this;
		}

		if (Events.isState(event, WATCHING))
		{
			view.clearMessage();
			view.appendMessage("now watching " + description);
			// if this if from another machine, synchronise all balls position to begin the shot
			if (event.hasAttribute(TABLE_STATE))
			{
				if (!event.getAttribute(TABLE_STATE).getValue().equals("rack"))				
					BilliardsMarshaller.unmarshalToTable(model.table,event.getAttribute(TABLE_STATE).getValue());
			}
			
			return new ViewingMode(model,view);
		}

		GWT.log("RequestGamesMode handled unexpected event:" + event);

		return this;
	}

}
