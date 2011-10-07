package org.motion.ballsimapp.client.mode.pool;

import static org.motion.ballsimapp.shared.Events.BEGIN_AIMING;
import static org.motion.ballsimapp.shared.Events.BEGIN_VIEWING;
import static org.motion.ballsimapp.shared.Events.LOSER;
import static org.motion.ballsimapp.shared.Events.*;

import org.motion.ballsimapp.client.mode.BilliardsMode;
import org.motion.ballsimapp.client.mode.RequestGameMode;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class AnimationCompleteMode  extends BilliardsMode {

	public AnimationCompleteMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (Events.isState(event, BEGIN_AIMING)) {
			return selectAimingMode(event);
		}

		if (Events.isState(event, BEGIN_VIEWING)) {
			return new ViewingMode(model, view);
		}

		if (Events.isState(event, WINNER)) {
			view.appendMessage("winner!");
			return new RequestGameMode(model, view);
		}

		if (Events.isState(event, LOSER)) {
			view.appendMessage("loser!");
			return new RequestGameMode(model, view);
		}

		GWT.log("AnimationCompleteMode handled unexpected event:" + event);

		return this;
	}

}
