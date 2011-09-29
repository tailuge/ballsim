package org.motion.ballsimapp.client.pool;

import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.client.pool.mode.BilliardsMode;
import org.motion.ballsimapp.client.pool.mode.PlacingMode;
import org.motion.ballsimapp.shared.GameEvent;

public class BilliardsPresenter implements GWTGameEventHandler {

	// model

	final public BilliardsModel model;

	// view

	final public BilliardsView view;

	// mode

	private BilliardsMode mode;

	public BilliardsPresenter(BilliardsModel model, BilliardsView view) {
		this.model = model;
		this.view = view;
//		model.table.rack("only cue ball","");
		model.table.rack("SimplePool","");
		model.setEventHandler(this);
		view.setEventHandler(this);
		view.showTable(model.table);
//		mode = new LoginMode(model, view);
//		mode = new AimingMode(model,view);
		mode = new PlacingMode(model,view);
		
	}

	@Override
	public void handleEvent(GameEvent event) {
		mode = mode.handle(event);
	}
}
