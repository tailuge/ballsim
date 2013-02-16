#include "common.h"
#include "board.h"


float nega_max(int depth, int color, float alpha, float beta, Board board, Evaluation (*ef)(Board), Moves (*mf)(Board,char)) {
	Evaluation eval = ef(board);
	if (depth == 0 || eval.state != IN_PLAY ) {
      	return color * eval.score;      
	}	
    Moves moves = mf(board,-color);
      
	for (int i=0;i<moves.size;++i) {
       Board newPosition = do_move(board,moves.move[i]);
	   
       float value  = -nega_max(depth - 1, -color, -beta, -alpha,  newPosition, ef, mf);
       if (value >= beta) {
		  alpha = value; 
		  free_board(newPosition);
          break;
	   }
	   if (value >= alpha) {
		 alpha = value;
       }
       free_board(newPosition);
    }
    free_moves(moves);     
    return alpha;
}
