package org.oxtail.play.ui;

import org.oxtail.play.Board;
import org.oxtail.play.Move;

public interface MoveInterpreter {

	Move interpretMove(boolean isPlayerOne, String input, Board board) throws IllegalMoveException;
}
