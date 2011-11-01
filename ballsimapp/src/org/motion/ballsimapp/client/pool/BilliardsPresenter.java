package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.physics.util.Rack;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.client.mode.BilliardsMode;
import org.motion.ballsimapp.client.mode.LoginMode;
import org.motion.ballsimapp.shared.GameEvent;

public class BilliardsPresenter implements GWTGameEventHandler {

	// model

	final public BilliardsModel model;

	// table view

	final public TableView tableView;
	
	// info view (login/chat)
	
	final public InfoView infoView;

	// mode

	private BilliardsMode mode;

	public BilliardsPresenter(BilliardsModel model, InfoView infoView,
			TableView tableView) {
		
		this.model = model;
		this.infoView = infoView;
		this.tableView = tableView;
		
		Rack.rack(model.table,"WhiteOnly","");
		model.setEventHandler(this);
		infoView.setEventHandler(this);
		tableView.setEventHandler(this);
		tableView.showTable(model.table);
		mode = new LoginMode(model, tableView, infoView);
	}

	@Override
	public void handleEvent(GameEvent event) {
		mode = mode.handle(event);
	}
		
}
