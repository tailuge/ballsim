package org.game

/**
 * Created by IntelliJ IDEA.
 * User: knoxl
 * Date: Dec 5, 2010
 * Time: 5:06:08 PM
 * To change this template use File | Settings | File Templates.
 */

trait GamePositionGenerator  {

  def generate(game : Game) : Array[Game];
}