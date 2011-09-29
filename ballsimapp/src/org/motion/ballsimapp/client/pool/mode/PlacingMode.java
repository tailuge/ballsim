package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.CURSOR_INPUT;
import static org.motion.ballsimapp.shared.Events.CURSOR_INPUT_COMPLETE;

import org.motion.ballsim.game.Aim;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class PlacingMode extends BilliardsMode {

	public PlacingMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
		view.place(15);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		if (event.hasAttribute(CURSOR_INPUT))
		{
			Aim input = BilliardsMarshaller.aimFromEvent(event);
//			model.sendLimitedPlaceBallUpdate(pos);
			view.showTable(model.table);
			//view.setPlacer(input.pos);
			return this;
		}

		if (event.hasAttribute(CURSOR_INPUT_COMPLETE))
		{
			Aim input = BilliardsMarshaller.aimFromEvent(event);
//			model.sendPlaceBallUpdate(pos);
			model.table.beginNewShot();
			view.showTable(model.table);
			return new AimingMode(model,view);
		}

		GWT.log("PlacingMode handled unexpected event:"+event);
		
		return this;
	}

}
