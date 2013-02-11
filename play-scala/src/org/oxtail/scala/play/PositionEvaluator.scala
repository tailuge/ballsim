package org.oxtail.scala.play

/**
 * Evaluate the current value of the position
 */
trait PositionEvaluator {
	
  def evaluate(board : Board) : PositionEvaluation
  
}