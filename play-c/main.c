#include <stdio.h>
#include <assert.h>
#include "tictactoe.h"
#include "negamax.h"
#include "board.h"


void assert_evaluation(char* b, float expected) {
   Board board = create_board(b);
   show_board(board,for_piece);
   float n = nega_max(9,1,NEGATIVE_INFINITY,POSITIVE_INFINITY,board,evaluate,possible_moves);
   free_board(board);
   printf("expected = %f got = %f\n",expected,n);
   assert( n == expected);
   
}


int main(void) {
	
    char* D1 = "..."
	           "..."
	           "...";
    

    char* D2 = "..X"
	           ".O."
	           "...";
    

	char* P1W= ".X."
	           ".O."
	           "...";
    
	char* P2W ="..X"
	           "OXO"
	           "...";


	assert_evaluation(D1,DRAW_SCORE);
	assert_evaluation(D2,DRAW_SCORE);
	assert_evaluation(P1W,PLAYER1_WIN_SCORE);
    assert_evaluation(P2W,PLAYER2_WIN_SCORE);
            
    
    return 0;
}
