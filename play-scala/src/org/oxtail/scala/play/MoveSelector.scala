package org.oxtail.scala.play

/**
 * Select the best continuation for a given position
 */
trait MoveSelector {
 
	def selectBestContinuation(board : Board) : Move

}