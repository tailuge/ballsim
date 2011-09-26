package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.PLACEBALL_COMPLETE;
import static org.motion.ballsimapp.shared.Events.PLACEBALL_UPDATE;

import org.motion.ballsim.gwtsafe.Vector3D;
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
		
		if (event.hasAttribute(PLACEBALL_UPDATE))
		{
			Vector3D pos = BilliardsMarshaller.placeFromEvent(event);
			model.sendLimitedPlaceBallUpdate(pos);
			view.showTable(model.table);
			view.setPlacer(pos);
			return this;
		}

		if (event.hasAttribute(PLACEBALL_COMPLETE))
		{
			Vector3D pos = BilliardsMarshaller.placeFromEvent(event);
			model.sendPlaceBallUpdate(pos);
			model.table.beginNewShot();
			view.showTable(model.table);
			return new AimingMode(model,view);
		}

		GWT.log("PlacingMode handled unexpected event:"+event);
		
		return this;
	}

}
