package org.motion.ballsim.physics;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.ball.State;
import org.motion.ballsim.physics.gwtsafe.Vector3D;
import org.motion.ballsim.physics.util.UtilEvent;

public class SlidingEventTest 
{

	private Event masse;
	private Event slide;
	private Event slideSpinning;
	
	@Before
	public void setUp()
	{
		slide = Utilities.getSliding(Vector3D.PLUS_I, Vector3D.ZERO);
		masse = Utilities.getSliding(Vector3D.ZERO, Vector3D.PLUS_I);
		slideSpinning = Utilities.getSlidingWithSideSpin(Vector3D.PLUS_I, Vector3D.ZERO,1);
	}


	@Test
	public final void testGetAccelerationVector() 
	{
		assertTrue("Sliding ball has acceleration",slide.acceleration().getNorm() > 0);
		assertTrue("Sliding masse ball has acceleration",masse.acceleration().getNorm() > 0);
		assertEquals("Sliding,masse have same magnitude of acceleration",slide.acceleration().getNorm() , masse.acceleration().getNorm(), 0.000001);
		assertTrue("Sliding acceleratoin opposes vel",slide.vel.normalize().add(slide.acceleration().normalize()).getNormSq() < 0.00001);
	}

	@Test
	public final void testGetAngularAccelerationVector() 
	{
		assertTrue("Sliding ball has angular acceleration",slide.angularAcceleration().getNorm() > 0);
		assertTrue("Sliding masse ball has angular acceleration",masse.angularAcceleration().getNorm() > 0);
		assertEquals("Sliding,masse have same magnitude of angular acceleration",slide.angularAcceleration().getNorm() , masse.angularAcceleration().getNorm(), 0.000001);
	}


	@Test
	public final void testTimeToNaturalRollEquilibrium() 
	{
		assertTrue("Sliding will reach equilibrium",slide.timeToNext() > 0);
		assertTrue("Sliding masse will reach equilibrium",masse.timeToNext() > 0);
	}

	@Test
	public final void testSideSpinUnchanged() 
	{
		Event next = slideSpinning.next();
		assertEquals("Sidespin unchanged",slideSpinning.sidespin , next.sidespin);
		
	}

	@Test
	public final void testRollingEventFromSliding() 
	{

		System.out.println("SLIDE");
		System.out.println("start:"+slide);
		
		System.out.println("acc  :"+slide.acceleration());
		System.out.println("angac:"+slide.angularAcceleration());

		Event roll = slide.next();
		assertEquals("Expect roll",State.Rolling,roll.state);

		
		System.out.println("end  :"+roll);
		System.out.println("expct:"+Utilities.getRolling(roll.vel));
		
		assertEquals("Expect conditions match rolling",State.Rolling,State.deriveStateOf(roll));
		
		roll = masse.next();
		System.out.println("MASSE");
		System.out.println("start:"+masse);
		
		System.out.println("acc  :"+masse.acceleration());
		System.out.println("angac:"+masse.angularAcceleration());
		assertEquals("Expect roll",State.Rolling,roll.state);

		System.out.println("end  :"+roll);
		System.out.println("expct:"+Utilities.getRolling(roll.vel));

		assertEquals("Expect conditions match rolling",State.Rolling,State.deriveStateOf(roll));

	}

	@Test
	public final void testInfereState() 
	{
		assertEquals("Expect sliding state infered as sliding",State.Sliding, State.deriveStateOf(slide));
		assertEquals("Expect masse state infered as sliding",State.Sliding, State.deriveStateOf(masse));
	}

	@Test
	public final void testToString() 
	{
		assertTrue("To string works",slide.toString().length() > 0);
	}

	
	@Test
	public final void testInspector() 
	{
		slide = Utilities.getSliding(Vector3D.PLUS_I.scalarMultiply(1000),Vector3D.ZERO);
		double tslide = slide.timeToNext();
		
		double dt = 0;
		while(dt < tslide)
		{
			dt += tslide/5.0;
			Event interpolated = slide.advanceDelta(dt);
			assertNotNull(interpolated);
			
			System.out.println(interpolated);
		}		
	}
	
	
	@Test
	public final void testAlreadyRolling() 
	{
		Event e = UtilEvent.hit(Vector3D.ZERO, Vector3D.MINUS_J, 100,0.9999999999999999);
		double t = e.timeToNext();
		assertTrue("should roll instantly : t is "+t,t>0);
	}
}
