package org.motion.ballsimapp.client.mode;

import static org.motion.ballsimapp.shared.Events.CHAT;
import static org.motion.ballsimapp.shared.Events.CHATTING;
import static org.motion.ballsimapp.shared.Events.CHAT_MESSAGE;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.InfoView;
import org.motion.ballsimapp.client.pool.TableView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

public abstract class ChatMode extends BilliardsMode {

	public ChatMode(BilliardsModel model, TableView tableView, InfoView infoView) {
		super(model, tableView, infoView);
		infoView.setChatEnable(true);
	}

	protected boolean handleChat(GameEvent event) {

		if (Events.isState(event, CHATTING)) {
			infoView.appendMessage( model.opponentId + ":"
					+ event.getAttribute(CHAT_MESSAGE).getValue());
			return true;
		}

		if (Events.isAction(event, CHAT)) {
			String message = event.getAttribute(CHAT_MESSAGE).getValue();
			if (message.contains("dump")) {
				infoView.appendMessage(model.lastTableShot);
				return true;
			}

			model.notify(event);
			infoView.appendMessage(model.playerId + ":"
					+ event.getAttribute(CHAT_MESSAGE).getValue());
			return true;
		}

		return false;
	}

}
