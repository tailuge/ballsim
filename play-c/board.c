#include <stdlib.h>
#include <stdio.h>

#include "board.h"
#include "common.h"

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


void show_board(Board board, char (*pf)(char)) {
   for (int y=0;y<board.height;++y) {
     for (int x=0;x<board.width;++x)  
        printf("%c",pf(board.pieces[y][x])); 
     printf("\n"); 
   }
}
