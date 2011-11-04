package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.BALL_IN_HAND;

import java.util.List;

import org.motion.ballsimapp.client.mode.pool.AimingMode;
import org.motion.ballsimapp.client.mode.pool.PlacingMode;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.InfoView;
import org.motion.ballsimapp.client.pool.TableView;
import org.motion.ballsimapp.shared.GameEvent;

public abstract class BilliardsMode {

	protected final BilliardsModel model;
	protected final TableView tableView;
	protected final InfoView infoView;
	protected static boolean debugMode = false;
	
	public BilliardsMode(BilliardsModel model, TableView tableView, InfoView infoView) {
		this.model = model;
		this.tableView = tableView;
		this.infoView = infoView;
	}

	public abstract BilliardsMode handle(GameEvent event);

	protected void replayPendingEvents(List<GameEvent> pending) {
		for (GameEvent event : pending) {
			model.sendToEventLoop(event);
		}
	}

	protected BilliardsMode selectAimingMode(GameEvent event) {
		if (event.hasAttribute(BALL_IN_HAND)
				&& event.getAttribute(BALL_IN_HAND).getValue().equals("true"))
			return new PlacingMode(model, tableView, infoView);
		else
			return new AimingMode(model, tableView, infoView);
	}


}
