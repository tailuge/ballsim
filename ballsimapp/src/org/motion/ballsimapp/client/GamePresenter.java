package org.motion.ballsimapp.client;


public class GamePresenter {
	
	// model
	
	private GameModel model;

	// view
	
	private GameView view;

	
	public GamePresenter(GameModel model, GameView view) {
		this.model = model;
		this.view = view;
		view.addAimCompleteHandler(aimHandler());
	}


	public void begin() {
		
		view.setPlayer(model.playerId);
		view.appendMessage("init");
		
		// hit callback
		
		
		
	}
	 
	// temporary
	public void beginAim()
	{
		model.temp();
		model.table.resetToCurrent(model.table.getMaxTime());
		view.aim(model.table, 15);
	}
	
	public AimHandler aimHandler()
	{
		return new AimHandler() {
			
			@Override
			public void handleAim() {
				
				// pass to model
				
				model.temp();
				
				// update view
				
				view.appendMessage("aim");
				view.animate(model.table);
			}
		};
	}
	
}
