package org.motion.ballsim.physics.util;

import static org.motion.ballsim.physics.Cushion.xn;
import static org.motion.ballsim.physics.Cushion.xp;
import static org.motion.ballsim.physics.Cushion.yn;
import static org.motion.ballsim.physics.Cushion.yp;
import static org.motion.ballsim.physics.PocketGeometry.p1k1;
import static org.motion.ballsim.physics.PocketGeometry.p1k2;
import static org.motion.ballsim.physics.PocketGeometry.p2k1;
import static org.motion.ballsim.physics.PocketGeometry.p2k2;
import static org.motion.ballsim.physics.PocketGeometry.p3k1;
import static org.motion.ballsim.physics.PocketGeometry.p3k2;
import static org.motion.ballsim.physics.PocketGeometry.p4k1;
import static org.motion.ballsim.physics.PocketGeometry.p4k2;
import static org.motion.ballsim.physics.PocketGeometry.p5k1;
import static org.motion.ballsim.physics.PocketGeometry.p5k2;

import org.motion.ballsim.gwtsafe.RealPredicate;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;

/**
 * @author luke
 * 
 *         Predicates testing position of ball on table
 * 
 */
public final class Position {

	public static boolean onTable(Vector3D pos) {
		return onTableX(pos) && onTableY(pos);
	}

	private static boolean onTableX(Vector3D pos) {
		return (pos.getX() < xp) && (pos.getX() > xn);
	}

	private static boolean onTableY(Vector3D pos) {
		return (pos.getY() < yp) && (pos.getY() > yn);
	}

	public static RealPredicate onY(final Event e) {
		return new RealPredicate() {
			public boolean apply(double arg) {
				return onTableY(e.advanceDeltaPosition(arg));
			}
		};
	}

	public static RealPredicate onX(final Event e) {
		return new RealPredicate() {
			public boolean apply(double arg) {
				return onTableX(e.advanceDeltaPosition(arg));
			}
		};
	}

	public static boolean validPosition(Table table) {
		for (Ball a : table.balls()) {
			if (!onTable(a.lastEvent().pos))
				return false;
		}
		return true;
	}

	public static boolean isCushionEventInPocketRegion(Event e) {
		// bottom left
		if ((e.pos.getX() < p1k2.getX()) && (e.pos.getY() > p1k1.getY()))
			return true;

		// bottom right
		if ((e.pos.getX() > p2k2.getX()) && (e.pos.getY() > p2k1.getY()))
			return true;

		// top right
		if ((e.pos.getX() > p3k2.getX()) && (e.pos.getY() < p3k1.getY()))
			return true;

		// top left
		if ((e.pos.getX() < p4k2.getX()) && (e.pos.getY() < p4k1.getY()))
			return true;

		// middle pockets
		if ((e.pos.getY() > p5k1.getY()) && (e.pos.getY() < p5k2.getY()))
			return true;

		return false;
	}
}
