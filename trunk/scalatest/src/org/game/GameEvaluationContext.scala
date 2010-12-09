package org.game

/**
 * Created by IntelliJ IDEA.
 * User: knoxl
 * Date: Dec 5, 2010
 * Time: 5:11:16 PM
 * To change this template use File | Settings | File Templates.
 */

class GameEvaluationContext  {

  private var depth : Int = 0

  private var minMax : Boolean = true 

  def dec : GameEvaluationContext = {
	minMax != minMax 
	depth -= 1
	this  
  }

  def getMinMax : Boolean = {
	minMax
  }

  
  
}