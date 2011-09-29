package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.CURSOR_INPUT;
import static org.motion.ballsimapp.shared.Events.CURSOR_INPUT_COMPLETE;

import org.motion.ballsim.game.Aim;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;


public class AimingMode extends BilliardsMode {

	public AimingMode(BilliardsModel model,BilliardsView view) {
		super(model,view);
		view.showTable(model.table);
		view.aim(15);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (event.hasAttribute(CURSOR_INPUT))
		{
			// update view with aim
			Aim input = BilliardsMarshaller.aimFromEvent(event);
			view.setAim(input);
			view.showAim();
			
			// send aim update to server
			model.sendAimUpdate(input);
			return this;
		}


		if (event.hasAttribute(CURSOR_INPUT_COMPLETE))
		{			
			return new CalculationMode(model,view,BilliardsMarshaller.aimFromEvent(event),true);
		}
		
		GWT.log("AimingMode handled unexpected event:"+event);
		
		return this;
	}

}
