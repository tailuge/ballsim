package org.motion.ballsim.plotter;

import static org.junit.Assert.*;


import org.junit.Test;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.BallSpot;

public class BallSpotTest {





	@Test
	public void testRotationZero() {
		Vector3D unitPos = new Vector3D(0,0,1);
		Vector3D velocity = new Vector3D(1,0,0);
		Vector3D acceleration = new Vector3D(-0.1,0,0);
		Vector3D result = BallSpot.progressTo(unitPos,velocity,acceleration,0);
		assertTrue("must not move",unitPos.equals(result));	
	}
	
	@Test
	public void testRotation() {
		Vector3D unitPos = new Vector3D(0,0,1);
		Vector3D velocity = new Vector3D(1,0,0);
		Vector3D acceleration = new Vector3D(-0.1,0,0);
		Vector3D result = BallSpot.progressTo(unitPos,velocity,acceleration,1.0);
		assertTrue("must move",!unitPos.equals(result));	
	}

	@Test
	public void testPlotRotation() {

		Vector3D unitPos = new Vector3D(0,0,1);
		Vector3D velocity = new Vector3D(1,0,0);
		Vector3D acceleration = new Vector3D(-0.5,0,0);

		for(double t = 0; t < 4; t+=0.2)
		{
			Vector3D result = BallSpot.progressTo(unitPos,velocity,acceleration,t);
			System.out.println(result);
		}
		assertTrue("curve",true);	
	}
}
