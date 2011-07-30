package org.oxtail.game.billiards.model;

import java.util.ArrayList;
import java.util.List;

public class BilliardBall {

	private final BilliardBallCategory category;
	private int cushionCollisions;
	private BillardBallTableState tableState;
	/**
	 * struck directly by cue ball 
	 */
	private boolean struckByCueBall;
	
	public BilliardBall(BilliardBallCategory category) {
		this.category = category;
		rack();
	}

	public int getCushionCollisions() {
		return cushionCollisions;
	}

	public void setCushionCollisions(int cushionCollisions) {
		this.cushionCollisions = cushionCollisions;
	}

	public BillardBallTableState getTableState() {
		return tableState;
	}

	public void setTableState(BillardBallTableState tableState) {
		this.tableState = tableState;
	}

	public BilliardBallCategory getCategory() {
		return category;
	}
	
	public static <T extends BilliardBallCategory> List<BilliardBall> create(T...  categories){
		List<BilliardBall> balls = new ArrayList<BilliardBall>();
		for(BilliardBallCategory category : categories) 
			balls.add(new BilliardBall(category));
		return balls;
	}

	public final void rack() {
		tableState = BillardBallTableState.OnTable;
		cushionCollisions = 0;
	}
}
