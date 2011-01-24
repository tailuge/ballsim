package org.motion.ballsim.plotter;

import static org.junit.Assert.*;

import org.apache.commons.math.geometry.Rotation;
import org.apache.commons.math.geometry.Vector3D;
import org.junit.Test;

public class BallSpotTest {



	Vector3D progressTo(Vector3D position, Vector3D velocity, Vector3D acceleration, double t)
	{
		Rotation rot = new Rotation(velocity,velocity.getNorm()*t);
		Rotation acc = new Rotation(acceleration,acceleration.getNorm()*t*t/2.0);
		Rotation net = acc.applyTo(rot);
		Vector3D result = net.applyTo(position);
		return result;
	}

	@Test
	public void testRotationZero() {
		Vector3D unitPos = new Vector3D(0,0,1);
		Vector3D velocity = new Vector3D(1,0,0);
		Vector3D acceleration = new Vector3D(-0.1,0,0);
		Vector3D result = progressTo(unitPos,velocity,acceleration,0);
		assertTrue("must not move",unitPos.equals(result));	
	}
	
	@Test
	public void testRotation() {
		Vector3D unitPos = new Vector3D(0,0,1);
		Vector3D velocity = new Vector3D(1,0,0);
		Vector3D acceleration = new Vector3D(-0.1,0,0);
		Vector3D result = progressTo(unitPos,velocity,acceleration,1.0);
		assertTrue("must move",!unitPos.equals(result));	
	}

	@Test
	public void testPlotRotation() {

		Vector3D unitPos = new Vector3D(0,0,1);
		Vector3D velocity = new Vector3D(1,0,0);
		Vector3D acceleration = new Vector3D(-0.5,0,0);

		for(double t = 0; t < 4; t+=0.2)
		{
			Vector3D result = progressTo(unitPos,velocity,acceleration,t);
			System.out.println(result);
		}
		assertTrue("curve",true);	
	}
}
