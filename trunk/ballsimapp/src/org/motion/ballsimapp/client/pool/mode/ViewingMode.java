package org.motion.ballsimapp.client.pool.mode;

import org.motion.ballsimapp.client.pool.BilliardsPresenter;
import org.motion.ballsimapp.shared.GameEvent;

public class ViewingMode extends BilliardsMode {

	public ViewingMode(BilliardsPresenter presenter) {
		super(presenter);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {
		
		if (event.hasAttribute("aim"))
		{
			
		}
		
		return this;
	}

}
