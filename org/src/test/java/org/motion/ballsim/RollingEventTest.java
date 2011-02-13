package org.motion.ballsim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.State;



public class RollingEventTest {

	private Event e;
	private Event roll;
	private Event stationary;
	
	@Before
	public void setUp()
	{
		e = Utilities.getSimpleEvent();
		stationary = Utilities.getStationary();
	}

	@Test
	public final void testCopy() 
	{
		e.pos = Vector3D.PLUS_I;
		Event c = new Event(e);
		assertEquals(e.pos, c.pos);
		e.pos = Vector3D.MINUS_I;
		assertFalse(e.pos.equals(c.pos));
		
	}

	@Test
	public final void testUtilRolling() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I);		
		assertFalse("Angular velocity set",Double.isNaN(roll.angularVel.getNorm1()));		
		roll = Utilities.getRolling(Vector3D.ZERO);		
		assertFalse("Angular velocity set",Double.isNaN(roll.angularVel.getNorm1()));		
		assertEquals("Angular velocity zero",0.0,roll.angularVel.getNorm(),0.0);		

	}
	
	@Test
	public final void testAccRolling() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I);		
		assertEquals("Acceleration should oppose velocity when rolling",roll.acceleration().normalize(),roll.vel.negate());

		roll = Utilities.getRolling(new Vector3D(Double.MIN_NORMAL,0,0));
		assertFalse("At halting point acceleration should behave",Double.isNaN(roll.acceleration().getNorm()));

		roll = Utilities.getRolling(Vector3D.ZERO);
		assertFalse("At halting point acceleration should behave",Double.isNaN(roll.acceleration().getNorm()));		
		
		roll = Utilities.getRolling(Vector3D.PLUS_I);		
		assertEquals("Acceleration magnitude independent of velocity",
				roll.acceleration(),
				Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(2.0)).acceleration());

		assertEquals("Acceleration magnitude independent of velocity",
				roll.acceleration().getNorm(),
				Utilities.getRolling(Vector3D.PLUS_J).acceleration().getNorm(),
				0.0);

		assertEquals("Acceleration of stationary expected 0",Vector3D.ZERO,Utilities.getStationary().acceleration());
	}

	@Test
	public final void testTimeToStopRolling() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I);		
		double t = roll.timeToNext();
		assertTrue("Rolling ball will stop", t > 0);

		roll = Utilities.getRolling(Vector3D.ZERO);		
		t = roll.timeToNext();
		assertTrue("Stationary ball stays stopped", t == 0);

		t = stationary.timeToNext();
		assertTrue("Stationary ball stays stopped", t == 0);
		
	}

	@Test
	public final void testStationaryEventFromRolling() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I);		
		Event next = roll.next();
		assertEquals("Is stationary state",State.Stationary,next.state);
		assertEquals("Is not moving",0.0,next.vel.getNorm(),0.0);
		assertEquals("Is not spinning",0.0,next.angularVel.getNorm(),0.0);
		assertTrue("Position changed",Vector3D.distance(next.pos, roll.pos) > 0);

	}

	@Test
	public final void testAdvanceDelta() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I);
		double troll = roll.timeToNext();		
		Event i1 = roll.advanceDelta(troll/2.0);
		Event i2 = roll.advanceDelta(troll);
		
		assertTrue("More distance travelled in first half of time",
				Vector3D.distance(roll.pos, i1.pos) > Vector3D.distance(i1.pos, i2.pos)); 
		assertTrue("Velocity reduces", i1.vel.getNorm() > i2.vel.getNorm());
		assertTrue("Angular velocity reduces", i1.angularVel.getNorm() > i2.angularVel.getNorm());
		assertEquals("Expect still rolling state half way",State.Rolling,State.deriveStateOf(i1));
		assertEquals("Expect stationary at end",State.Stationary,State.deriveStateOf(i2));
	}
	
	@Test
	public final void testInspector() 
	{
		roll = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(100));
		double troll = roll.timeToNext();
		
		double dt = 0;
		while(dt < troll)
		{
			dt += troll/5.0;
			Event interpolated = roll.advanceDelta(dt);
			assertNotNull(interpolated);
			System.out.println(interpolated);
		}		
	}
	
	@Test
	public final void testStationaryStopsSpinning() 
	{
		roll = Utilities.getRollingWithSideSpin(Vector3D.PLUS_I,1);		
		Event next = roll.next();
		assertEquals("Is stationary state",State.Stationary,next.state);
		assertEquals("Is not moving",0.0,next.vel.getNorm(),0.0);
		assertEquals("Is not spinning",0.0,next.angularVel.getNorm(),0.0);
		assertEquals("Is not side spinning",0.0,next.sidespin.getNorm(),0.0);
	}

}
