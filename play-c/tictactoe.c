#include <stdlib.h>
#include <stdio.h>
#include "common.h"
#include "board.h"


char for_vpiece(char c) {
   if (c == PLAYER1_VPIECE) 
	 return PLAYER1_PIECE;
   if (c == PLAYER2_VPIECE)
	 return PLAYER2_PIECE;
   return NO_PIECE;
}

/** returns visual representation of the piece */
char for_piece(char c) {
   if (c == PLAYER1_PIECE) 
	 return PLAYER1_VPIECE;
   if (c == PLAYER2_PIECE)
	 return PLAYER2_VPIECE;
   return NO_VPIECE;
}

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

Evaluation evaluate(Board board) {
   float s = do_evaluate(board.pieces,PLAYER1_PIECE,PLAYER1_WIN_SCORE);
   if (s == PLAYER1_WIN_SCORE) {
     return (Evaluation){ s, PLAYER1_WIN };
   } 
   s = do_evaluate(board.pieces,PLAYER2_PIECE,PLAYER2_WIN_SCORE);
       
   if (s == PLAYER2_WIN_SCORE) {
   	  return (Evaluation){ s, PLAYER2_WIN }; 
   }  
   if (no_free_squares(board))
     return (Evaluation){ s, DRAW };   

   return (Evaluation){ s, IN_PLAY };  
}

int free_squares(Board board) {
  int cnt = 0; 
  for (int y=0;y<3;++y)
     for (int x=0;x<3;++x)
        if (board.pieces[y][x] == NO_PIECE) ++cnt; 
  return cnt;
}

Moves possible_moves(Board board, char piece) {
   int n = free_squares(board);
   int i = 0;
   Move* moves = malloc(n * sizeof(Move));
   for (int y=0;y<3;++y)
     for (int x=0;x<3;++x)
        if (board.pieces[y][x] == NO_PIECE) 	  
		  moves[i++] = (Move){ x, y, piece };

   return (Moves){ moves, n };
} 

Board create_board(char* s) {
   Board board = alloc_board(3,3);
   for (int i=0;i<9;++i) {
        int x = i % 3;
        int y = i / 3;  
        board.pieces[y][x] = for_vpiece(s[i]);        
   }
   return board;
}

