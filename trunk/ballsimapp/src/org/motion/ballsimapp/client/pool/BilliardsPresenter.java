package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.physics.Event;


public class BilliardsPresenter implements MessageNotify {
	
	// model
	
	private BilliardsModel model;

	// view
	
	private BilliardsView view;

	
	public BilliardsPresenter(BilliardsModel model, BilliardsView view) {
		this.model = model;
		this.view = view;
		view.setAimUpdateHandler(aimUpdateHandler());
		view.setAimCompleteHandler(aimCompleteHandler());
		view.setAnimationCompleteHandler(animationCompleteHandler());
		view.setPlayer(model.playerId);
		
		
		model.setMessageHandler(this);
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
		model.table.resetToCurrent(model.table.getMaxTime());
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
				model.sendAimUpdate(aim);
				
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

	@Override
	public void handle(String message) {
		view.appendMessage(message);		
	}

	
}
