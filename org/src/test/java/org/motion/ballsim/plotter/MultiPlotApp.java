package org.motion.ballsim.plotter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.math.geometry.Vector3D;
import org.motion.ballsim.Ball;
import org.motion.ballsim.OneCushionRuleSet;
import org.motion.ballsim.ShotFinder;
import org.motion.ballsim.Table;
import org.motion.ballsim.Utilities;

public class MultiPlotApp {

	static StaticPlot plot;
	
    public static void main(String[] args) 
    {
    	
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*6,-Ball.R*16,0)));
		t.ball(2).setFirstEvent(Utilities.getStationary(new Vector3D(-Ball.R*6,-Ball.R*6,0)));
		t.ball(3).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*5,-Ball.R*3,0)));
		ShotFinder finder = new ShotFinder(new OneCushionRuleSet(),t);		
		Table tResult = finder.FindBest(t.ball(1),25);

		Table t2 = new Table();
		t2.ball(1).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*6,-Ball.R*10,0)));
		t2.ball(2).setFirstEvent(Utilities.getStationary(new Vector3D(-Ball.R*6,-Ball.R*6,0)));
		t2.ball(3).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*5,-Ball.R*3,0)));
		ShotFinder finder2 = new ShotFinder(new OneCushionRuleSet(),t2);
		Table tResult2 = finder2.FindBest(t2.ball(1),26);

		Collection<Table> tables = new ArrayList<Table>();
		tables.add(tResult);
		tables.add(tResult2);
		
		plot = new StaticPlot(tables,50);
    	plot.draw();
    	
    }	

}
