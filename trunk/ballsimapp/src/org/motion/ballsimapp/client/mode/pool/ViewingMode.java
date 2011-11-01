package org.motion.ballsimapp.client.mode.pool;

import static org.motion.ballsimapp.shared.Events.AIM_COMPLETE;
import static org.motion.ballsimapp.shared.Events.AIM_UPDATE;
import static org.motion.ballsimapp.shared.Events.PLACEBALL_UPDATE;

import org.motion.ballsim.physics.game.Aim;
import org.motion.ballsimapp.client.mode.BilliardsMode;
import org.motion.ballsimapp.client.mode.ChatMode;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.InfoView;
import org.motion.ballsimapp.client.pool.TableView;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class ViewingMode extends ChatMode {

	public ViewingMode(BilliardsModel model, TableView tableView, InfoView infoView) {
		super(model, tableView, infoView);
		tableView.showTable(model.table);
		tableView.watch();
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (event.hasAttribute(AIM_UPDATE)) {
			tableView.setAim(BilliardsMarshaller.aimFromEvent(event));
			tableView.showAim();
			return this;
		}

		if (event.hasAttribute(AIM_COMPLETE)) {
			
			return new CalculationMode(model, tableView, infoView, event, false);
		}

		if (event.hasAttribute(PLACEBALL_UPDATE)) {
			Aim input = BilliardsMarshaller.aimFromEvent(event);
			model.table.placeBall(input.ballId, input.pos);
			tableView.showTable(model.table);
			tableView.showPlacer();
			return this;
		}

		if (handleChat(event))
			return this;
		
		GWT.log("ViewingMode handled unexpected event:" + event);

		return this;
	}

}
