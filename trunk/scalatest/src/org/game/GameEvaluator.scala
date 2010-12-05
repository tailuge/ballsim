package org.game

/**
 * Created by IntelliJ IDEA.
 * User: knoxl
 * Date: Dec 5, 2010
 * Time: 5:03:07 PM
 * To change this template use File | Settings | File Templates.
 */

trait GameEvaluator {

  def evaluate(game : Game, context : GameEvaluationContext) : Score

}

