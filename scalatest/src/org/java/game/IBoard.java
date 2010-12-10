package org.java.game;


public interface IBoard {

	void move(Piece piece, Position position);

   boolean hasPieceAt(Position position);

    Piece getPieceAt(Position position);

    Piece getPieceAt(int x, int y);

	IBoard copy();
	
	int getX(); 

	int getY() ;
	
    boolean piecesAreAll(Piece piece, Position... positions);

	boolean isFull();

	int getFirstEmptyVerticalPosition(int xindex);

}
