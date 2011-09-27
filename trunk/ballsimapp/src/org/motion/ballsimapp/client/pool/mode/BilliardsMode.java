package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.BALL_IN_HAND;

import java.util.List;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.GameEvent;

public abstract class BilliardsMode {

	protected final BilliardsModel model;
	protected final BilliardsView view;

	public BilliardsMode(BilliardsModel model, BilliardsView view) {
		this.model = model;
		this.view = view;
	}

	public abstract BilliardsMode handle(GameEvent event);

	protected void replayPendingEvents(List<GameEvent> pending) {
		for (GameEvent event : pending) {
			model.sendToEventLoop(event);
		}
	}

	protected BilliardsMode selectAimingMode(GameEvent event) {
		return event.hasAttribute(BALL_IN_HAND) ? new PlacingMode(model, view)
				: new AimingMode(model, view);
	}

}
