package org.motion.ballsimapp.client.pool.mode;

import org.motion.ballsimapp.client.pool.BilliardsPresenter;
import org.motion.ballsimapp.shared.GameEvent;

public abstract class BilliardsMode {
	
	protected final BilliardsPresenter presenter;
	
	public BilliardsMode(BilliardsPresenter presenter)
	{
		this.presenter = presenter;
	}
	
	public abstract BilliardsMode handle(GameEvent event);
	

}
