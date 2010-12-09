package org.java.game.inarow;

import junit.framework.Assert;

import org.java.game.Game;
import org.junit.Test;

public class TestInARowPositionEvaluator {

	
	InARowPositionEvaluator evaluator = new InARowPositionEvaluator(3);
	
	
	String horizontalNoWin = 
	"00.."+
    "...."+
    "...."+
	"....";

	String horizontalNoWin2 = 
		"0.0."+
	    "...."+
	    "...."+
		"....";

	
	String horizontalWin = "000."+
					       "...."+
					       "...."+
						   "....";
	
	String horizontalWin2 = "...."+
    					    ".000"+
                            "...."+
	 						"....";

	String verticalWin = "0..."+
    					 "0..."+
    					 "0..."+
    					 "....";
	   
	
	String verticalWin2 = "...."+
	    				  ".0.."+
	    				  ".0.."+
                          ".0..";  


    String diagonalWin = "0..."+
 					     ".0.."+
	                     "..0."+
                         "....";  
    
    String diagonalWin2 = ".0.."+
     				      "..0."+
                          "...0"+
                          "....";  

    String diagonalWin3 = "...."+
    					  ".0.."+
    					  "..0."+
    					  "...0";  

    String diagonalWin4 = "...."+
     					  "0..."+
                          ".0.."+
                          "..0.";  

    String diagonalWin5 = "...0"+
    					  "..0."+
    					  ".0.."+
    					  "....";  

    String diagonalWin6 = 
    					  "...."+
	                      "..0."+
	                      ".0.."+
	                      "0...";  

    String diagonalWin7 = 
    "..0."+
    ".0.."+
    "0..."+
    "....";  

    String diagonalWin8 = 
    "...."+
    "...0"+
    "..0."+
    ".0..";  


	String noWin = "...."+
	                        "X..."+
	                        "0..."+
	                        "00X.";  
	
    
    
	private void assertResultAsExpected(String board, double score) {
		Game game = InARow.newGame(board, 4, 4);
		Assert.assertEquals(score, evaluator.evaluate(game).getScore());
	}
	
	private void assertWin(String board) {
		assertResultAsExpected(board, 1.0);
	}
	
	private void assertNoWin(String board) {
		assertResultAsExpected(board, 0.0);
	}
	
	@Test
	public void testHorizontal() {
		assertWin(horizontalWin);
		assertWin(horizontalWin2);
		assertNoWin(horizontalNoWin);
		assertNoWin(horizontalNoWin2);
	}
	
	@Test
	public void testVertical() {
		assertWin(verticalWin);
		assertWin(verticalWin2);
	}
	
	@Test
	public void testDiagonal1() {
		assertWin(diagonalWin);
	}
	
	@Test
	public void testDiagonal2() {
		assertWin(diagonalWin2);
	}
	
	@Test
	public void testDiagonal3() {
		assertWin(diagonalWin3);
	}
	
	@Test
	public void testDiagonal4() {
		assertWin(diagonalWin4);
	}
	
	@Test
	public void testDiagonal5() {
		assertWin(diagonalWin5);
	}
	
	@Test
	public void testDiagonal6() {
		assertWin(diagonalWin6);
	}
	
	@Test
	public void testDiagonal7() {
		assertWin(diagonalWin7);
	}
	
	@Test
	public void testDiagonal8() {
		assertWin(diagonalWin8);
	}
	
	@Test
	public void testNoWin() {
		assertNoWin(noWin);
	}
	
}
