package org.oxtail.scala.play.demo

import org.oxtail.scala.play.MoveGenerator
import org.oxtail.scala.play.PositionEvaluator
import org.oxtail.scala.play.Board
import org.oxtail.scala.play.PositionEvaluation
import org.oxtail.scala.play.Move

/**
 * Simple example to show fixed cutoffs based on a set game tree
 * <pre>
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
class DemoMoveGenerator extends MoveGenerator with PositionEvaluator {

  def evaluate(board: Board): PositionEvaluation = doEvaluate(board.onlyPiece.toChar,board);

  private def doEvaluate(piece : Char, board : Board): PositionEvaluation = {
     piece match {
			case 'O' =>  PositionEvaluation.inPlay(board, 3.0);
			case 'P' =>  PositionEvaluation.inPlay(board, 17.0);
			case 'Q' =>  PositionEvaluation.inPlay(board, 2.0);
			case 'R' =>  PositionEvaluation.inPlay(board, 12.0);
			case 'S' =>  PositionEvaluation.inPlay(board, 15.0);
			case 'T' =>  PositionEvaluation.inPlay(board, 25.0);
			case 'U' =>  PositionEvaluation.inPlay(board, 0.0);
			case 'V' =>  PositionEvaluation.inPlay(board, 2.0);
			case 'W' =>  PositionEvaluation.inPlay(board, 5.0);
			case 'X' =>  PositionEvaluation.inPlay(board, 3.0);
			case 'Y' =>  PositionEvaluation.inPlay(board, 2.0);
			case 'Z' =>  PositionEvaluation.inPlay(board, 14.0);
	 }
     PositionEvaluation.inPlay(board, 0.0);
  } 
  
  def possibleMoves(board: Board): List[Move] = doPossibleMoves(board.onlyPiece.toChar)

  private def doPossibleMoves(piece: Char): List[Move] = {
    piece match {
      case 'A' => moves('B', 'C')
      case 'B' => moves('D', 'E');
      case 'C' => moves('F', 'G');
      case 'D' => moves('H', 'I');
      case 'E' => moves('J', 'K');
      case 'F' => moves('L', 'M');
      case 'G' => moves('N');
      case 'H' => moves('O', 'P');
      case 'I' => moves('Q', 'R');
      case 'J' => moves('S');
      case 'K' => moves('T', 'U');
      case 'L' => moves('V', 'W');
      case 'M' => moves('X');
      case 'N' => moves('Y', 'Z');
    }
    throw new IllegalArgumentException("can't find possible move for " + piece)
  }

  def moves(pieces: Char*): List[Move] = pieces.map(c => new Move(0, 0, c.toByte)).toList

}