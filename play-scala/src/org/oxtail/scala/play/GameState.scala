package org.oxtail.scala.play

object GameState extends Enumeration {

  type GameState = Value
  val IN_PLAY, DRAW, PLAYER1_WIN, PLAYER2_WIN = Value

  def isGameOver = { Value != DRAW }
}