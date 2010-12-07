package org.java.game.tictactoe;

import org.java.game.Position;

public enum TicTacToePositions implements Position {

	TOP_LEFT(0,0),
	TOP_MIDDLE(1,0),
	TOP_RIGHT(2,0),
	
	MIDDLE_LEFT(0,1),
	MIDDLE_MIDDLE(1,1),
	MIDDLE_RIGHT(2,1),
	
	BOTTOM_LEFT(0,2),
	BOTTOM_MIDDLE(1,2),
	BOTTOM_RIGHT(2,2);
	
	private int x;
	private int y;
	
	private TicTacToePositions(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	public static Position[] LeftColumn() {
		return new Position[] { TOP_LEFT, MIDDLE_LEFT, BOTTOM_LEFT };
	}
	
	public static Position[] RightColumn() {
		return new Position[] { TOP_RIGHT, MIDDLE_RIGHT, BOTTOM_RIGHT };
	}
	
	public static Position[] MiddleColumn() {
		return new Position[] { TOP_MIDDLE, MIDDLE_MIDDLE, BOTTOM_MIDDLE };
	}
	
	public static Position[] TopRow() {
		return new Position[] { TOP_LEFT, TOP_MIDDLE, TOP_RIGHT };
	}
	
	public static Position[] MiddleRow() {
		return new Position[] { MIDDLE_RIGHT, MIDDLE_MIDDLE, MIDDLE_LEFT };
	}
	
	public static Position[] BottomRow() {
		return new Position[] { BOTTOM_LEFT, BOTTOM_MIDDLE, BOTTOM_RIGHT };
	}
	
	public static Position[] DiagonalOne() {
		return new Position[] { BOTTOM_LEFT, MIDDLE_MIDDLE, TOP_RIGHT };
	}
	
	public static Position[] DiagonalTwo() {
		return new Position[] { BOTTOM_RIGHT, MIDDLE_MIDDLE, TOP_LEFT };
	}
	
	
}
