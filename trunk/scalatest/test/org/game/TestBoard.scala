package org.game

import org.scalatest.junit.AssertionsForJUnit
import scala.collection.mutable.ListBuffer
import org.junit.Assert._
import org.junit.Test
import org.junit.Before

class TestBoard extends AssertionsForJUnit {

  var board: Board = _;
  val piece : Piece = new Black();
    
  @Before def initialize() {
    board = new Board(1,1)
  }


  @Test def verifyMove() { // Uses ScalaTest assertions
    
    board.move(piece)
	assert(board.pieceAt(0, 0) == piece)
    
	piece.setX(1);
    intercept[IllegalArgumentException] {
       board.move(piece)
    }
  }
  
  class Black extends Piece("Black") {
	  
	  
  }
   
}
