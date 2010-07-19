package org.motion.ballsim;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math.geometry.Vector3D;

import com.google.common.collect.Lists;

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

	public Collection<Event> GenerateRadialEvents(Ball ball, int segments)
	{
		Vector3D pos = ball.lastEvent().pos;
		
		Collection<Event> radialEvents = Lists.newArrayList();
		for(int i=0; i<segments; i++)
		{
			Vector3D dir = new Vector3D(2.0 * Math.PI * (double)i/(double)segments,0);
			radialEvents.add(UtilEvent.hit(pos, dir,160, 0)); 			
		}
		
		return radialEvents;
	}

	public Table FindBest(Ball ball, int segments)
	{
		Map<Event,Double> ranks = RankEvents(ball,GenerateRadialEvents(ball, segments));

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
