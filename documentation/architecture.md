# Architecture

We use a layer architecture with 5 different layers.

## XBoard
For the visualization of the board/game we have chosen XBoard ([http://www.gnu.org/software/xboard/]). Therfore we need to implement a ChessEngine according to Xboard.

## ChessEngine
The implementation of the ChessEngine for XBoard. Makes use of stdout and stdin.Holds the current board of the game, as XBoard does not take care of this.

## ChessAI
The main part, the implementation of our artificial intelligence. Gets a board from the ChessEngine and returns a move to apply. 

## Chess
Contains all the relevant parts of the chess game, such as the board, figures, representation of the moves, ...

## Foundation
We base our implementation on Java.
