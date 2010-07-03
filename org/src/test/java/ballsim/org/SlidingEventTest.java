package ballsim.org;

import static org.junit.Assert.*;

import org.apache.commons.math.geometry.Vector3D;
import org.junit.Before;
import org.junit.Test;

public class SlidingEventTest {

	private Event masse;
	private Event slide;
	
	@Before
	public void setUp()
	{
		slide = Utilities.getSliding(Vector3D.PLUS_I, Vector3D.ZERO);
		masse = Utilities.getSliding(Vector3D.ZERO, Vector3D.PLUS_I);
	}


	@Test
	public final void testGetAccelerationVector() {
		assertTrue("Sliding ball has acceleration",slide.getAccelerationVector().getNorm() > 0);
		assertTrue("Sliding masse ball has acceleration",masse.getAccelerationVector().getNorm() > 0);
		assertEquals("Sliding,masse have same magnitude of acceleration",slide.getAccelerationVector().getNorm() , masse.getAccelerationVector().getNorm(), 0.0);
	}

	@Test
	public final void testGetAngularAccelerationVector() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAdvanceRollingDelta() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testTimeToStopRolling() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testStationaryEventFromRolling() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testInfereState() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testToString() {
		fail("Not yet implemented"); // TODO
	}

}
