package org.motion.ballsim.search;

import java.util.HashMap;
import java.util.Map;

import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Collision;
import org.motion.ballsim.physics.Cushion;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.EventType;


public class ThreeCushionRuleSet implements IRuleSet {

	public boolean valid(Table table) {
		return Cushion.validPosition(table)  && (Collision.validPosition(table));
	}

	public boolean scores(Table table, Ball ball) {
		
		int cushions = 0;
		Map<Integer,Integer> cannons = new HashMap<Integer,Integer>();
		
		for(Event e : ball.getAllEvents())
		{
			if (e.type == EventType.Cushion)
				cushions++;
			
			if (e.type == EventType.Collision)
			{	
				cannons.put(e.otherBallId, 1);
				
				if (cannons.keySet().size() == 2)
					return (cushions >= 3); 
			}			
		}
		return false;
	}

	public double rank(Table table, Ball ball) {

		int cushions = 0;
		boolean firstCannon = false;
		
		Map<Integer,Integer> cannons = new HashMap<Integer,Integer>();

		for(Event e : ball.getAllEvents())
		{
			if (e.type == EventType.Cushion)
				cushions++;
			
			if (e.type == EventType.Collision)
			{	
				cannons.put(e.otherBallId, 1);		
				if (cannons.size() == 2) 
				{
					if (firstCannon == false)
					{
						if (cushions >= 3)
							return 1.0 - 0.01*(double)cushions;
					}
					else 
						firstCannon = true;
				}
			}			
		}
		if (cannons.size() == 1)
			return 0.5 - 0.01 * (double)cushions;
		return 0.01 * (double)cushions;
		
	}

}
