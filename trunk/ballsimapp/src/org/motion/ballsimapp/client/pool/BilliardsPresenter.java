package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.client.pool.handlers.AimNotify;
import org.motion.ballsimapp.client.pool.handlers.ViewNotify;
import org.motion.ballsimapp.client.pool.mode.AimingMode;
import org.motion.ballsimapp.client.pool.mode.BilliardsMode;
import org.motion.ballsimapp.shared.GameEvent;


public class BilliardsPresenter {
	
	// model
	
	final private BilliardsModel model;

	// view
	
	final private BilliardsView view;

	// mode
	
	private BilliardsMode mode;
	
	
	public BilliardsPresenter(BilliardsModel model, BilliardsView view) {
		this.model = model;
		this.view = view;
		this.mode = new AimingMode(this);
		
		view.setAimUpdateHandler(aimUpdateHandler());
		view.setAimCompleteHandler(aimCompleteHandler());
		view.setAnimationCompleteHandler(animationCompleteHandler());
		view.setPlayer(model.playerId);	
		model.setEventHandler(getEventHandler());
	}

	// temporary
	public void forceLogin() {		
	
		model.login(model.playerId);
		
	}

	// temporary
	public void beginWatching() {		
		view.setPlayer(model.playerId);
		view.appendMessage("init");
	}
	 
	// temporary
	public void beginAim()
	{
		model.temp();
		view.aim(model.table, 15);
	}
	
	public AimNotify aimUpdateHandler()
	{
		return new AimNotify() {
			
			@Override
			public void handle(Aim aim) {				
				// pass to model
				model.sendAimUpdate(aim);
			}
		};
	}

	public AimNotify aimCompleteHandler()
	{
		return new AimNotify() {
			
			@Override
			public void handle(Aim aim) {
				
				// pass to model
				model.hit(aim);
				
				// update view				
				view.appendMessage("animate");
				view.animate(model.table);
			}
		};
	}

	public ViewNotify animationCompleteHandler()
	{
		return new ViewNotify() {
			
			@Override
			public void handle() {
				model.table.resetToCurrent(model.table.getMaxTime());
				view.aim(model.table, 15);
			}
		};
	}

	public void showMessage(String message)
	{
		view.appendMessage(message);
	}

	// the mode of the presenter is driven by network events
	// and local input actions
	
	private GWTGameEventHandler getEventHandler()
	{
		return new GWTGameEventHandler() {
			@Override
			public void handle(GameEvent event) {
				mode = mode.handle(event);				
			}
		};
	}
}
