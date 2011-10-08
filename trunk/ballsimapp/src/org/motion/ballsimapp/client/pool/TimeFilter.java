package org.motion.ballsimapp.client.pool;

import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.user.client.Timer;

public class TimeFilter {

	final BilliardsModel model;
	final Timer timer;
	boolean timerActive;
	GameEvent pendingEvent;

	public TimeFilter(BilliardsModel model_) {
		this.model = model_;
		timer = new Timer() {

			@Override
			public void run() {
				if (pendingEvent != null)
					model.notify(pendingEvent);
				pendingEvent = null;
				timerActive = false;
			}
		};
	}

	public void throttledSend(GameEvent event) {

		if (timerActive) {
			pendingEvent = event;
			return;
		}

		model.notify(event);
		timerActive = true;
		timer.schedule(2000);
	}
	
	public void cancel()
	{
		timer.cancel();
		timerActive = false;
		pendingEvent = null;
	}
}
