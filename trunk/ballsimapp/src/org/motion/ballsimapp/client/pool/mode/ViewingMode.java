package org.motion.ballsimapp.client.pool.mode;

import org.motion.ballsim.game.Aim;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsPresenter;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class ViewingMode extends BilliardsMode {

	public ViewingMode(BilliardsPresenter presenter) {
		super(presenter);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		if (event.hasAttribute("aimUpdate"))
		{
			Aim aim = BilliardsMarshaller.aimFromEvent(event);
			presenter.view.setAim(aim);
			return this;
		}
		
		if (event.hasAttribute("animationComplete"))
		{
			presenter.model.table.resetToCurrent(presenter.model.table.getMaxTime());
			return this;
		}
		
		GWT.log("ViewingMode handled unexcpected event:"+event);

		return this;
	}

}
