package org.motion.ballsim;

public class OneCushionRuleSet implements IRuleSet {

	@Override
	public boolean valid(Table table) {
		return Cushion.validPosition(table)  && (Collision.validPosition(table));
	}

	@Override
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
					return (cushions >= 1);
			}			
		}
		return false;
	}

	@Override
	public double rank(Table table, Ball ball) {

		if (scores(table,ball))
			return 1;
		
		return 0;
	}

}
