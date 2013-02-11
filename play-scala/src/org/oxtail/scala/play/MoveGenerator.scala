package org.oxtail.scala.play

import scala.collection.immutable.List

/**
 * Generate all possible moves for a given position
 */
trait MoveGenerator {

  def possibleMoves(board: Board): List[Move]

}