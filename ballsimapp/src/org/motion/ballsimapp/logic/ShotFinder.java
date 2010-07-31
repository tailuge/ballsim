package org.motion.ballsimapp.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



public class ShotFinder {


	private IRuleSet rule;

	private final Table table;
	
	public ShotFinder(IRuleSet rule, Table table)
	{
		this.table = table;
		this.rule = rule;
	}

	public Map<Event,Double> RankEvents(Ball ball, Collection<Event> events)
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



	public Table FindBest(Ball ball, int segments)
	{
		Map<Event,Double> ranks = RankEvents(ball,UtilEvent.generateRadialEvents(ball.lastEvent().pos, segments, 200, 0.5));

		Entry<Event,Double> best = null;
		
		for(Entry<Event,Double> entry: ranks.entrySet())
		{
			if ((best == null) || (entry.getValue() > best.getValue()))
				best = entry;
		}
		
		table.reset();
		ball.setFirstEvent(best.getKey());
		table.generateSequence();
		
		
		
		return table;
		
	}
}
