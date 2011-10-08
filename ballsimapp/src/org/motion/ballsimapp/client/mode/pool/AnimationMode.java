package org.motion.ballsimapp.client.mode.pool;

import static org.motion.ballsimapp.shared.Events.ANIMATION_COMPLETE;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsimapp.client.mode.BilliardsMode;
import org.motion.ballsimapp.client.mode.ChatMode;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.Scheduler;

public class AnimationMode extends ChatMode {

	final List<GameEvent> pending = new ArrayList<GameEvent>();

	public AnimationMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
		view.animate(model.table);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (event.hasAttribute(ANIMATION_COMPLETE)) {
			model.table.beginNewShot();

			view.showTable(model.table);
			Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
				@Override
				public void execute() {
					replayPendingEvents(pending);
				}
			});
			return new AnimationCompleteMode(model, view);
		}

		if (!handleChat(event))
			pending.add(event);

		return this;
	}

}
