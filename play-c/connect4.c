#include<stdlib.h>
#include<stdio.h>
#include "common.h"
#include "board.h"

const int HEIGHT = 6;
const int WIDTH = 7;

float weight(int score) {
	return score ^ 3;
}

int winBy(int cnt) {
	return cnt >= 4 ? 1 : 0;
}

int inARow(Board board, int x, int y, int match, int xinc,
			int yinc, int cnt) {
		x += xinc;
		y += yinc;
		while (piece_at_with_range_check(board,x,y) == match) {
			x += xinc;
			y += yinc;
			++cnt;	
		}
		return cnt;
}

int horizontalInARow(Board board, int x, int y, int match) {
	return inARow(board, x, y, match, -1, 0, 0)
				+ inARow(board, x, y, match, 1, 0, 0);
}

int verticalInARow(Board board, int x, int y, int match) {
	return inARow(board, x, y, match, 0, -1, 0)
				+ inARow(board, x, y, match, 0, 1, 0);
}

int forwardDiagonalInARow(Board board, int x, int y, int match) {
	return inARow(board, x, y, match, -1, -1, 0)
				+ inARow(board, x, y, match, 1, 1, 0);
}

int backwardDiagonalInARow(Board board, int x, int y, int match) {
	return inARow(board, x, y, match, -1, 1, 0)
				+ inARow(board, x, y, match, 1, -1, 0);
}



Board connect4_create_board(char* s) {
   return create_board(s,WIDTH,HEIGHT,from_visual_checker_piece);
}

Moves connect4_possible_moves(Board board, char piece) {
   int i = 0;
   Move* moves = malloc(WIDTH * sizeof(Move));
   for (int x=0;x<WIDTH;++x) {
      int y = first_free_row_in_column(board,x);
	  if (y>=0) 
	     moves[i++] = (Move){x,y,piece}; 
   }
   return (Moves){ moves, i };
} 

float connect4_do_evaluate(Board board, char match) {

	float score = 0.0;

	for (int y = 0; y < board.height; ++y)
			for (int x = 0; x < board.width; ++x) {

				int piece = piece_at(board, x, y);
				if (piece != match)
					continue;

				int cnt = horizontalInARow(board, x, y, match) + 1;
				score += weight(cnt);
                //printf("h: %d %f\n",cnt,score);
				if (winBy(cnt)) {
					return WIN_SCORE;
				}

				cnt = verticalInARow(board, x, y, match) + 1;
				score += weight(cnt);
	            //printf("v: %d %f\n",cnt,score);
				
				if (winBy(cnt)) {
					return WIN_SCORE;
				}

				cnt = forwardDiagonalInARow(board, x, y, match) + 1;

				score += weight(cnt);
			    //printf("d: %d %f\n",cnt,score);
	
				if (winBy(cnt)) {
					return WIN_SCORE;
				}
				cnt = backwardDiagonalInARow(board, x, y, match) + 1;
				score += weight(cnt);
                //printf("b: %d %f\n",cnt,score);
	

				if (winBy(cnt)) {
					return WIN_SCORE;
				}
		}
	return score;
}


Evaluation connect4_evaluate(Board board) {
	float player1Score = connect4_do_evaluate(board, 1);
	if (player1Score == WIN_SCORE) {
		return p1_win();
	}
	double player2Score = connect4_do_evaluate(board, -1);
	if (player2Score == WIN_SCORE) {
		return p2_win();
	}
	if (no_free_squares(board))
		return draw();
	return in_play(player1Score - player2Score);
}


