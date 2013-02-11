package org.oxtail.scala.play

class Board(val width : Int, val height : Int) {

  val board = Array.ofDim[Byte](height,width)
  
  def setPiece(x : Int, y: Int, piece : Byte) = { board(y)(x) = piece }
  
  def pieceAt(x: Int , y: Int) { board(y)(x) }

}