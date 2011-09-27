package org.oxtail.game.model;

/**
 * Evaluates a move
 * 
 * @author kinko
 */
public interface MoveEvaluator<R, G extends Game<S>, S extends PlayingSpace, M extends Move> {

	R evaluate(G game, M move);
}
