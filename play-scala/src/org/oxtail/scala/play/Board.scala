package org.oxtail.scala.play

/**
 * Simple board with Bytes representing pieces
 */
class Board(val width : Int, val height : Int) {

  val board = Array.ofDim[Byte](height,width)
  
  def setPiece(x : Int, y: Int, piece : Byte) =  board(y)(x) = piece
  
  def pieceAt(x: Int , y: Int) : Byte = board(y)(x) 

  def isFull = !board.flatten.exists(e => e == 0)
 
  def onlyPiece = pieceAt(0, 0)
}