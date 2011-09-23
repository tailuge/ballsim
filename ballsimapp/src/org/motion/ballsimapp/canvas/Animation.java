package org.motion.ballsimapp.canvas;

import static org.motion.ballsimapp.shared.GameEventUtil.ANIMATION_COMPLETE;

import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.shared.GameEventUtil;

import com.google.gwt.user.client.Timer;

public class Animation {

	private Table table;
	private TableCanvas tableCanvas;
	double time, startTime;
	double maxt;
	final private GWTGameEventHandler animationComplete;

	public Animation(Table table, TableCanvas tableCanvas,
			GWTGameEventHandler animationComplete) {
		this.table = table;
		this.tableCanvas = tableCanvas;
		this.animationComplete = animationComplete;

		maxt = table.getMaxTime();
		startTime = System.currentTimeMillis();
		getTimer().scheduleRepeating(30);
	}

	private Timer getTimer() {
		return new Timer() {
			@Override
			public void run() {
				time = (System.currentTimeMillis() - startTime) / 1000.0;
				tableCanvas.plotAtTime(table, time > maxt ? maxt : time);
				if (time > maxt) {
					this.cancel();
					animationComplete.handleEvent(GameEventUtil
							.makeEvent(ANIMATION_COMPLETE));
				}
			}
		};
	}

}
