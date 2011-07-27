package org.oxtail.game.billiards.model;

import static junit.framework.Assert.*;
import org.junit.Test;
import org.oxtail.game.billiards.model.BilliardsGameCategory;

/**
 * Tests categories of Billiards types games
 * @author liam knox
 */
public class TestBilliardsGameCategory {

	@Test 
	public void testValueOf() {
		assertEquals(BilliardsGameCategory.NineBall,BilliardsGameCategory.valueOf("NineBall"));
	}
	
}
