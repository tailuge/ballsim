package org.motion.ballsim.plotter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.math.geometry.Vector3D;
import org.motion.ballsim.Ball;
import org.motion.ballsim.Table;
import org.motion.ballsim.UtilEvent;
import org.motion.ballsim.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiPlotApp {

	static StaticPlot plot;
	
	private final static Logger logger = LoggerFactory.getLogger(MultiPlotApp.class);

    public static void main(String[] args) 
    {
		Collection<Table> tables = new ArrayList<Table>();
    	double h = -1.5;
    	while(h < 1.5)
    	{
    		logger.info("h:{}",h);
			Table t = new Table();
			t.ball(1).setFirstEvent(UtilEvent.hit(Vector3D.ZERO, Vector3D.MINUS_J, 100,h));			
			t.ball(2).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R,-Ball.R * 6, 0)));
			t.generateSequence();
			tables.add(t);		
			h += 0.3;
    	}
		
		plot = new StaticPlot(tables,50);
    	plot.draw();
    	
    }	

}
