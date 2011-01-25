package org.motion.ballsim.plotter;

import org.motion.ballsim.Utilities;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.motion.Ball;
import org.motion.ballsim.search.Table;

public class InteractivePlot {

	static MousePlot plot;
	
    public static void main(String[] args) 
    {
 		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*7,-Ball.R*18,0)));
		t.ball(2).setFirstEvent(Utilities.getStationary(new Vector3D(-Ball.R*6,-Ball.R*6,0)));
		t.ball(3).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));
					
		plot = new MousePlot(t,50);
    	plot.draw();
    	
    }	
}
