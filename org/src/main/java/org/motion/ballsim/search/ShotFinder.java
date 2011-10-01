package org.motion.ballsim.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.util.Logger;
import org.motion.ballsim.util.UtilEvent;


public class ShotFinder {

	private final static Logger logger = new Logger("ShotFinder",false);

	private IRuleSet rule;

	private final Table table;
	
	public ShotFinder(IRuleSet rule, Table table)
	{
		this.table = table;
		this.rule = rule;
	}

	public Map<Event,Double> rankEvents(Ball ball, Collection<Event> events)
	{
		Map<Event,Double> ranks = new HashMap<Event,Double>();
		
		for(Event event : events)
		{
			table.reset();
			ball.setFirstEvent(event);
			table.generateSequence();
			ranks.put(event, rule.rank(table, ball));
		}
		
		return ranks;
	}



	public Table findBest(Ball ball, int segments)
	{
		Map<Event,Double> ranks = rankEvents(ball,UtilEvent.generateRadialEvents(ball.lastEvent().pos, segments, 200, 0.5));

		Entry<Event,Double> best = null;
		
		for(Entry<Event,Double> entry: ranks.entrySet())
		{
			if ((best == null) || (entry.getValue() > best.getValue()))
				best = entry;
		}
		
		table.reset();
		ball.setFirstEvent(best.getKey());
		table.generateSequence();
		
		Collection<Double> d = ranks.values();
		
		logger.info("ranks:{}",d);
		
		return table;
		
	}
}
