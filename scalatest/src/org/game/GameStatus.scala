package org.game

case object InPlay extends GameStatus(false)

sealed case class GameStatus(over : Boolean) {
  
	def isOver : Boolean = over  

}