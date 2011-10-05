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
		view.setVisibility(false);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		if (event.hasAttribute(INITIATE_CONNECT))
		{
			if (view.getPlayerId().isEmpty())
			{
				view.appendMessage("enter name");
				return this;
			}				
			
			view.setVisibility(true);
			view.appendMessage("connecting...");
			model.connect(view.getPlayerId());
			return this;
		}
		
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
			return new RequestGameMode(model, view);
		}

		if (Events.isAction(event, CHAT)) {
			String message = event.getAttribute(CHAT_MESSAGE).getValue();
			if (message.equals("d")) {
				return new DebugMode(model,view);
			}
		}
		
		GWT.log("LoginMode handled unexpected event:" + event);

		return this;
	}

}
