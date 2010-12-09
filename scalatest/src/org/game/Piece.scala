package org.game

/**
 * Created by IntelliJ IDEA.
 * User: knoxl
 * Date: Dec 5, 2010
 * Time: 5:19:22 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class Piece(aName: String) {

  def name: String = aName

  private var mX, mY: Int = 0

  override def toString(): String = "(" + name + ")";

  private def validate(v : Int, message : String) = {
	require(v >= 0, message)
  }
  
  def setX(v: Int) = {
    validate(v, "x < 0")
    mX = v
  }

  def setY(v: Int) = {
    validate(v, "y < 0")
    mY = v
  }

  def x() = {
    mX;
  }

  def y() = {
    mY;
  }

  
}