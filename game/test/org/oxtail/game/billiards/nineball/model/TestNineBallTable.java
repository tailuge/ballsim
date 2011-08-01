package org.oxtail.game.billiards.nineball.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardBallId;
import org.oxtail.game.billiards.model.BilliardsGameCategory;
import org.oxtail.game.billiards.nineball.model.NineBallTable;

public class TestNineBallTable {

	@Test
	public void testNineBallTable() {
		NineBallTable table = new NineBallTable();
		assertNotNull(table);
	}

	@Test
	public void testRack() {
		NineBallTable table = new NineBallTable();
		table.rack();
		// will this expect 9 or 10 ?
		assertEquals("expect all balls on table", 9, table
				.getBallsLeftOnTable().size());
	}

	@Test
	public void testBallCategory() {
		NineBallTable table = new NineBallTable();
		for (BilliardBall ball : table.getBallsLeftOnTable()) {
			assertEquals("expect nineball category", ball.getCategory()
					.getGameCategory(), BilliardsGameCategory.NineBall);
		}
	}

	@Test
	public void testIsBallsLeftOnTable() {
		NineBallTable table = new NineBallTable();
		assertTrue("expect balls on table", table.isBallsLeftOnTable());
	}

	@Test
	public void testGetNextBallToHit() {
		NineBallTable table = new NineBallTable();
		BilliardBall ball = table.getNextBallToHit();
		assertEquals("expect 1 ball is first to hit", ball.getCategory()
				.getBallCategory(), BilliardBallId.ONE);
	}

}
