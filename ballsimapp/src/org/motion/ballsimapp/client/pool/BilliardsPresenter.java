package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.client.pool.handlers.AimNotify;
import org.motion.ballsimapp.client.pool.handlers.ViewNotify;
import org.motion.ballsimapp.client.pool.mode.AimingMode;
import org.motion.ballsimapp.client.pool.mode.BilliardsMode;
import org.motion.ballsimapp.client.pool.mode.ViewingMode;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.motion.ballsimapp.shared.GameEventUtil;

public class BilliardsPresenter implements AimNotify, ViewNotify,
		GWTGameEventHandler {

	// model

	final public BilliardsModel model;

	// view

	final public BilliardsView view;

	// mode

	private BilliardsMode mode;

	public BilliardsPresenter(BilliardsModel model, BilliardsView view) {
		this.model = model;
		this.view = view;

		view.setAimHandler(this);
		view.setAnimationCompleteHandler(this);
		view.setPlayer(model.playerId);
		model.setEventHandler(this);
	}

	// temporary
	public void forceLoginAim() {
		mode = new AimingMode(model, view);
		model.tempInitTable();
		view.showTable(model.table);
		model.login(model.playerId);
		view.aim(15);
	}

	// temporary
	public void forceLoginView() {
		mode = new ViewingMode(model, view);
		model.tempInitTable();
		view.showTable(model.table);
		model.login(model.playerId);
	}

	@Override
	public void handleAimUpdate(Aim aim) {
		GameEvent aimComplete = BilliardsMarshaller.eventFromAim(aim);
		aimComplete.addAttribute(new GameEventAttribute("aimUpdate", ""));
		mode = mode.handle(aimComplete);
	}

	@Override
	public void handleAimComplete(Aim aim) {
		GameEvent aimComplete = BilliardsMarshaller.eventFromAim(aim);
		aimComplete.addAttribute(new GameEventAttribute("aimComplete", ""));
		mode = mode.handle(aimComplete);
	}

	@Override
	public void handleAnimationComplete() {
		mode = mode.handle(GameEventUtil.simpleEvent("animationComplete", ""));
	}

	@Override
	public void handlePlaceBallUpdate(Vector3D pos) {
	}

	@Override
	public void handlePlaceBallComplete(Vector3D pos) {
	}

	@Override
	public void handleEvent(GameEvent event) {
		mode = mode.handle(event);
	}
}
