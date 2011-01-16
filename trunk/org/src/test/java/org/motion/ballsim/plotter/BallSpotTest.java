package org.motion.ballsim.plotter;

import static org.junit.Assert.*;

import org.junit.Test;

public class BallSpotTest {

	@Test
	public void testStartAtTop() {
		assertTrue("Initial pos at top",BallSpot.getOffsetX(0, 0) == 0);
		assertTrue("Initial pos at top",BallSpot.getOffsetY(0, 0) == 0);
	}

	@Test
	public void testAtEdgeStationary() {
		assertEquals("spot at edge does not move",
				BallSpot.getOffsetY(Math.PI/2, 0) , BallSpot.getOffsetY(Math.PI/2, 1),0.00001);		
	}

}
