package ballsim.org;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.math.geometry.Vector3D;

public class EventTest extends TestCase {

	private Event e;
	private Event roll;
	
	protected void setUp()
	{
		e = Event.getSimpleEvent();
	}

	public final void testCopy() 
	{
		e.pos = Vector3D.PLUS_I;
		Event c = new Event(e);
		Assert.assertEquals(e.pos, c.pos);
		e.pos = Vector3D.MINUS_I;
		Assert.assertFalse(e.pos.equals(c.pos));
		
	}

	public final void testUtilRolling() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I);		
		Assert.assertFalse("Angular velocity set",Double.isNaN(roll.angularVel.getNorm1()));		
		roll = Utilities.getRolling(Vector3D.ZERO);		
		Assert.assertFalse("Angular velocity set",Double.isNaN(roll.angularVel.getNorm1()));		
		Assert.assertEquals("Angular velocity zero",0.0,roll.angularVel.getNorm());		

	}
	
	public final void testAccRolling() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I);		
		Assert.assertEquals("Acceleration should oppose velocity when rolling",roll.getAccelerationVector().normalize(),roll.vel.negate());

		roll = Utilities.getRolling(new Vector3D(Double.MIN_NORMAL,0,0));
		Assert.assertFalse("At halting point acceleration should behave",Double.isNaN(roll.getAccelerationVector().getNorm()));

		roll = Utilities.getRolling(Vector3D.ZERO);
		Assert.assertFalse("At halting point acceleration should behave",Double.isNaN(roll.getAccelerationVector().getNorm()));		
	}

	public final void testTimeToStopRolling() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I);		
		double t = roll.timeToStopRolling();
		Assert.assertTrue("Rolling ball will stop", t > 0);
	}

	public final void testAdvanceRollingDelta() 
	{
		
	}


	public final void testStationaryEvent() 
	{
		
	}

}
