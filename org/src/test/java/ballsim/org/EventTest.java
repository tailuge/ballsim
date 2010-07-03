package ballsim.org;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.math.geometry.Vector3D;

public class EventTest extends TestCase {

	private Event e;
	private Event roll;
	private Event stationary;
	
	protected void setUp()
	{
		e = Event.getSimpleEvent();
		stationary = Utilities.getStationary();
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
		
		roll = Utilities.getRolling(Vector3D.PLUS_I);		
		Assert.assertEquals("Acceleration magnitude independent of velocity",
				roll.getAccelerationVector(),
				Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(2.0)).getAccelerationVector());

		Assert.assertEquals("Acceleration magnitude independent of velocity",
				roll.getAccelerationVector().getNorm(),
				Utilities.getRolling(Vector3D.PLUS_J).getAccelerationVector().getNorm() );

	}

	public final void testTimeToStopRolling() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I);		
		double t = roll.timeToStopRolling();
		Assert.assertTrue("Rolling ball will stop", t > 0);

		roll = Utilities.getRolling(Vector3D.ZERO);		
		t = roll.timeToStopRolling();
		Assert.assertTrue("Stationary ball stays stopped", t == 0);

		t = stationary.timeToStopRolling();
		Assert.assertTrue("Stationary ball stays stopped", t == 0);
		
	}

	public final void testStationaryEventFromRolling() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I);		
		Event next = roll.stationaryEventFromRolling();
		Assert.assertEquals("Is stationary state",State.Stationary,next.state);
		Assert.assertEquals("Is not moving",0.0,next.vel.getNorm());
		Assert.assertEquals("Is not spinning",0.0,next.angularVel.getNorm());
		Assert.assertTrue("Position changed",Vector3D.distance(next.pos, roll.pos) > 0);

	}

	
	public final void testAdvanceDelta() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I);
		double troll = roll.timeToStopRolling();		
		Event i1 = roll.advanceRollingDelta(troll/2.0);
		Event i2 = roll.advanceRollingDelta(troll);
		Assert.assertTrue("More distance travelled in first half of time",
				Vector3D.distance(roll.pos, i1.pos) > Vector3D.distance(i1.pos, i2.pos)); 
		Assert.assertTrue("Velocity reduces", i1.vel.getNorm() > i2.vel.getNorm());
		Assert.assertTrue("Angular velocity reduces", i1.angularVel.getNorm() > i2.angularVel.getNorm());

		i1.infereState();
		Assert.assertEquals("Expect still rolling state half way",State.Rolling,i1.state);
		i2.infereState();
		Assert.assertEquals("Expect stationary at end",State.Stationary,i2.state);
	}

	public final void testInspect() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(100));
		double troll = roll.timeToStopRolling();
		
		double dt = 0;
		while(dt < troll)
		{
			dt += troll/5.0;
			Event interpolated = roll.advanceRollingDelta(dt);
			Assert.assertNotNull(interpolated);
			System.out.println(interpolated);
		}
		
	}
}
