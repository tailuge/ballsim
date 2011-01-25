package org.motion.ballsim.plotter;

import org.motion.ballsim.Utilities;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.motion.Ball;
import org.motion.ballsim.search.Table;

public class RollingPlot {

	static AnimatedPlot plot;
	
    public static void main(String[] args) 
    {
    	//testCase1();
    	
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getRolling(new Vector3D(-120,120,0),new Vector3D(+Ball.R*4,-Ball.R*16,0)));
		t.ball(2).setFirstEvent(Utilities.getRolling(new Vector3D(100,-100,0),new Vector3D(-Ball.R*4,-Ball.R*16,0)));
		t.ball(3).setFirstEvent(Utilities.getSliding(new Vector3D(10,200,0),Vector3D.ZERO));
					
//		ShotFinder finder = new ShotFinder(new ThreeCushionRuleSet(),t);
		
//		Table tResult = finder.FindBest(t.ball(1),300);
		t.generateSequence();
		plot = new AnimatedPlot(t,700);
    	plot.draw();
    	
    }	
}
