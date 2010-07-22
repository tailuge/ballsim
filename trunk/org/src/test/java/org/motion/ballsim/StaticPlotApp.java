package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;
import org.motion.ballsim.plotter.StaticPlot;

public class StaticPlotApp 
{
	static StaticPlot plot;
	
    public static void main(String[] args) 
    {
    	//testCase1();
    	
		Table t = new Table();
		Ball b1 = new Ball(Utilities.getStationary(new Vector3D(Ball.R*6,-Ball.R*6,0)));
		Ball b2 = new Ball(Utilities.getStationary(new Vector3D(-Ball.R*6,-Ball.R*6,0)));
		Ball b3 = new Ball(Utilities.getStationary(new Vector3D(Ball.R*5,-Ball.R*3,0)));
				
		t.balls.add(b1);
		t.balls.add(b2);
		t.balls.add(b3);
		
		ShotFinder finder = new ShotFinder(new OneCushionRuleSet(),t);
		
		Table tResult = finder.FindBest(b1,360);
		plot = new StaticPlot(tResult,50);
    	plot.draw();
    	
    }	

    public static void testCase1() 
    {

	
	Event e2 = new Event(
			new Vector3D(-7.46186466568119040, 13.58212010728338400,0),
			new Vector3D(-0.17115815906368680, -6.18992731042295200,0),
			new Vector3D(0,0,0),
			new Vector3D(59.38269619059247600, 6.96035116039190900,0),
			new Vector3D(0,0,0),
			State.Sliding,
			0.567154439135523,
			EventType.Interpolated,0,0);
	Event e3 = new Event(
			new Vector3D(-6.07824220980257750, 15.02632144823587300,0),
			new Vector3D(42.04694426339171000, 79.12242811508793000,0),
			new Vector3D(0,0,0),
			new Vector3D(13.54980461407249400, 20.77515110112390700,0),
			new Vector3D(0,0,0),
			State.Sliding,
			0.567154439135523,
			EventType.Interpolated,0,0);

	
	Ball b2 = new Ball(e2);
	Ball b3 = new Ball(e3);
	
	Table t= new Table();
//	t.balls.add(b1);
	t.balls.add(b2);
	t.balls.add(b3);

	t.generateNext();
	
	plot = new StaticPlot(t,50);
	plot.draw();
    }
}
