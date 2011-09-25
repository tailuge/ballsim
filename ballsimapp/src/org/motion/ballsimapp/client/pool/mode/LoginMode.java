package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.GameEventUtil.*;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventUtil;

import com.google.gwt.core.client.GWT;

public class LoginMode extends BilliardsMode {

	public LoginMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
		model.connect(view.getPlayerId());
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		
		if (event.hasAttribute(CHANNEL_CONNECTED))
		{
			model.notify(GameEventUtil.getLoginEvent(view.getPlayerId(), view.getPassword()));
			return this;
		}

		if (event.hasAttribute(LOGIN_SUCCESS))
		{
			model.notify(GameEventUtil.requestGame(view.getPlayerId()));			
		}

		GWT.log("LoginMode handled unexpected event:" + event);

		return this;
	}

}
