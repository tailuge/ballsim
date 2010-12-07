package org.java.game;

import static org.java.util.Assert.assertGreaterThanOrEqualToZero;

public final class PositionBean implements Position {

	private int x, y;
	
	private PositionBean(int x, int y) {
		setX(x);
		setY(y);
	}

	public static final PositionBean newPosition(int x, int y) {
		return new PositionBean(x, y);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		assertGreaterThanOrEqualToZero(x, "invalid x");
		this.x = x;
	}

	public int getY() {
		assertGreaterThanOrEqualToZero(y, "invalid y");
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return "x=" + x + ",y=" + y;
	}

	
}
