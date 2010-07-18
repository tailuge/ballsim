package org.motion.ballsim;

public class OneCushionRuleSet implements IRuleSet {

	public boolean valid(Table table) {
		return Cushion.validPosition(table)  && (Collision.validPosition(table));
	}

	public boolean scores(Table table, Ball ball) {
		
		int cushions = 0;
		int cannons = 0;
		for(Event e : ball.getAllEvents())
		{
			if (e.type == EventType.Cushion)
				cushions++;
			
			// TODO: need to check it cannon with new ball.

			if (e.type == EventType.Collision)
			{
				cannons++;
				if (cannons == 2)
					return (cushions >= 3); // for a minute 3 cushion !
			}			
		}
		return false;
	}

	public double rank(Table table, Ball ball) {

		if (scores(table,ball))
			return 1;

		int cushions = 0;
		int cannons = 0;
		for(Event e : ball.getAllEvents())
		{
			if (e.type == EventType.Cushion)
				cushions++;
			
			// TODO: need to check it cannon with new ball.

			if (e.type == EventType.Collision)
				cannons++;
			
		}
		return 0.1 * (double)cannons - 0.01 * (double)cushions;
	}

}
