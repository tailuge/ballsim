package org.oxtail.play;

/**
 * From a give board select the best continuation move
 */
public interface MoveSelector {

	Move selectBestContinuation(Board board);
	
}
