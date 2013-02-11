package org.oxtail.play.demo;

import java.util.ArrayList;
import java.util.List;

import org.oxtail.play.Board;
import org.oxtail.play.Move;
import org.oxtail.play.MoveGenerator;
import org.oxtail.play.PositionEvaluation;
import org.oxtail.play.PositionEvaluator;

/**
 * Simple example to show fixed cutoffs based on a set game tree
 * 
 * <pre>
 * 
 * 
 * 
 *                                           A[3] 
 *                                           |            
 *                              -------------------------- 
 *                             /                            \
 *                            /                              \
 *                           /                                \
 *                       B  [3]                               C            
 *                /                \                         /  \
 *               /                  \                       /    \x
 *            D [3]                   E                   F[3]      G
 *         /      \                 /   \                /  \       |
 *        /        \               /     \x             /    \      |
 *       H [3]      I [2]         J [15]  K           L[2]    M[3]  N    
 *    /    \      /   \x           |     /  \        / \x      |    |  \
 *   O[3] P[17] Q[2]  R[12]     S[15]  T[25] U[O]  V[2] W[5] X[3] Y[2] Z[14]
 * 
 * </pre>
 * 
 */
public class DemoMoveGenerator implements MoveGenerator, PositionEvaluator {

	@Override
	public Move[] possibleMoves(Board board) {
		char piece =(char)board.getPiece(0, 0);
		switch (piece) {
		case 'A':
			return moves('B', 'C');
		case 'B':
			return moves('D', 'E');
		case 'C':
			return moves('F', 'G');
		case 'D':
			return moves('H', 'I');
		case 'E':
			return moves('J', 'K');
		case 'F':
			return moves('L', 'M');
		case 'G':
			return moves('N');
		case 'H':
			return moves('O', 'P');
		case 'I':
			return moves('Q', 'R');
		case 'J':
			return moves('S');
		case 'K':
			return moves('T', 'U');
		case 'L':
			return moves('V', 'W');
		case 'M':
			return moves('X');
		case 'N':
			return moves('Y', 'Z');
		}
		throw new AssertionError("invalid piece " + piece);
	}

	public Move[] moves(char... m) {
		List<Move> moves = new ArrayList<>();
		for (char c : m) {
			moves.add(new Move(0, 0, c));
		}
		return moves.toArray(new Move[0]);
	}

	@Override
	public PositionEvaluation evaluate(Board board) {
		char piece = (char)board.getPiece(0, 0);
		switch(piece) {
			case 'O' : return PositionEvaluation.inPlay(board, 3.0);
			case 'P' : return PositionEvaluation.inPlay(board, 17.0);
			case 'Q' : return PositionEvaluation.inPlay(board, 2.0);
			case 'R' : return PositionEvaluation.inPlay(board, 12.0);
			case 'S' : return PositionEvaluation.inPlay(board, 15.0);
			case 'T' : return PositionEvaluation.inPlay(board, 25.0);
			case 'U' : return PositionEvaluation.inPlay(board, 0.0);
			case 'V' : return PositionEvaluation.inPlay(board, 2.0);
			case 'W' : return PositionEvaluation.inPlay(board, 5.0);
			case 'X' : return PositionEvaluation.inPlay(board, 3.0);
			case 'Y' : return PositionEvaluation.inPlay(board, 2.0);
			case 'Z' : return PositionEvaluation.inPlay(board, 14.0);
		}
		return PositionEvaluation.inPlay(board, 0.0);
	}

}
