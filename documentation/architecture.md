# Architecture

We use a layer architecture with 5 different layers.

## XBoard
For the visualization of the board/game we have chosen XBoard (http://www.gnu.org/software/xboard/). Therfore we need to implement a ChessEngine according to Xboard.

## ChessEngine
The implementation of the ChessEngine for XBoard. Makes use of stdout and stdin.Holds the current board of the game, as XBoard does not take care of this.

## ChessAI
The main part, the implementation of our artificial intelligence. Gets a board from the ChessEngine and returns a move to apply.

### Move Generator
In this package is also the move generator included, responsible for asking every figure on the field for its possible moves.

### Alpha-Beta algorithm
For getting an as good as possible move we use the alpha-beta algorithm.

## Chess
Contains all the relevant parts of the chess game, such as the board, figures, representation of the moves, ...

### Board
For the board we use the representation called 0x88, using with an array of figures with the size of 128. The board is also responsible for remembering the figure on the field of the board.

### Figures
There is a base class figure, which all other figures (king, queen, knight, bishop, rook) inherit from. The figures are responsible for returning there valid moves.
If the figure has the position -1, it means that the figure is not on the board anymore (it has been captured).

### Move
Represented as class, containting the new and old fields, the figures on these two fields, and if the move was a special move (castling, pawn promotion).

## Foundation
We base our implementation on Java.
