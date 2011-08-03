package org.oxtail.game.billiards.nineball.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardBallNotFoundException;
import org.oxtail.game.billiards.model.BilliardBallTableState;
import org.oxtail.game.billiards.model.BilliardsGameCategory;

public class TestNineBallTable {

	private NineBallTable table;
	
	@Before
	public void setUp() throws Exception {
		table = new NineBallTable();
	}
	
	@Test
	public void testNineBallTable() {
		assertNotNull(table);
	}

	@Test
	public void testRack() {
		clearTable();
		assertEquals("expect no balls on table", 0, table
				.getBallsLeftOnTable().size());
		table.rack();
		assertEquals("expect all balls on table", 9, table
				.getBallsLeftOnTable().size());
	}

	@Test
	public void testGetBall() throws BilliardBallNotFoundException {		
		assertNotNull("expect a ball", table.getBall(NineBallBallCategory.NINE_BALL));
	}

	@Test (expected=BilliardBallNotFoundException.class)
	public void testGetBallNotFound() throws BilliardBallNotFoundException {		
		table.getBall(NineBallBallCategory.CUE_BALL);
	}

	@Test
	public void testIsBallsLeftOnTable() {
		assertTrue("expect balls on table", table.isBallsLeftOnTable());
	}

	@Test
	public void testBallsLeftOnTableCategory() {
		for (BilliardBall ball : table.getBallsLeftOnTable()) {
			assertEquals("expect nineball category", ball.getCategory()
					.getGameCategory(), BilliardsGameCategory.NineBall);
		}
	}

	@Test
	public void testBallsLeftOnTableNotCueBall() throws BilliardBallNotFoundException {	
		assertTrue("expect a nine ball", 
				table.getBallsLeftOnTable().contains(table.getBall(NineBallBallCategory.NINE_BALL)));
		assertFalse("expect no cue ball", 
				table.getBallsLeftOnTable().contains(table.getCueBall()));
	}


	@Test
	public void testGetNextBallToHit() throws BilliardBallNotFoundException {
		assertEquals("expect 1 ball is first to hit", 
				table.getBall(NineBallBallCategory.ONE_BALL) , 
				table.getNextBallToHit());
	}

	@Test (expected=BilliardBallNotFoundException.class)
	public void testGetNextBallToHitNone() throws BilliardBallNotFoundException {
		clearTable();
		table.getNextBallToHit();
	}

	private void clearTable() {
		for (BilliardBall ball : table.getBallsLeftOnTable()) {
			ball.setTableState(BilliardBallTableState.OffTable);
		}		
	}
	

	
	
}
