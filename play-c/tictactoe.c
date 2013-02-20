#include <stdlib.h>
#include <stdio.h>
#include "common.h"
#include "board.h"

int horz_win(int i, char** board, int match) {
 	int rtn = board[i][0] == match && board[i][1] == match && board[i][2] == match;
    return rtn; 
}

int vert_win(int i, char** board, char match) {
 	int rtn = board[0][i] == match && board[1][i] == match && board[2][i] == match;
    return rtn;
}

int diag_win(char** board, char match) {
    return board[0][0] == match && board[1][1] == match && board[2][2] == match;
}

int rdiag_win(char** board, char match) {
    return board[2][0] == match && board[1][1] == match && board[0][2] == match;
}

float do_evaluate(char** board, char match, float winScore) {
	if (horz_win(0,board,match) || horz_win(1,board,match) || horz_win(2,board,match))
       return winScore;    
      
	if (vert_win(0,board,match) || vert_win(1,board,match) || vert_win(2,board,match))
		return winScore;
    
	if (diag_win(board,match) || rdiag_win(board,match))
		return winScore;
    
	return 0;	
}

Evaluation tictactoe_evaluate(Board board) {
   float score = do_evaluate(board.pieces,PLAYER1_PIECE,PLAYER1_WIN_SCORE);
   if (score == PLAYER1_WIN_SCORE)
     return p1_win();
   
   score = do_evaluate(board.pieces,PLAYER2_PIECE,PLAYER2_WIN_SCORE);
       
   if (score == PLAYER2_WIN_SCORE)
   	  return p2_win(); 
    
   if (no_free_squares(board))
     return draw();   

   return in_play(score);  
}

Moves tictactoe_possible_moves(Board board, char piece) {
   int n = number_of_free_squares(board);
   int i = 0;
   Move* moves = malloc(n * sizeof(Move));
   for (int y=0;y<3;++y)
     for (int x=0;x<3;++x)
        if (board.pieces[y][x] == NO_PIECE) 	  
		  moves[i++] = (Move){ x, y, piece };

   return (Moves){ moves, n };
} 

Board tictactoe_create_board(char* s) {
   return create_board(s,3,3,from_visual_checker_piece);
}


