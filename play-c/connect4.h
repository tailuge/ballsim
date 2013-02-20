#ifndef CONNECT4_H
#define CONNECT4_H

Board connect4_create_board(char* s);

Moves connect4_possible_moves(Board board, char piece);

Evaluation connect4_evaluate(Board board);

#endif
