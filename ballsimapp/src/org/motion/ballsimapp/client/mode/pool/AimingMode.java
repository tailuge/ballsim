package org.motion.ballsimapp.client.mode.pool;

import static org.motion.ballsimapp.shared.Events.CURSOR_INPUT;
import static org.motion.ballsimapp.shared.Events.CURSOR_INPUT_COMPLETE;

import org.motion.ballsim.physics.game.Aim;
import org.motion.ballsimapp.client.mode.BilliardsMode;
import org.motion.ballsimapp.client.mode.ChatMode;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.InfoView;
import org.motion.ballsimapp.client.pool.TableView;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;


public class AimingMode extends ChatMode {

	public AimingMode(BilliardsModel model, TableView tableView, InfoView infoView) {
		super(model, tableView, infoView);
		tableView.showTable(model.table);
		tableView.aim(15);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (event.hasAttribute(CURSOR_INPUT))
		{
			// update view with aim
			Aim input = BilliardsMarshaller.aimFromEvent(event);
			tableView.setAim(input);
			tableView.showAim();
			
			// send aim update to server
			model.sendAimUpdate(input);
			return this;
		}


		if (event.hasAttribute(CURSOR_INPUT_COMPLETE))
		{			
			return new CalculationMode(model,tableView,infoView,event,true);
		}
		
		if (handleChat(event))
			return this;

		GWT.log("AimingMode handled unexpected event:"+event);
		
		return this;
	}

}
