package org.motion.ballsimapp.canvas;

import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.client.pool.handlers.ViewNotify;

import com.google.gwt.user.client.Timer;

public class Animation {

	private Table table;
	private TableCanvas tableCanvas;
	double time, startTime;
	double maxt;
	final private ViewNotify animationComplete;
	
	public Animation(Table table, TableCanvas tableCanvas,ViewNotify animationComplete)
	{
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
	        double plottime = time > maxt ? maxt : time;
	        tableCanvas.plotAtTime(table, plottime);
			if (time > maxt) 
			{
				this.cancel();
				animationComplete.handleAnimationComplete();
			}
	      }
	    };
	}

}
