package org.motion.ballsim.physics.util;

import static org.motion.ballsim.physics.Cushion.*;
import static org.motion.ballsim.physics.PocketGeometry.*;
import org.motion.ballsim.gwtsafe.Function;
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

	public static boolean onTable(Event e) {
		return onTableX(e) && onTableY(e);
	}

	private static boolean onTableX(Event e) {
		return (e.pos.getX() < xp) && (e.pos.getX() > xn);
	}

	private static boolean onTableY(Event e) {
		return (e.pos.getY() < yp) && (e.pos.getY() > yn);
	}

	public static Function<Double, Boolean> onY(final Event e) {
		return new Function<Double, Boolean>() {
			public Boolean apply(Double arg) {
				return onTableY(e.advanceDelta(arg));
			}
		};
	}

	public static Function<Double, Boolean> onX(final Event e) {
		return new Function<Double, Boolean>() {
			public Boolean apply(Double arg) {
				return onTableX(e.advanceDelta(arg));
			}
		};
	}

	public static boolean validPosition(Table table) {
		for (Ball a : table.balls()) {
			if (!onTable(a.lastEvent()))
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
