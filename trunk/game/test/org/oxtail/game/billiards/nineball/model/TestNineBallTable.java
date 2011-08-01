package org.oxtail.game.billiards.nineball.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardBallId;
import org.oxtail.game.billiards.model.BilliardBallTableState;
import org.oxtail.game.billiards.model.BilliardsGameCategory;
import org.oxtail.game.billiards.nineball.model.NineBallTable;

public class TestNineBallTable {

	private NineBallTable table;
	
	@Before
	public void setUp() throws Exception {
		table = new NineBallTable();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testNineBallTable() {
		assertNotNull(table);
	}

	@Test
	public void testRack() {
		table.rack();
		// will this expect 9 or 10 ?
		assertEquals("expect all balls on table", 9, table
				.getBallsLeftOnTable().size());
	}

	@Test
	public void testBallCategory() {
		for (BilliardBall ball : table.getBallsLeftOnTable()) {
			assertEquals("expect nineball category", ball.getCategory()
					.getGameCategory(), BilliardsGameCategory.NineBall);
		}
	}

	@Test
	public void testIsBallsLeftOnTable() {
		assertTrue("expect balls on table", table.isBallsLeftOnTable());
	}

	@Test
	public void testGetNextBallToHit() {
		BilliardBall ball = table.getNextBallToHit();
		assertEquals("expect 1 ball is first to hit", ball.getCategory()
				.getBallCategory(), BilliardBallId.ONE);
	}

	@Ignore
	public void testGetNextBallToHitNone() {
		
		clearTable();
		
		// what is expected here? for several of the queries 
		// there should be a BallNotFound named exception.
		
		BilliardBall ball = table.getNextBallToHit();
		
	}

	private void clearTable() {
		for (BilliardBall ball : table.getBallsLeftOnTable()) {
			ball.setTableState(BilliardBallTableState.OffTable);
		}		
	}
	
	@Test (expected=IllegalStateException.class)
	public void testGetBallStruckByCueBall() {
		table.getBallStruckByCueBall();
	}
	
	
}
