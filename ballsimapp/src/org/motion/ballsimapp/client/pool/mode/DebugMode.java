package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.CHAT;
import static org.motion.ballsimapp.shared.Events.CHAT_MESSAGE;

import org.motion.ballsim.physics.util.Rack;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

public class DebugMode extends BilliardsMode {

	public DebugMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
		view.setChatEnable(true);
		view.setVisibility(true);
		view.appendMessage("DEBUG MODE");
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (Events.isAction(event, CHAT)) {
			String message = event.getAttribute(CHAT_MESSAGE).getValue();
			Rack.rack(model.table, message, "");
			model.table.generateSequence();
			view.appendMessage("CHECKSUM:" + model.table.getChecksum());
			return new AnimationMode(model, view);
		}

		return this;
	}

	public static BilliardsMode filter(BilliardsMode mode, GameEvent event) {
		
		if (Events.isAction(event, CHAT)) {
			String message = event.getAttribute(CHAT_MESSAGE).getValue();
			if (message.equals("d")) {
				return new DebugMode(mode.model, mode.view);
			}
		}
		
		return mode;
	}

}
