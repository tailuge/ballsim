package demo.plotter;



import org.motion.ballsim.Utilities;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import demo.plotter.AnimatedPlot;
import org.motion.ballsim.search.ShotFinder;
import org.motion.ballsim.search.Table;
import org.motion.ballsim.search.ThreeCushionRuleSet;

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
		
		Table tResult = finder.findBest(t.ball(1),301);
		plot = new AnimatedPlot(tResult,50);
    	plot.draw();
    	
    }	

}
