package org.game

/**
 * Created by IntelliJ IDEA.
 * User: knoxl
 * Date: Dec 5, 2010
 * Time: 4:59:43 PM
 * To change this template use File | Settings | File Templates.
 */

class Game {
	
	var score : Score = null
	 
	var status : GameStatus = null
	
	def isOver : Boolean = status.isOver 
	
	
	
}