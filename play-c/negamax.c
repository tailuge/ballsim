#include "common.h"
#include "board.h"
#include <stdio.h>


float nega_max(int depth, int color, float alpha, float beta, Board board, PositionEvaluator ef, MoveGenerator mg) {
	Evaluation eval = ef(board);
	if (depth == 0 || eval.state != IN_PLAY ) {
	//	show_board(board,to_visual_checker_piece);
  	//	printf("%f\n",eval.score);    	
		return color * eval.score;      
	}	
    
    Moves moves = mg(board,color);
    //printf("b\n");    	
	//show_board(board,to_visual_checker_piece);
    
	for (int i=0;i<moves.size;++i) {
		Move move = moves.move[i];
        char p = piece_at(board,move.x,move.y);      	        
		//Board newPosition = do_move(board,move); 
		//printf("a\n");    	
        //show_board(newPosition,to_visual_checker_piece);
       do_move_inplace(board,move);
       //float value  = -nega_max(depth - 1, -color, -beta, -alpha,  newPosition, ef, mg);
       float value  = -nega_max(depth - 1, -color, -beta, -alpha,  board, ef, mg);
       undo_move_inplace(board,move,p);    
	   if (value >= beta) {
		  alpha = value; 
		  //free_board(newPosition);
          break;
	   }
	   if (value > alpha) {
		 alpha = value;
       }
       //free_board(newPosition);
    }
    free_moves(moves);     
    return alpha;
}
