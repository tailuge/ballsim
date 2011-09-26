package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.AIM_COMPLETE;
import static org.motion.ballsimapp.shared.Events.AIM_UPDATE;

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


		if (event.hasAttribute(AIM_UPDATE))
		{
			model.sendAimUpdate(BilliardsMarshaller.aimFromEvent(event));
			return this;
		}

		if (event.hasAttribute(AIM_COMPLETE))
		{			
			model.sendHit(BilliardsMarshaller.aimFromEvent(event));
			return new AnimationMode(model,view);
		}
		
		GWT.log("AimingMode handled unexpected event:"+event);
		
		return this;
	}

}
