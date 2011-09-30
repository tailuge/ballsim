package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.AIM_COMPLETE;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.game.Aim;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class CalculationMode extends BilliardsMode implements RepeatingCommand {

	final List<GameEvent> pending = new ArrayList<GameEvent>();
	final Aim aim;
	final boolean sendResult;

	public CalculationMode(BilliardsModel model, BilliardsView view, Aim aim,
			boolean sendResult) {
		super(model, view);
		this.sendResult = sendResult;
		this.aim = aim;
		model.table.setAim(aim);
		pending.add(Events.event(AIM_COMPLETE, ""));
		Scheduler.get().scheduleIncremental(this);
	}

	@Override
	public boolean execute() {
		
		// do two iterations per call
		boolean busy = model.table.generateNext();// && model.table.generateNext();
		
		if (!busy) {
			if (sendResult) {
				model.sendHit(aim);
			}

			Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
				@Override
				public void execute() {
					replayPendingEvents(pending);
				}
			});
		}
		return busy;
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (event.hasAttribute(AIM_COMPLETE)) {
			return new AnimationMode(model, view);
		}

		// Events received in this mode must be placed in
		// pending list for replay when calculation is finished

		pending.add(event);

		//GWT.log("CalculationMode added pending event:" + event);
		return this;
	}

}
