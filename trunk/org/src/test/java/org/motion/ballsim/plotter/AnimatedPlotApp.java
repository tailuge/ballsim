package org.motion.ballsim.plotter;

import org.apache.commons.math.geometry.Vector3D;
import org.motion.ballsim.Ball;
import org.motion.ballsim.OneCushionRuleSet;
import org.motion.ballsim.ShotFinder;
import org.motion.ballsim.Table;
import org.motion.ballsim.Utilities;

public class AnimatedPlotApp {

	static AnimatedPlot plot;
	
    public static void main(String[] args) 
    {
    	//testCase1();
    	
		Table t = new Table();
		Ball b1 = new Ball(Utilities.getStationary(new Vector3D(Ball.R*7,-Ball.R*12,0)));
		Ball b2 = new Ball(Utilities.getStationary(new Vector3D(-Ball.R*6,-Ball.R*6,0)));
		Ball b3 = new Ball(Utilities.getStationary(new Vector3D(Ball.R*5,-Ball.R*3,0)));
				
		t.balls.add(b1);
		t.balls.add(b2);
		t.balls.add(b3);
		
		ShotFinder finder = new ShotFinder(new OneCushionRuleSet(),t);
		
		Table tResult = finder.FindBest(b1,300);
		plot = new AnimatedPlot(tResult,50);
    	plot.draw();
    	
    }	

}
