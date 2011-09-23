package org.motion.ballsimapp.client.pool.mode;

import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsPresenter;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class AimingMode extends BilliardsMode {

	public AimingMode(BilliardsPresenter presenter) {
		super(presenter);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (event.hasAttribute("animationComplete"))
		{
			presenter.model.resetForNextShot();
			presenter.view.showTable(presenter.model.table);
			
			// for now enter aiming state again
			presenter.view.aim(15);
			return this;
			
		}

		if (event.hasAttribute("aimUpdate"))
		{
			// send to model
			presenter.model.sendAimUpdate(BilliardsMarshaller.aimFromEvent(event));
			return this;
		}

		if (event.hasAttribute("aimComplete"))
		{			
			// pass to model
			presenter.model.sendHit(BilliardsMarshaller.aimFromEvent(event));
			presenter.model.updateWithHit(BilliardsMarshaller.aimFromEvent(event));
		
			// update view				
			presenter.view.appendMessage("animate");
			presenter.view.animate(presenter.model.table);
			return this;
		}
		
		GWT.log("AimingMode handled unexpected event:"+event);
		
		return this;
	}

}
