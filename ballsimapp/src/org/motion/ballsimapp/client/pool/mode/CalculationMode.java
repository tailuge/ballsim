package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.CALCULATION_COMPLETE;
import static org.motion.ballsimapp.shared.Events.TABLE_CHECKSUM;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.game.Aim;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
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
	private String remoteChecksum;
	
	public CalculationMode(BilliardsModel model, BilliardsView view, GameEvent event,
			boolean sendResult) {
		super(model, view);
		
		Aim aim = BilliardsMarshaller.aimFromEvent(event);
		this.sendResult = sendResult;
		this.aim = aim;
		model.table.setAim(aim);
		
		if (event.hasAttribute(TABLE_CHECKSUM))
		{
			remoteChecksum = event.getAttribute(TABLE_CHECKSUM).getValue();
		}

		pending.add(Events.event(CALCULATION_COMPLETE, ""));
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

		if (event.hasAttribute(CALCULATION_COMPLETE)) {
			if (remoteChecksum != null)
			{
				String localChecksum = model.table.getChecksum();
				if (!localChecksum.equals(remoteChecksum))
				{
					view.appendMessage("diverged:");
					view.appendMessage("remote checksum:"+remoteChecksum);	
					view.appendMessage("local  checksum:"+localChecksum);
				}
			}
			return new AnimationMode(model, view);
		}

		// Events received in this mode must be placed in
		// pending list for replay when calculation is finished

		pending.add(event);

		//GWT.log("CalculationMode added pending event:" + event);
		return this;
	}

}
