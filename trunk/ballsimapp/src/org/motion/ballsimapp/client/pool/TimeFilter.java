package org.motion.ballsimapp.client.pool;

public class TimeFilter {

	double startTime;
	
	public TimeFilter()
	{
		startTime = System.currentTimeMillis();
	}
	
	public boolean hasElapsed(int i) {

		if (System.currentTimeMillis()-startTime > i*1000)
		{
			startTime = System.currentTimeMillis();			
			return true;
		}
		return false;
	}

}
