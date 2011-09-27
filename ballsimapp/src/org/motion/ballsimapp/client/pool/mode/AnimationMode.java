package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.ANIMATION_COMPLETE;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;

public class AnimationMode extends BilliardsMode {

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
			return new AnimationCompleteMode(model,view);
		}


		pending.add(event);

		GWT.log("AnimationMode added pending event:" + event);
		return this;
	}

}
