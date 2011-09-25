package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.AIM_COMPLETE;
import static org.motion.ballsimapp.shared.Events.AIM_UPDATE;
import static org.motion.ballsimapp.shared.Events.PLACEBALL_UPDATE;

import org.motion.ballsim.gwtsafe.Vector3D;
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
			return new AnimationMode(model, view,false);
		}

		if (event.hasAttribute(PLACEBALL_UPDATE)) {
			Vector3D pos = BilliardsMarshaller.placeFromEvent(event);
			model.placeBall(pos);
			view.showTable(model.table);
			view.setPlacer(pos);
			return this;
		}

		GWT.log("ViewingMode handled unexpected event:" + event);

		return this;
	}

}
