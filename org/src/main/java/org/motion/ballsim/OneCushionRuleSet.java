package org.motion.ballsim;

import java.util.Map;

import com.google.common.collect.Maps;

public class OneCushionRuleSet implements IRuleSet {

	public boolean valid(Table table) {
		return Cushion.validPosition(table)  && (Collision.validPosition(table));
	}

	public boolean scores(Table table, Ball ball) {
		
		int cushions = 0;
		Map<Integer,Integer> cannons = Maps.newHashMap();
		
		for(Event e : ball.getAllEvents())
		{
			if (e.type == EventType.Cushion)
				cushions++;
			
			if (e.type == EventType.Collision)
			{	
				cannons.put(e.otherBallId, 1);
				
				if (cannons.keySet().size() == 2)
					return (cushions >= 3); // for a minute 3 cushion !
			}			
		}
		return false;
	}

	public double rank(Table table, Ball ball) {

		if (scores(table,ball))
			return 1;

		int cushions = 0;

		Map<Integer,Integer> cannons = Maps.newHashMap();

		for(Event e : ball.getAllEvents())
		{
			if (e.type == EventType.Cushion)
				cushions++;
			
			if (e.type == EventType.Collision)
			{	
				cannons.put(e.otherBallId, 1);				
			}			
		}
		return 0.1 * (double)cannons.keySet().size() - 0.01 * (double)cushions;
	}

}
