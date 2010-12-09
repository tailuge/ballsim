package org.game.minmax

import org.game.GamePositionGenerator
import org.game.GameEvaluator
import org.game.GameEvaluationContext
import org.game.Game
import org.game.Score

import org.game.InPlay

/**
 * Created by IntelliJ IDEA.
 * User: knoxl
 * Date: Dec 5, 2010
 * Time: 5:34:52 PM
 * To change this template use File | Settings | File Templates.
 */

class MinMaxGameEvaluator(private val positionGenerator : GamePositionGenerator, private val evaluator : GameEvaluator) extends GameEvaluator {

	def evaluate(game : Game, context : GameEvaluationContext) =  {
      if (game.isOver) {
    	  // if we have finished stop
    	  game 
      }
      var bestGame : Game = null
      // evaluate the children and pick the best
      for (newGame <- positionGenerator.generate(game)) {
    	  if (bestGame == null) {
    	 	  bestGame = evaluate(newGame, context.dec)
    	  }
    	  else {
    	      	  
    	  }
      }
      game
   }
   
   
}