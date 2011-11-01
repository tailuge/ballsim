package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.TABLE_STATE;
import static org.motion.ballsimapp.shared.Events.WATCHING;

import org.motion.ballsimapp.client.mode.pool.ViewingMode;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.InfoView;
import org.motion.ballsimapp.client.pool.TableView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class RequestGamesMode extends BilliardsMode {

	private Games games = new Games();
	private String description = "";
	
	public RequestGamesMode(BilliardsModel model, TableView tableView, InfoView infoView) {
		super(model, tableView, infoView);
		infoView.appendMessage("requesting to be a spectator");
		model.gameId = "";
		model.notify(Events.requestGames(infoView.getPlayerId()));			
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		
		if (games.handle(event))
		{			
			infoView.appendMessage("active games " + games.active());
			
			// for now pick first and request to watch it
			
			if (!games.active().isEmpty())
			{
				String id = games.getAnyGameId();
				description = games.active().get(id);
				infoView.appendMessage("request to watch game " + description);
				model.notify(Events.requestWatchGame(id));
			}
			
			return this;
		}

		if (Events.isState(event, WATCHING))
		{
			infoView.clearMessage();
			infoView.appendMessage("now watching " + description);
			// if this if from another machine, synchronise all balls position to begin the shot
			if (event.hasAttribute(TABLE_STATE))
			{
				if (!event.getAttribute(TABLE_STATE).getValue().equals("rack"))				
					BilliardsMarshaller.unmarshalToTable(model.table,event.getAttribute(TABLE_STATE).getValue());
			}
			
			return new ViewingMode(model,tableView,infoView);
		}

		GWT.log("RequestGamesMode handled unexpected event:" + event);

		return this;
	}

}
