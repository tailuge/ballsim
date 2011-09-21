package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.physics.Event;


public class GamePresenter {
	
	// model
	
	private GameModel model;

	// view
	
	private GameView view;

	
	public GamePresenter(GameModel model, GameView view) {
		this.model = model;
		this.view = view;
		view.setAimCompleteHandler(aimHandler());
		view.setAnimationCompleteHandler(animationCompleteHandler());
		view.setPlayer(model.playerId);
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
	
	public AimNotify aimHandler()
	{
		return new AimNotify() {
			
			@Override
			public void handle(Event aimEvent) {
				
				// pass to model
				
				model.table.ball(1).setFirstEvent(aimEvent);
				model.table.generateSequence();
				
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

	
}
