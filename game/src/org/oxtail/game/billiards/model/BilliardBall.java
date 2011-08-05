package org.oxtail.game.billiards.model;

import java.util.ArrayList;
import java.util.List;

public class BilliardBall {

	private final BilliardBallCategory category;
	private int cushionCollisions;
	private BilliardBallTableState tableState;
	/**
	 * struck directly by cue ball 
	 */
	private boolean struckByCueBall;
	private BilliardsPocket pottedIn;
	
	public BilliardBall(BilliardBallCategory category) {
		this.category = category;
		rack();
	}

	public BilliardsPocket getPocket() {
		return pottedIn;
	}
	
	public int getCushionCollisions() {
		return cushionCollisions;
	}

	public void setCushionCollisions(int cushionCollisions) {
		this.cushionCollisions = cushionCollisions;
	}

	public void setStruckByCueBall() {
		this.struckByCueBall = true;
	}

	public BilliardBallTableState getTableState() {
		return tableState;
	}

	public void setTableState(BilliardBallTableState tableState) {
		this.tableState = tableState;
	}

	public BilliardBallCategory getCategory() {
		return category;
	}
	
	public static <T extends BilliardBallCategory> List<BilliardBall> create(Iterable<T>  categories){
		List<BilliardBall> balls = new ArrayList<BilliardBall>();
		for(BilliardBallCategory category : categories) 
			balls.add(new BilliardBall(category));
		return balls;
	}

	public final void rack() {
		tableState = BilliardBallTableState.OnTable;
		cushionCollisions = 0;
	}
	
	public boolean isPotted() {
		return tableState == BilliardBallTableState.Potted;
	}

	public boolean isOnTable() {
		return tableState == BilliardBallTableState.OnTable;
	}

	public boolean isStruckByCueBall() {
		return struckByCueBall;
	}
	
	public boolean isSame(BilliardBall ball) {
		return ball.getCategory() == getCategory();
	}

	public void pot(BilliardsPocket pocket) {
		pottedIn = pocket;
	}

	@Override
	public String toString() {
		return "BilliardBall [category=" + category + ", cushionCollisions="
				+ cushionCollisions + ", tableState=" + tableState
				+ ", struckByCueBall=" + struckByCueBall + ", pottedIn="
				+ pottedIn + "]";
	}


}
