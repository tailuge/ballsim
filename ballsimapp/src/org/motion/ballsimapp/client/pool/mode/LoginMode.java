package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.*;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.Events;

import com.google.gwt.core.client.GWT;

public class LoginMode extends BilliardsMode {

	public LoginMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
		view.appendMessage("connecting...");
		model.connect(view.getPlayerId());
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		
		if (event.hasAttribute(CHANNEL_CONNECTED))
		{
			view.appendMessage("connected to server.");
			view.appendMessage("logging in as " + view.getPlayerId() + "...");
			model.notify(Events.login(view.getPlayerId(), view.getPassword()));
			return this;
		}

		if (Events.isState(event,LOGIN_SUCCESS))
		{			
			view.appendMessage("login successfull.");
			view.appendMessage("requesting game");
			model.notify(Events.requestGame(view.getPlayerId()));			
			return this;
		}

		if (Events.isState(event,AWAITING_GAME))
		{			
			view.appendMessage("awaiting game...");
			return this;
		}

		if (Events.isState(event,"aiming"))
		{			
			return new AimingMode(model,view);
		}

		if (Events.isState(event,"viewing"))
		{			
			return new ViewingMode(model,view);
		}

		GWT.log("LoginMode handled unexpected event:" + event);

		return this;
	}

}
