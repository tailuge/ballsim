#include <stdio.h>
#include <assert.h>
#include <time.h>
#include "tictactoe.h"
#include "connect4.h"
#include "negamax.h"
#include "board.h"


void assert_evaluation(char* b, float expected) {
   Board board = tictactoe_create_board(b);
   show_board_with_coordinates(board,to_visual_checker_piece);
   clock_t start = clock(), diff;
   float n = nega_max(9,1,NEGATIVE_INFINITY,POSITIVE_INFINITY,board,tictactoe_evaluate,tictactoe_possible_moves);
   diff = clock() - start;
   int msec = diff * 1000 / CLOCKS_PER_SEC;
   printf("Time taken %d seconds %d milliseconds\n", msec/1000, msec%1000);

   free_board(board);
   printf("expected = %f got = %f\n",expected,n);
   assert( n == expected);
   
}

void tictactoe_tests(void) {

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
                
}



void connect4_tests(void) {
   
    char* E  = "......."
	           "......."
	           "......."
	           "......."
	           "......."
	           ".......";
  
    char* B1 = "......."
			  "......."
              "......."
              "......."
              "O......"
              "O......";




    Board board = connect4_create_board(E);
    show_board(board,to_visual_checker_piece);
  
	
   clock_t start = clock(), diff;
   float n = nega_max(13,1,NEGATIVE_INFINITY,POSITIVE_INFINITY,board,connect4_evaluate,connect4_possible_moves);
   diff = clock() - start;
   int msec = diff * 1000 / CLOCKS_PER_SEC;
   printf("Time taken %d seconds %d milliseconds\n", msec/1000, msec%1000);


    printf("Eval %f\n",n);  
 	free_board(board);
  
}


int main(void) {
	//tictactoe_tests();
    connect4_tests();
    return 0;
}
