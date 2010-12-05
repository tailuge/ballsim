package org.game

/**
 * Created by IntelliJ IDEA.
 * User: knoxl
 * Date: Dec 5, 2010
 * Time: 5:15:52 PM
 * To change this template use File | Settings | File Templates.
 */
class Board(xc: Int, yc: Int) {
  var x: Int = xc
  var y: Int = yc

  var position : Array[Array[Piece]] = new Array[Array[Piece]](x, y)

  
  override def toString(): String = "(" +position+ ")";
  
  def pieceAt(x : Int, y : Int) : Piece = {
	  position(x)(y);
  }
  
}
