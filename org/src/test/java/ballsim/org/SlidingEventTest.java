package ballsim.org;

import static org.junit.Assert.*;

import org.apache.commons.math.geometry.Vector3D;
import org.junit.Before;
import org.junit.Test;

public class SlidingEventTest 
{

	private Event masse;
	private Event slide;
	
	@Before
	public void setUp()
	{
		slide = Utilities.getSliding(Vector3D.PLUS_I, Vector3D.ZERO);
		masse = Utilities.getSliding(Vector3D.ZERO, Vector3D.PLUS_I);
	}


	@Test
	public final void testGetAccelerationVector() 
	{
		assertTrue("Sliding ball has acceleration",slide.getAccelerationVector().getNorm() > 0);
		assertTrue("Sliding masse ball has acceleration",masse.getAccelerationVector().getNorm() > 0);
		assertEquals("Sliding,masse have same magnitude of acceleration",slide.getAccelerationVector().getNorm() , masse.getAccelerationVector().getNorm(), 0.0);
		assertEquals("Sliding acceleratoin opposes vel",slide.vel.normalize().negate(),slide.getAccelerationVector().normalize());
	}

	@Test
	public final void testGetAngularAccelerationVector() 
	{
		assertTrue("Sliding ball has angular acceleration",slide.getAngularAccelerationVector().getNorm() > 0);
		assertTrue("Sliding masse ball has angular acceleration",masse.getAngularAccelerationVector().getNorm() > 0);
		assertEquals("Sliding,masse have same magnitude of angular acceleration",slide.getAngularAccelerationVector().getNorm() , masse.getAngularAccelerationVector().getNorm(), 0.0);
	}

	@Test
	public final void testAdvanceDelta() 
	{
		
	}

	@Test
	public final void testTimeToNaturalRollEquilibrium() 
	{
		assertTrue("Sliding will reach equilibrium",slide.timeToNaturalRollEquilibrium() > 0);
		assertTrue("Sliding masse will reach equilibrium",masse.timeToNaturalRollEquilibrium() > 0);
	}

	@Test
	public final void testRollingEventFromSliding() 
	{
		Event roll = slide.rollingEventFromSliding();
		assertEquals("Expect roll",State.Rolling,roll.state);

		roll.infereState();
		assertEquals("Expect conditions match rolling",State.Rolling,roll.state);
		
		roll = masse.rollingEventFromSliding();
		assertEquals("Expect roll",State.Rolling,masse.state);

		roll.infereState();
		assertEquals("Expect conditions match rolling",State.Rolling,roll.state);

	}

	@Test
	public final void testInfereState() {
		slide.infereState();
		masse.infereState();
		assertEquals("Expect sliding state infered as sliding",State.Sliding, slide.state);
		assertEquals("Expect masse state infered as sliding",State.Sliding, masse.state);
	}

	@Test
	public final void testToString() 
	{
		assertTrue("To string works",slide.toString().length() > 0);
	}

	
	@Test
	public final void testInspector() 
	{
		slide = Utilities.getSliding(Vector3D.PLUS_I.scalarMultiply(100),Vector3D.ZERO);
		double tslide = slide.timeToNaturalRollEquilibrium();
		
		double dt = 0;
		while(dt < tslide)
		{
			dt += tslide/5.0;
			Event interpolated = slide.advanceDelta(dt);
			assertNotNull(interpolated);
			System.out.println(interpolated);
		}		
	}
}
