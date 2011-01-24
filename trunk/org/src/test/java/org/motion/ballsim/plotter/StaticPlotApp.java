package org.motion.ballsim.plotter;


import org.motion.ballsim.Ball;
import org.motion.ballsim.ThreeCushionRuleSet;
import org.motion.ballsim.ShotFinder;
import org.motion.ballsim.Table;
import org.motion.ballsim.Utilities;
import org.motion.ballsim.gwtsafe.Vector3D;

public class StaticPlotApp 
{
	static StaticPlot plot;
	
    public static void main(String[] args) 
    {
    	
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*6,-Ball.R*6,0)));
		t.ball(2).setFirstEvent(Utilities.getStationary(new Vector3D(-Ball.R*6,-Ball.R*6,0)));
		t.ball(3).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*5,-Ball.R*3,0)));
						
		ShotFinder finder = new ShotFinder(new ThreeCushionRuleSet(),t);
		
		Table tResult = finder.findBest(t.ball(1),360);
		plot = new StaticPlot(tResult,50);
    	plot.draw();
    	
    }	

 
}
