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

public class ViewingMode extends BilliardsMode {

	public ViewingMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (event.hasAttribute(AIM_UPDATE)) {
			view.setAim(BilliardsMarshaller.aimFromEvent(event));
			return this;
		}

		if (event.hasAttribute(AIM_COMPLETE)) {
			model.updateWithHit(BilliardsMarshaller.aimFromEvent(event));
			view.animate(model.table);
			return this;
		}

		if (event.hasAttribute(ANIMATION_COMPLETE)) {
			model.resetForNextShot();
			view.showTable(model.table);
			return this;
		}

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
			return new AimingMode(model,view);
		}
		
		GWT.log("ViewingMode handled unexpected event:" + event);

		return this;
	}

}
