package ballsim.org;

import org.apache.commons.math.geometry.Vector3D;

import junit.framework.Assert;
import junit.framework.TestCase;

public class EventTest extends TestCase {


	public final void testCopy() {
		Event e = Event.getSimpleEvent();
		e.pos = Vector3D.PLUS_I;
		Event c = new Event(e);
		Assert.assertEquals(e.pos, c.pos);
		e.pos = Vector3D.MINUS_I;
		Assert.assertFalse(e.pos.equals(c.pos));
		
	}

	public final void testGetRollingAccelerationVector() {
		fail("Not yet implemented"); // TODO
	}

	public final void testAdvanceRollingDelta() {
		fail("Not yet implemented"); // TODO
	}

	public final void testTimeToStopRolling() {
		fail("Not yet implemented"); // TODO
	}

	public final void testStationaryEvent() {
		fail("Not yet implemented"); // TODO
	}

}
