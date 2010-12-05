package org.game.minmax

import org.game.GamePositionGenerator
import org.game.GameEvaluator
import org.game.GameEvaluationContext
import org.game.Game
import org.game.Score


/**
 * Created by IntelliJ IDEA.
 * User: knoxl
 * Date: Dec 5, 2010
 * Time: 5:34:52 PM
 * To change this template use File | Settings | File Templates.
 */

class MinMaxGameEvaluator(private val positionGenerator : GamePositionGenerator) extends GameEvaluator {

   def evaluate(game : Game, context : GameEvaluationContext) =  {
      if (context.depth > 0) {
           
      }
      new Score(0);
   }

}