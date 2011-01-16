package org.motion.ballsim.plotter;

import static org.junit.Assert.*;

import org.junit.Test;

public class BallSpotTest {

	@Test
	public void testGetOffsetX() {
		assertTrue("Initial pos at top",BallSpot.getOffsetX(0, 0) == 0);
	}

	@Test
	public void testGetOffsetY() {
		assertTrue("Initial pos at top",BallSpot.getOffsetY(0, 0) == 0);
	}

}
