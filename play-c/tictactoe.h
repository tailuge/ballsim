#include "board.h"
#include "common.h"

Board create_board(char* s);

Evaluation evaluate(Board board);

Moves possible_moves(Board board, char piece);

char for_piece(char c);
