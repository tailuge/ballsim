package org.motion.ballsimapp.client.mode.pool;

import static org.motion.ballsimapp.shared.Events.BEGIN_AIMING;
import static org.motion.ballsimapp.shared.Events.BEGIN_VIEWING;
import static org.motion.ballsimapp.shared.Events.LOSER;
import static org.motion.ballsimapp.shared.Events.WINNER;

import org.motion.ballsimapp.client.mode.BilliardsMode;
import org.motion.ballsimapp.client.mode.ChatMode;
import org.motion.ballsimapp.client.mode.RequestGameMode;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.InfoView;
import org.motion.ballsimapp.client.pool.TableView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class AnimationCompleteMode extends ChatMode {

	public AnimationCompleteMode(BilliardsModel model, TableView tableView, InfoView infoView) {
		super(model, tableView, infoView);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (Events.isState(event, BEGIN_AIMING)) {
			return selectAimingMode(event);
		}

		if (Events.isState(event, BEGIN_VIEWING)) {
			return new ViewingMode(model, tableView, infoView);
		}

		if (Events.isState(event, WINNER)) {
			infoView.appendMessage("winner!");
			return new RequestGameMode(model, tableView, infoView);
		}

		if (Events.isState(event, LOSER)) {
			infoView.appendMessage("loser!");
			return new RequestGameMode(model, tableView, infoView);
		}
		
		if (handleChat(event))
			return this;

		GWT.log("AnimationCompleteMode handled unexpected event:" + event);

		return this;
	}

}
