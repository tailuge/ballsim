#ifndef COMMON_H
#define COMMON_H

extern const float DRAW_SCORE;
extern const float WIN_SCORE;
extern const float PLAYER1_WIN_SCORE; 
extern const float PLAYER2_WIN_SCORE;
extern const float POSITIVE_INFINITY;
extern const float NEGATIVE_INFINITY; 
extern const char PLAYER1_PIECE;
extern const char PLAYER2_PIECE;
extern const char NO_PIECE;
extern const char PLAYER1_VPIECE;
extern const char PLAYER2_VPIECE;
extern const char NO_VPIECE;

enum gamestate { PLAYER1_WIN, PLAYER2_WIN, DRAW, IN_PLAY };   

typedef enum gamestate GameState;

typedef struct evaluation {
  float score; 
  GameState state;   
} Evaluation;


#endif
