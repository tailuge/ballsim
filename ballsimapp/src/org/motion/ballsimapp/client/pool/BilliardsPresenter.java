package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.client.pool.handlers.AimNotify;
import org.motion.ballsimapp.client.pool.handlers.ViewNotify;
import org.motion.ballsimapp.client.pool.mode.AimingMode;
import org.motion.ballsimapp.client.pool.mode.BilliardsMode;
import org.motion.ballsimapp.client.pool.mode.ViewingMode;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.motion.ballsimapp.shared.GameEventUtil;


public class BilliardsPresenter {
	
	// model
	
	final public BilliardsModel model;

	// view
	
	final public BilliardsView view;

	// mode
	
	private BilliardsMode mode;
	
	
	public BilliardsPresenter(BilliardsModel model, BilliardsView view) {
		this.model = model;
		this.view = view;
//		this.mode = new AimingMode(this);
		
		view.setAimUpdateHandler(aimUpdateHandler());
		view.setAimCompleteHandler(aimCompleteHandler());
		view.setAnimationCompleteHandler(animationCompleteHandler());
		view.setPlayer(model.playerId);	
		model.setEventHandler(getEventHandler());
	}

	// temporary
	public void forceLoginAim() {			
		mode = new AimingMode(this);
		model.tempInitTable();
		view.showTable(model.table);
		model.login(model.playerId);
		view.aim(15);
	}

	// temporary
	public void forceLoginView() {			
		mode = new ViewingMode(this);
		model.tempInitTable();
		view.showTable(model.table);
		model.login(model.playerId);
	}

	
	public AimNotify aimUpdateHandler()
	{
		return new AimNotify() {
			
			@Override
			public void handle(Aim aim) {				
				GameEvent aimComplete = BilliardsMarshaller.eventFromAim(aim);
				aimComplete.addAttribute(new GameEventAttribute("aimUpdate",""));
				mode = mode.handle(aimComplete);
			}
		};
	}

	public AimNotify aimCompleteHandler()
	{
		return new AimNotify() {
			
			@Override
			public void handle(Aim aim) {
				GameEvent aimComplete = BilliardsMarshaller.eventFromAim(aim);
				aimComplete.addAttribute(new GameEventAttribute("aimComplete",""));
				mode = mode.handle(aimComplete);
			}
		};
	}

	public ViewNotify animationCompleteHandler()
	{
		return new ViewNotify() {			
			@Override
			public void handle() {
				mode = mode.handle(GameEventUtil.simpleEvent("animationComplete", ""));
			}
		};
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
