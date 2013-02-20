#include <stdlib.h>
#include <stdio.h>

#include "board.h"
#include "common.h"


// returns a working representation of a checker piece (1,-1,0) 
// from a visual representation
char from_visual_checker_piece(char c) {
   if (c == PLAYER1_VPIECE) 
	 return PLAYER1_PIECE;
   if (c == PLAYER2_VPIECE)
	 return PLAYER2_PIECE;
   return NO_PIECE;
}

// returns visual representation of a checker piece (O,X,.)
// from a working representation
char to_visual_checker_piece(char c) {
   if (c == PLAYER1_PIECE) 
	 return PLAYER1_VPIECE;
   if (c == PLAYER2_PIECE)
	 return PLAYER2_VPIECE;
   return NO_VPIECE;
}

void free_moves(Moves moves) {
  free(moves.move);
}

void free_board(Board board) {
   for (int i=0;i<board.height;++i)
     free(board.pieces[i]);
   free(board.pieces);
}

Board alloc_board(int width, int height) {
  char** pieces = malloc(height * sizeof(char *));
  for (int i=0;i<height;++i)  
	pieces[i] = malloc(width * sizeof(char));
  return (Board){width,height,pieces};
}

Board copy_board(Board board) {
  Board copy = alloc_board(board.width,board.height);
  for (int y=0;y<board.height;++y)
     for (int x=0;x<board.width;++x)
		copy.pieces[y][x] = board.pieces[y][x];
  return copy;
}

Board do_move(Board board, Move move) {
    Board copy = copy_board(board);
    copy.pieces[move.y][move.x] = move.piece;
    return copy; 
}

void do_move_inplace(Board board, Move move) {
    board.pieces[move.y][move.x] = move.piece;
}

void undo_move_inplace(Board board, Move move, char other_piece) {
    board.pieces[move.y][move.x] = other_piece;
}

char piece_at(Board board, int x, int y) {
    return board.pieces[y][x];
}

int has_piece_at(Board board, int x, int y) {
    return piece_at(board,x,y) > 0 ? 1 : 0;
}

void place_piece(Board board, int x, int y, char piece) {
    board.pieces[y][x] = piece;
}

int no_free_squares(Board board) {
  for (int y=0;y<board.height;++y)
     for (int x=0;x<board.width;++x)
        if (board.pieces[y][x] == NO_PIECE) return 0; 
  return 1;
}

void show_move(Move move) {
  printf("%d %d %d\n",move.x,move.y,move.piece);
}

void show_moves(Moves moves) {
  printf("number of moves %d\n",moves.size);
  for (int i=0;i<moves.size;++i) 
	show_move(moves.move[i]);
}

void do_show_board(Board board, void (*pf)(ToChar,char,int,int), ToChar toChar) {
   for (int y=board.height-1;y>=0;--y) {
     for (int x=0;x<board.width;++x)  
        pf(toChar,piece_at(board,x,y),x,y); 
     printf("\n"); 
   }
}

void show_piece(ToChar sf, char piece, int x, int y) {
   printf("%c",sf(piece)); 
}

void show_piece_with_coordinates(ToChar sf, char piece, int x, int y) {
   printf("%c[%d,%d]",sf(piece),x,y); 
}

void show_board(Board board, ToChar toChar) {
   do_show_board(board,show_piece,toChar);
}

void show_board_with_coordinates(Board board, ToChar toChar) {
   do_show_board(board,show_piece_with_coordinates,toChar);
}

// creates board from a string representation
Board create_board(char* s, int width, int height, char (*pf)(char)) {
   printf("creating board %d,%d\n",width,height); 
   Board board = alloc_board(width,height);
   int i = 0;
   for (int y=height-1;y>=0;--y) {
      for (int x=0;x<board.width;++x) {
	    printf("%d",i);    
		place_piece(board,x,y,pf(s[i++]));
      }
      printf("\n");   
   }   
   return board;
}

int number_of_free_squares(Board board) {
  int cnt = 0; 
  for (int y=0;y<board.height;++y)
     for (int x=0;x<board.width;++x)
        if (board.pieces[y][x] == NO_PIECE) ++cnt; 
  return cnt;
}

int first_free_row_in_column(Board board,int x) {
   for (int y=0;y<board.height;++y) {
	  if (piece_at(board,x,y) == NO_PIECE) 
        return y;
	}
	return -1;
}

char piece_at_with_range_check(Board board, int x, int y) {
   if (x>=0 && x<board.width && y>=0 && y<board.height)
      return piece_at(board,x,y);
   return 0; 
} 

Evaluation p1_win(void) {
	return (Evaluation){PLAYER1_WIN_SCORE,PLAYER1_WIN};
}


Evaluation p2_win(void) {
	return (Evaluation){PLAYER2_WIN_SCORE,PLAYER2_WIN};
}

Evaluation draw(void) {
	return (Evaluation){DRAW_SCORE,DRAW};
}

Evaluation in_play(float score) {
	return (Evaluation){score,IN_PLAY};
}

