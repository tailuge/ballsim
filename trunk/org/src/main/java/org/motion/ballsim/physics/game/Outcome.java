package org.motion.ballsim.physics.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.ball.Transition;

public class Outcome {

	final public int firstBallHit;
	final public List<Integer> ballsPotted = new ArrayList<Integer>();
	final public int cushionsBeforeSecondBall;
	final public int totalBallsHittingCushion;

	public Outcome(int firstBallHit, List<Integer> ballsPotted,
			int cushionsBeforeSecondBall, int totalBallsHittingCushion) {
		this.firstBallHit = firstBallHit;
		this.ballsPotted.addAll(ballsPotted);
		this.cushionsBeforeSecondBall = cushionsBeforeSecondBall;
		this.totalBallsHittingCushion = totalBallsHittingCushion;
	}

	public static Outcome evaluate(Table table) {
		return new Outcome(
				getFirstBallHit(table.ball(1)),
				getBallsPotted(table), 
				getCushionsBeforeSecondBall(table.ball(1)),
				getTotalBallsHittingCushion(table));
	}

	private static int getFirstBallHit(Ball ball) {
		for (Event e : ball.getAllEvents()) {
			if (e.type == Transition.Collision) {
				return e.otherBallId;
			}
		}
		return 0; // fix when proper type available
	}

	private static List<Integer> getBallsPotted(Table table) {
		final List<Integer> ballsPotted = new ArrayList<Integer>();
		for (Ball ball : table.balls()) {
			for (Event e : ball.getAllEvents()) {
				if (e.type == Transition.Potting) {
					ballsPotted.add(ball.id);
					break;
				}
			}
		}
		return ballsPotted;
	}

	private static int getTotalBallsHittingCushion(Table table) {
		int totalBallsHittingCushion = 0;
		for (Ball ball : table.balls()) {
			for (Event e : ball.getAllEvents()) {
				if (e.type == Transition.Cushion || e.type == Transition.KnuckleCushion) {
					totalBallsHittingCushion++;
					break;	
				}
			}
		}
		return totalBallsHittingCushion;
	}

	private static int getCushionsBeforeSecondBall(Ball ball) {
		
		int cushions = 0;
		Map<Integer,Integer> cannons = new HashMap<Integer,Integer>();
		
		for(Event e : ball.getAllEvents())
		{
			if (e.type == Transition.Cushion)
				cushions++;
			
			if (e.type == Transition.Collision)
			{	
				cannons.put(e.otherBallId, 1);
				if (cannons.size() == 2)
					return cushions;
			}			
		}
		return 0;
	}

}
