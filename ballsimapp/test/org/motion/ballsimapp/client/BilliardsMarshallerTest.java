package org.motion.ballsimapp.client;

import org.junit.Test;
import org.motion.ballsim.physics.game.Aim;
import org.motion.ballsim.physics.gwtsafe.Vector3D;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.junit.client.GWTTestCase;

public class BilliardsMarshallerTest extends GWTTestCase {

	public String getModuleName() {
		return "org.motion.ballsimapp.Ballsimapp";
	}

	@Test
	public void testMarshalAim() {

		Aim aim = new Aim(1, new Vector3D(1, 2.2, 0), new Vector3D(1, 2.2, 0),
				new Vector3D(-1, -3.000000001, 0), 0.89);

		GameEvent event = BilliardsMarshaller.eventFromAim(aim);
		Aim testAim = BilliardsMarshaller.aimFromEvent(event);

		assertTrue(aim.dir.equals(testAim.dir));
		assertTrue(aim.spin.equals(testAim.spin));
		assertTrue(aim.speed == testAim.speed);
	}

}
