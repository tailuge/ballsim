package org.motion.ballsim.physics.util;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.util.UtilEvent;

/**
 * @author august
 * 
 *         Helper class to rack/replace balls on table for given game rule
 * 
 */
public final class Rack {

	public static void rack(Table table, String type, String seed) {

		double random = ((double)(seed.hashCode() & 31)) / 10000;
		double triangleY = Math.cos(Math.PI/6) * 2.001 * Ball.R + random;
		double triangleX = Math.cos(2*Math.PI/6) * 2.001 * Ball.R + random;
		
		Vector3D triangleDownLeft = new Vector3D(-triangleX,triangleY,0);
		Vector3D across = new Vector3D(Ball.R * 2.001,0,0);
		if (type.equals("SimplePool"))
		{
			table.ball(1).setFirstEvent(UtilEvent.stationary(new Vector3D(0,Ball.R * 15, 0)));
			Vector3D pos = new Vector3D(0, - Ball.R * 15, 0);
			int b = 2;
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(triangleDownLeft);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(across);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(triangleDownLeft);
			pos = pos.add(-1,across);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(across);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(across);
			table.ball(b++).setFirstEvent(UtilEvent.stationary(pos));
			pos = pos.add(triangleDownLeft);
			pos = pos.add(-1,across);
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

		if (type.equals("9Ball")) {
			table.ball(1).setFirstEvent(UtilEvent.stationary(Vector3D.ZERO));
			for (int b = 2; b < 11; b++) {
				table.ball(b).setFirstEvent(
						UtilEvent.stationary(new Vector3D(-Ball.R * 0.5 * b,
								+Ball.R * 2.5 * b, 0)));

			}
		}
	}
}
