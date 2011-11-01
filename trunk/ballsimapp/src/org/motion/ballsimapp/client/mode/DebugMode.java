package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.CHAT;
import static org.motion.ballsimapp.shared.Events.CHAT_MESSAGE;

import org.motion.ballsim.physics.util.Rack;
import org.motion.ballsimapp.client.mode.pool.AnimationMode;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.InfoView;
import org.motion.ballsimapp.client.pool.TableView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

public class DebugMode extends BilliardsMode {

	public DebugMode(BilliardsModel model, TableView tableView, InfoView infoView) {
		super(model, tableView, infoView);
		infoView.setChatEnable(true);
		tableView.setVisibility(true);
		infoView.appendMessage("DEBUG MODE");
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (Events.isAction(event, CHAT)) {
			String message = event.getAttribute(CHAT_MESSAGE).getValue();
			Rack.rack(model.table, message, "");
			model.table.generateSequence();
			infoView.appendMessage("CHECKSUM:" + model.table.getChecksum());
			return new AnimationMode(model, tableView, infoView);
		}

		return this;
	}

	public static BilliardsMode filter(BilliardsMode mode, GameEvent event) {
		
		if (Events.isAction(event, CHAT)) {
			String message = event.getAttribute(CHAT_MESSAGE).getValue();
			if (message.isEmpty()) {
				return new DebugMode(mode.model, mode.tableView, mode.infoView);
			}
		}
		
		return mode;
	}

}
