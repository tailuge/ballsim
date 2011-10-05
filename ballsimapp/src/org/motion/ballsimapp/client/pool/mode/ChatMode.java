package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.CHAT;
import static org.motion.ballsimapp.shared.Events.CHATTING;
import static org.motion.ballsimapp.shared.Events.CHAT_MESSAGE;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

public abstract class ChatMode extends BilliardsMode {

	public ChatMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
		view.setChatEnable(true);
	}

	protected boolean handleChat(GameEvent event) {

		if (Events.isState(event, CHATTING)) {
			view.appendMessage("opp:"
					+ event.getAttribute(CHAT_MESSAGE).getValue());
			return true;
		}

		if (Events.isAction(event, CHAT)) {
			String message = event.getAttribute(CHAT_MESSAGE).getValue();
			if (message.contains("dump")) {
				view.appendMessage(model.lastTableShot);
				return true;
			}

			model.notify(event);
			view.appendMessage("me:"
					+ event.getAttribute(CHAT_MESSAGE).getValue());
			return true;
		}

		return false;
	}

}
