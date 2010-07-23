package org.motion.ballsim.plotter;

import org.apache.commons.math.geometry.Vector3D;
import org.motion.ballsim.Ball;
import org.motion.ballsim.ThreeCushionRuleSet;
import org.motion.ballsim.ShotFinder;
import org.motion.ballsim.Table;
import org.motion.ballsim.Utilities;

public class AnimatedPlotApp {

	static AnimatedPlot plot;
	
    public static void main(String[] args) 
    {
    	//testCase1();
    	
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*7,-Ball.R*18,0)));
		t.ball(2).setFirstEvent(Utilities.getStationary(new Vector3D(-Ball.R*6,-Ball.R*6,0)));
		t.ball(3).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));
					
		ShotFinder finder = new ShotFinder(new ThreeCushionRuleSet(),t);
		
		Table tResult = finder.FindBest(t.ball(1),300);
		plot = new AnimatedPlot(tResult,50);
    	plot.draw();
    	
    }	

}
