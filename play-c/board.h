#ifndef BOARD_H
#define BOARD_H

typedef struct move
{ 
    int x;
    int y;
    char piece;
} Move;


typedef struct moves
{ 
    Move* move;
    int size;
} Moves;

typedef struct board
{
   int height;
   int width;
   char** pieces; 
} Board;


void free_board(Board board);

Board alloc_board(int width, int height);

Board copy_board(Board board);

Board do_move(Board board, Move move);

void show_move(Move move);

void show_moves(Moves moves);

void free_moves(Moves moves);

int no_free_squares(Board board);

void show_board(Board board, char (*pf)(char));

#endif
