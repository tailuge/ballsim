#include "common.h"
#include "board.h"

float nega_max(int depth, int color, float alpha, float beta, Board board, Evaluation (*ef)(Board), Moves (*mf)(Board,char));
