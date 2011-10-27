package org.motion.ballsim.physics.util;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.ball.State;
import org.motion.ballsim.physics.ball.Transition;
import org.motion.ballsim.physics.gwtsafe.Vector3D;

/**
 * @author august
 * 
 *         Helper class to rack/replace balls on table for given game rule
 * 
 */
public final class Rack {

	private static Table table;

	public static void rack(Table table, String type, String seed) {

		Rack.table = table;

		double random = ((double) (seed.hashCode() & 31)) / 10000;
		double triangleY = Math.cos(Math.PI / 6) * 2.001 * Ball.R + random;
		double triangleX = Math.cos(2 * Math.PI / 6) * 2.001 * Ball.R + random;

		Vector3D triangleDownLeft = new Vector3D(-triangleX, triangleY, 0);
		Vector3D across = new Vector3D(Ball.R * 2.001, 0, 0);
		if (type.equals("SimplePool")) {
			table.ball(1).setFirstEvent(
					UtilEvent.stationary(new Vector3D(0, Ball.R * 15, 0)));
			Vector3D pos = new Vector3D(0, -Ball.R * 15, 0);
			int b = 2;
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(triangleDownLeft);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(across);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(triangleDownLeft);
			pos = pos.add(-1, across);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(across);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(across);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(triangleDownLeft);
			pos = pos.add(-1, across);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(across);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(triangleDownLeft);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			return;
		}

		if (type.equals("WhiteOnly")) {
			table.ball(1).setFirstEvent(UtilEvent.stationary(Vector3D.ZERO));
			return;
		}

		if (type.equals("SimplePool")) {
			table.ball(1).setFirstEvent(UtilEvent.stationary(Vector3D.ZERO));
			table.ball(2).setFirstEvent(
					UtilEvent.stationary(new Vector3D(-Ball.R * 0.46,
							+Ball.R * 18, 0)));
			table.ball(3).setFirstEvent(
					UtilEvent.stationary(new Vector3D(Ball.R * 8,
							-Ball.R * 0.5, 0)));
		}

		// racks for debug

		// keep at profiling test case
		if (type.equals("PerformanceTest")) {
			table.ball(1).setFirstEvent(UtilEvent.stationary(Vector3D.ZERO));
			for (int b = 2; b < 11; b++) {
				table.ball(b).setFirstEvent(
						UtilEvent.stationary(new Vector3D(-Ball.R * 0.5 * b,
								+Ball.R * 2.5 * b, 0)));

			}
		}

		if (type.equals("1")) {
			getTestShot1();
			return;
		}
		if (type.equals("2")) {
			getTestShot2();
			return;
		}
		if (type.equals("3")) {
			getTestShot3();
			return;
		}
	}

	private static void getTestShot1() {
		tableSetup(new Vector3D(1.8242474789817393, -25.09917289607344, 0),
				new Vector3D(16.57013622704321, 56.625352850266914, 0),
				new Vector3D(-90.8365035306365, 26.58126019754848, 0), 1,
				State.Sliding);

		tableSetup(new Vector3D(9.81341879440367, -2.397553151964832, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 10,
				State.Stationary);
	}

	private static void tableSetup(Vector3D pos, Vector3D vel,
			Vector3D anguarVel, int ballId, State state) {
		Event e = new Event(pos, vel, Vector3D.PLUS_K, Vector3D.PLUS_J,
				anguarVel, Vector3D.ZERO, state, 0, Transition.Interpolated,
				ballId, 0);
		table.ball(ballId).setFirstEvent(e);
	}

	private static void getTestShot2() {
		// break shot that diverges
		tableSetup(new Vector3D(0, 15, 0), new Vector3D(0, -56.8, 0),
				new Vector3D(41.653333333333336, 0, 0), 1, State.Sliding);
		tableSetup(new Vector3D(0, -15, 0), new Vector3D(0, 0, 0),
				new Vector3D(0, 0, 0), 2, State.Stationary);
		tableSetup(new Vector3D(-1.0022000000000002, -13.265383167027338, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 3,
				State.Stationary);
		tableSetup(new Vector3D(0.9987999999999997, -13.265383167027338, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 4,
				State.Stationary);
		tableSetup(new Vector3D(-2.0044000000000004, -11.530766334054675, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 5,
				State.Stationary);
		tableSetup(
				new Vector3D(-0.0034000000000005137, -11.530766334054675, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 6,
				State.Stationary);
		tableSetup(new Vector3D(1.9975999999999994, -11.530766334054675, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 7,
				State.Stationary);
		tableSetup(new Vector3D(-1.0056000000000007, -9.796149501082013, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 8,
				State.Stationary);
		tableSetup(new Vector3D(0.9953999999999992, -9.796149501082013, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 9,
				State.Stationary);
		tableSetup(new Vector3D(-0.0068000000000010274, -8.06153266810935, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 10,
				State.Stationary);

	}

	private static void getTestShot3() {
		// check contact with blue
		tableSetup(new Vector3D(-6.434908112527509, 15, 0), new Vector3D(
				12.964228794051609, -43.092096395690504, 0), new Vector3D(0, 0,
				0), 1, State.Sliding);
		tableSetup(new Vector3D(0, -15, 0), new Vector3D(0, 0, 0),
				new Vector3D(0, 0, 0), 2, State.Stationary);
		tableSetup(new Vector3D(-1.0022000000000002, -13.265383167027338, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 3,
				State.Stationary);
		tableSetup(new Vector3D(0.9987999999999997, -13.265383167027338, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 4,
				State.Stationary);
		tableSetup(new Vector3D(-2.0044000000000004, -11.530766334054675, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 5,
				State.Stationary);
		tableSetup(
				new Vector3D(-0.0034000000000005137, -11.530766334054675, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 6,
				State.Stationary);
		tableSetup(new Vector3D(1.9975999999999994, -11.530766334054675, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 7,
				State.Stationary);
		tableSetup(new Vector3D(-1.0056000000000007, -9.796149501082013, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 8,
				State.Stationary);
		tableSetup(new Vector3D(0.9953999999999992, -9.796149501082013, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 9,
				State.Stationary);
		tableSetup(new Vector3D(-0.0068000000000010274, -8.06153266810935, 0),
				new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 10,
				State.Stationary);

	}

}