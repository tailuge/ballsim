package org.motion.ballsim.plotter;

import org.motion.ballsim.Ball;
import org.motion.ballsim.ShotFinder;
import org.motion.ballsim.Table;
import org.motion.ballsim.ThreeCushionRuleSet;
import org.motion.ballsim.Utilities;
import org.motion.ballsim.gwtsafe.Vector3D;

public class RollingPlot {

	static AnimatedPlot plot;
	
    public static void main(String[] args) 
    {
    	//testCase1();
    	
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getRolling(new Vector3D(0,100,0)));
		t.ball(2).setFirstEvent(Utilities.getRolling(new Vector3D(100,0,0),new Vector3D(-Ball.R*6,-Ball.R*6,0)));
		t.ball(3).setFirstEvent(Utilities.getRolling(new Vector3D(80,80,0),new Vector3D(-Ball.R*8,-Ball.R*3,0)));
					
//		ShotFinder finder = new ShotFinder(new ThreeCushionRuleSet(),t);
		
//		Table tResult = finder.FindBest(t.ball(1),300);
		t.generateSequence();
		plot = new AnimatedPlot(t,500);
    	plot.draw();
    	
    }	
}
