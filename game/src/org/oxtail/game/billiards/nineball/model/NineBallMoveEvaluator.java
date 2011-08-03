package org.oxtail.game.billiards.nineball.model;

/**
 * Evaluation of valid nine ball moves
 * @author luke taylor
 */
public class NineBallMoveEvaluator {

	/** The table before the move is played */
	private final NineBallTable table;
	
	/** The move to play */
	private final NineBallMove move;
	
	public NineBallMoveEvaluator(NineBallTable table, NineBallMove move) {
		this.table = table;
		this.move = move;
	}

	/**
	 * We should be valid if the ball expected to be hit is the one hit 
	 */
	public boolean isBallHitValid() {
		if(move.isAnyBallHitByCueBall()) {
			move.getFirstBallStruckByCueBall().isSame(table.getNextBallToHit());
		}
		return false;
	}
}
