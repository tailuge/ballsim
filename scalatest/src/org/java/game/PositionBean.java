package org.java.game;

import static org.java.util.Assert.assertGreaterThanOrEqualToZero;

public final class PositionBean implements Position {


	/**
	 * Assume Go(19x19) is the biggest game we will model
	 */
	private static final int MAX_POSITIONS = 19; 
	
	private static final Position[][] POSITIONS = new Position[MAX_POSITIONS][MAX_POSITIONS];
	                                              
	static {
		for (int i=0;i<MAX_POSITIONS;++i) 
			for (int j=0;j<MAX_POSITIONS;++j) 
				POSITIONS[i][j] = new PositionBean(i, j);
		
	}
	
	private int x, y;
	
	private PositionBean(int x, int y) {
		setX(x);
		setY(y);
	}

	public static final Position newPosition(int x, int y) {
		return POSITIONS[x][y];
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
