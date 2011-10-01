package org.motion.ballsimapp.client;

import org.junit.Before;
import org.junit.Test;
import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.util.Rack;
import org.motion.ballsim.util.UtilEvent;
import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.junit.client.GWTTestCase;

public class BilliardsMarshallerTest extends GWTTestCase {

	public String getModuleName() {
		return "org.motion.ballsimapp.Ballsimapp";
	}

	@Test
	public final void testTableEval() {
		int numberOfEvents = doTest();
		assertTrue(numberOfEvents > 150);
		long start = now();
		for (int i = 0; i < numberOfIterations; ++i)
			doTest();
		System.out.println(now() - start);
	}

	private int numberOfIterations = 1;

	@Before
	public void before() {

	}

	private long now() {
		return System.currentTimeMillis();
	}

	private int doTest() {
		Table t = new Table(true);
		Rack.rack(t, "9Ball", "");
		t.ball(1).setFirstEvent(
				UtilEvent.hit(Vector3D.ZERO, Vector3D.PLUS_J, 2.6, 0.1));
		t.generateSequence();
		return t.getAllEvents().size();
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
