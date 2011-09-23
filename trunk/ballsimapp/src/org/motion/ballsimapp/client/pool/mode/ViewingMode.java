package org.motion.ballsimapp.client.pool.mode;

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
			presenter.view.setAim(BilliardsMarshaller.aimFromEvent(event));
			return this;
		}

		if (event.hasAttribute("aimComplete"))
		{
			presenter.model.updateWithHit(BilliardsMarshaller.aimFromEvent(event));
			
			// update view				
			presenter.view.appendMessage("animate");
			presenter.view.animate(presenter.model.table);
			return this;
		}

		if (event.hasAttribute("animationComplete"))
		{
			presenter.model.resetForNextShot();
			presenter.view.showTable(presenter.model.table);
			return this;
		}
		
		GWT.log("ViewingMode handled unexpected event:"+event);

		return this;
	}

}
