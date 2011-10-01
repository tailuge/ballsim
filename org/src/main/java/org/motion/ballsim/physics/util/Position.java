package org.motion.ballsim.physics.util;

import static org.motion.ballsim.physics.Cushion.*;
import org.motion.ballsim.gwtsafe.Function;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;

public class Position {

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


}
