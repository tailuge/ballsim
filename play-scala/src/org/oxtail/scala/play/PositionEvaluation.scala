package org.oxtail.scala.play

class PositionEvaluation(val gameState : GameState.Value,  val evaluation : Double, val board : Board) {
}

object PositionEvaluation {
	
	def player1Wins(evaluation : Double, board : Board) = new PositionEvaluation(GameState.PLAYER1_WIN,evaluation,board);
	def player2Wins(evaluation : Double, board : Board) = new PositionEvaluation(GameState.PLAYER2_WIN,evaluation,board);
	def draw(board : Board) = new PositionEvaluation(GameState.DRAW,0,board);
	def inPlay(evaluation : Double, board : Board) = new PositionEvaluation(GameState.IN_PLAY,evaluation,board);
	
}