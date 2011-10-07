package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.physics.util.Rack;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.client.pool.mode.BilliardsMode;
import org.motion.ballsimapp.client.pool.mode.LoginMode;
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
		Rack.rack(model.table,"WhiteOnly","");
		model.setEventHandler(this);
		view.setEventHandler(this);
		view.showTable(model.table);
		mode = new LoginMode(model, view);
	}

	@Override
	public void handleEvent(GameEvent event) {
		mode = mode.handle(event);
	}
		
}
