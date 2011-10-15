package org.motion.ballsimapp.client.mode.pool;

import static org.motion.ballsimapp.shared.Events.CURSOR_INPUT;
import static org.motion.ballsimapp.shared.Events.CURSOR_INPUT_COMPLETE;

import org.motion.ballsim.physics.game.Aim;
import org.motion.ballsimapp.client.mode.BilliardsMode;
import org.motion.ballsimapp.client.mode.ChatMode;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class PlacingMode extends ChatMode {

	public PlacingMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
		view.place(15);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (event.hasAttribute(CURSOR_INPUT)) {
			Aim input = BilliardsMarshaller.aimFromEvent(event);
			model.table.placeBall(input.ballId, input.pos);
			model.sendPlaceBallUpdate(input);
			view.setAim(input);
			view.showPlacer();
			view.showTable(model.table);
			return this;
		}

		if (event.hasAttribute(CURSOR_INPUT_COMPLETE)) {
			Aim input = BilliardsMarshaller.aimFromEvent(event);
			model.table.placeBall(input.ballId, input.pos);
			model.sendPlaceBall(input);
			return new AimingMode(model, view);
		}

		if (handleChat(event))
			return this;

		GWT.log("PlacingMode handled unexpected event:" + event);

		return this;
	}

}
