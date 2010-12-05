package org.game

/**
 * Created by IntelliJ IDEA.
 * User: knoxl
 * Date: Dec 5, 2010
 * Time: 5:19:22 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class Piece( aName : String) {


  def name : String = aName


  override def toString(): String = "(" +name+ ")";

}