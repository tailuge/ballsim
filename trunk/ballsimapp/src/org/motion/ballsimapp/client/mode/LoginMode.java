package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.CHANNEL_CONNECTED;
import static org.motion.ballsimapp.shared.Events.INITIATE_CONNECT;
import static org.motion.ballsimapp.shared.Events.LOGIN_SUCCESS;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.InfoView;
import org.motion.ballsimapp.client.pool.TableView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

public class LoginMode extends BilliardsMode {

	public LoginMode(BilliardsModel model, TableView tableView, InfoView infoView) {
		super(model, tableView, infoView);
		tableView.setVisibility(false);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		if (event.hasAttribute(INITIATE_CONNECT))
		{
			if (infoView.getPlayerId().isEmpty())
			{
				infoView.appendMessage("enter name");
				return this;
			}				
			
			tableView.setVisibility(true);
			infoView.appendMessage("connecting...");
			model.connect(infoView.getPlayerId());
			return this;
		}
		
		if (event.hasAttribute(CHANNEL_CONNECTED))
		{
			infoView.appendMessage("connected to server.");
			infoView.appendMessage("logging in as " + infoView.getPlayerId() + "...");
			model.notify(Events.login(infoView.getPlayerId(), infoView.getPassword()));
			return this;
		}
		
		if (Events.isState(event,LOGIN_SUCCESS))
		{			
			infoView.appendMessage("login successfull.");
			if (infoView.getPlayerId().contains("spec"))
				return new RequestGamesMode(model, tableView, infoView);
			else
				return new RequestGameMode(model, tableView, infoView);
		}
		
		return DebugMode.filter(this, event);
	}

}
