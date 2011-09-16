package org.motion.ballsimapp.canvas;

import org.motion.ballsim.physics.Table;

import com.google.gwt.user.client.Timer;

public class Animation {

	private Table table;
	private TableCanvas tableCanvas;
	double time, startTime;
	double maxt;
	
	public Animation(Table table, TableCanvas tableCanvas)
	{
		this.table = table;
		this.tableCanvas = tableCanvas;
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
				this.cancel();
	      }
	    };
	}

}
