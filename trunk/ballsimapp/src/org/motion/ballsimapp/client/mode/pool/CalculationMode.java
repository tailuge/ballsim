package org.motion.ballsimapp.client.mode.pool;

import static org.motion.ballsimapp.shared.Events.CALCULATION_COMPLETE;
import static org.motion.ballsimapp.shared.Events.TABLE_STATE;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.physics.game.Aim;
import org.motion.ballsimapp.client.mode.BilliardsMode;
import org.motion.ballsimapp.client.mode.ChatMode;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.InfoView;
import org.motion.ballsimapp.client.pool.TableView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class CalculationMode extends ChatMode implements RepeatingCommand {

	final List<GameEvent> pending = new ArrayList<GameEvent>();
	
	final Aim aim;
	final boolean sendResult;
	
	public CalculationMode(BilliardsModel model,TableView tableView, InfoView infoView, GameEvent event,
			boolean sendResult) {
		super(model, tableView, infoView);
		
		Aim aim = BilliardsMarshaller.aimFromEvent(event);
		this.sendResult = sendResult;
		this.aim = aim;
				
		// if this if from another machine, synchronise all balls position to begin the shot
		if (event.hasAttribute(TABLE_STATE))
		{
			BilliardsMarshaller.unmarshalToTable(model.table,event.getAttribute(TABLE_STATE).getValue());
		}
		
		model.table.setAim(aim);

		// for debug, serialise last shot initial state
		model.lastTableShot = BilliardsMarshaller.marshal(model.table);
				
		pending.add(Events.event(CALCULATION_COMPLETE, ""));
		Scheduler.get().scheduleIncremental(this);
	}

	@Override
	public boolean execute() {
		
		boolean busy = model.table.generateNext();

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
			return new AnimationMode(model, tableView, infoView);
		}

		// Events received in this mode must be placed in
		// pending list for replay when calculation is finished

		if (!handleChat(event))
			pending.add(event);

		return this;
	}

}
