package org.motion.ballsim.plotter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.math.geometry.Vector3D;
import org.motion.ballsim.Ball;
import org.motion.ballsim.Event;
import org.motion.ballsim.Table;
import org.motion.ballsim.ThreeCushionRuleSet;
import org.motion.ballsim.UtilEvent;
import org.motion.ballsim.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class MultiPlotApp {

	static StaticPlot plot;
	
	private final static Logger logger = LoggerFactory.getLogger(MultiPlotApp.class);

    public static void main(String[] args) 
    {
		Collection<Table> tables = new ArrayList<Table>();

		Collection<Event> events = Lists.newArrayList();

		Event target2 = Utilities.getStationary(new Vector3D(Ball.R * 8.5,-Ball.R * 16, 0));
		Event target3 = Utilities.getStationary(new Vector3D(Ball.R * 3.9,+Ball.R * 9, 0));
		
		//events.addAll(UtilEvent.generateImpactingEvents(Vector3D.ZERO, target3.pos, 1000, 270, -0.8));
		//events.addAll(UtilEvent.generateImpactingEvents(Vector3D.ZERO, target3.pos, 90, 270, 0.8));
		events.addAll(UtilEvent.generateImpactingEvents(Vector3D.ZERO, target2.pos, 1090, 270, -0.9));
 
		ThreeCushionRuleSet rule = new ThreeCushionRuleSet();
		
    	for(Event e : events)
    	{
			Table t = new Table();
			t.ball(1).setFirstEvent(e);			
			t.ball(2).setFirstEvent(target2);
			t.ball(3).setFirstEvent(target3);
			t.generateSequence();
			if (rule.scores(t, t.ball(1)))
				tables.add(t);		
    	}
		
		plot = new StaticPlot(tables,90);
    	plot.draw();
    	
    }	

    public static void radialInvestigation()
    {
		Collection<Table> tables = new ArrayList<Table>();

		Collection<Event> events = Lists.newArrayList();

		double h = -1.5;
		while (h < 1.5)
		{
			events.addAll(UtilEvent.generateRadialEvents(Vector3D.ZERO, 24+(int)h*2, 84, h));
			h += 0.5;
		}
 
    	for(Event e : events)
    	{
			Table t = new Table();
			t.ball(1).setFirstEvent(e);			
			t.ball(2).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R * 1.5,-Ball.R * 9, 0)));
			t.ball(3).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R * 3.9,+Ball.R * 9, 0)));
			t.generateSequence();
			tables.add(t);		
    	}
		
		plot = new StaticPlot(tables,20);
    	plot.draw();
    }
    
    public static void hitInvestigation()
    {
		Collection<Table> tables = new ArrayList<Table>();
    	double h = -2.5;
    	while(h < 2.5)
    	{
    		logger.info("h:{}",h);
			Table t = new Table();
			t.ball(1).setFirstEvent(UtilEvent.hit(Vector3D.ZERO, Vector3D.MINUS_J, 120,h));			
			t.ball(2).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R,-Ball.R * 6, 0)));
			t.generateSequence();
			tables.add(t);		
			h += 0.3;
    	}
		
		plot = new StaticPlot(tables,50);
    	plot.draw();
    	
    }
}
