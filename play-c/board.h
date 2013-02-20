#ifndef BOARD_H
#define BOARD_H
#include "common.h"

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
   int width;
   int height;
   char** pieces; 
} Board;

typedef Evaluation (*PositionEvaluator)(Board);

typedef Moves (*MoveGenerator)(Board,char);

typedef char (*ToChar)(char);

char to_visual_checker_piece(char c);

char from_visual_checker_piece(char c);

void free_board(Board board);

Board alloc_board(int width, int height);

Board copy_board(Board board);

// returns copy of the board with the move played
Board do_move(Board board, Move move);

void show_move(Move move);

void show_moves(Moves moves);

void free_moves(Moves moves);

int no_free_squares(Board board);

void show_board(Board board, ToChar toChar);

void show_board_with_coordinates(Board board, ToChar toChar);

char piece_at(Board board, int x, int y);

char piece_at_with_range_check(Board board, int x, int y);

void undo_move_inplace(Board board, Move move, char other_piece);

// apply the board with the move played (no board copy)
void do_move_inplace(Board board, Move move);

Board create_board(char* s, int width, int height, ToChar toChar);

int number_of_free_squares(Board board);

// given column index return the first free row index
int first_free_row_in_column(Board board,int x);


Evaluation p1_win(void);

Evaluation p2_win(void);

Evaluation draw(void);

Evaluation in_play(float score);

#endif
