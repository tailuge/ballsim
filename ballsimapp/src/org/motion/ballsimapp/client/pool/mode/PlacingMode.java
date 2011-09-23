package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.GameEventUtil.*;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.util.UtilEvent;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class PlacingMode extends BilliardsMode {

	public PlacingMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		if (event.hasAttribute(PLACEBALL_UPDATE))
		{
			Vector3D pos = BilliardsMarshaller.placeFromEvent(event);
			Event hit = UtilEvent.stationary(pos);
			model.table.ball(1).setFirstEvent(hit);
			view.showTable(model.table);
			view.setPlacer(pos);
			return this;
		}

		if (event.hasAttribute(PLACEBALL_COMPLETE))
		{
			Vector3D pos = BilliardsMarshaller.placeFromEvent(event);
			Event hit = UtilEvent.stationary(pos);
			model.table.ball(1).setFirstEvent(hit);
			view.showTable(model.table);
			view.aim(15);
			return new AimingMode(model,view);
		}

		GWT.log("PlacingMode handled unexpected event:"+event);
		
		return this;
	}

}
