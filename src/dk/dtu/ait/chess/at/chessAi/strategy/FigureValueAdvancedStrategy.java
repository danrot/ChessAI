package dk.dtu.ait.chess.at.chessAi.strategy;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.figures.Figure;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: johannes
 * Date: 13.05.13
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class FigureValueAdvancedStrategy implements Strategy {

    private static final int[] PawnRow = new int[]{
            0, 0, -1, 0, 2, 14, 30, 0
    };

    private static final int[] PawnLine = new int[]{
            -2, 0, 3, 4, 5, 1, -2, -2
    };

    private static final int arrCenterManhattanDistance[] = {
            6, 5, 4, 3, 3, 4, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0,
            5, 4, 3, 2, 2, 3, 4, 5, 0, 0, 0, 0, 0, 0, 0, 0,
            4, 3, 2, 1, 1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 2, 1, 0, 0, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 2, 1, 0, 0, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0,
            4, 3, 2, 1, 1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0,
            5, 4, 3, 2, 2, 3, 4, 5, 0, 0, 0, 0, 0, 0, 0, 0,
            6, 5, 4, 3, 3, 4, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0
    };

    private static final int arrCenterDistance[] = {
            3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 2, 2, 2, 2, 2, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 2, 1, 1, 1, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 2, 1, 0, 0, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 2, 1, 0, 0, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 2, 1, 1, 1, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 2, 2, 2, 2, 2, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0
    };

    private boolean doublePawn = false;
    private int blackPoints = 0;
    private int whitePoints = 0;
    private int attacksFromMinorPiecesI = 0, attacksFromMinorPiecesOther = 0;
    private boolean attackedFromQueen = false;
    private Color myColor;

    @Override
    public int evaluateBoard(Board board, Color myColor) {
        int retVal = 0;

        blackPoints = 0;
        whitePoints = 0;
        doublePawn = false;
        attacksFromMinorPiecesI = 0;
        attacksFromMinorPiecesOther = 0;
        this.myColor = myColor;

        for (Figure f : board.getFigures()) {

            if (f != null && f.getPosition() != -1) {
                int sign = f.getColor() == myColor ? 1 : -1;

                switch (f.getType()) {
                    case QUEEN:
                        retVal += evalQueen(board, f) * sign;
                        break;
                    case KING:
                        retVal += evalKing(board, f) * sign;
                        break;
                    case BISHOP:
                        retVal += evalBishop(board, f) * sign;
                        break;
                    case KNIGHT:
                        retVal += evalKnight(board, f) * sign;
                        break;
                    case ROOK:
                        retVal += evalRook(board, f) * sign;
                        break;
                    case PAWN:
                        if ((f.getPosition() + 0x10) <= 127)
                            if (board.getFigures()[f.getPosition() + 0x10] != null &&
                                    board.getFigures()[f.getPosition() + 0x10].getColor() == myColor &&
                                    board.getFigures()[f.getPosition() + 0x10].getType() == Figure.FigureType.PAWN) {
                                doublePawn = true;
                            }
                        retVal += evalPawn(board, f) * sign;
                        break;
                }
            }
        }
        if (doublePawn) {
            retVal -= 8;
        }

        if (attacksFromMinorPiecesI == 0) {
            retVal += 2;
        } else if (attacksFromMinorPiecesI == 1) {
            retVal -= 10;
        } else {
            retVal -= 50;
        }
        return retVal;
    }

    private int evalPawn(Board board, Figure figure) {
        int retVal = 100;

        int row = (figure.getPosition() & 0xf0) / 0x10;
        int col = (figure.getPosition() & 0x0f);

        //Correct row if color is black
        if (figure.getColor() == Color.BLACK) {
            row = 7 - row;
            col = 7 - col;
        }

        retVal += PawnRow[row];
        retVal += PawnLine[col] * row / 2;

        return retVal;
    }

    private int evalRook(Board board, Figure figure) {

        Integer movement = new Integer(0);
        int att = 0;
        att += attackedFromRook(figure, board, movement);
        att += attackedFromBishop(figure, board, new Integer(0));

        if (figure.getColor() == myColor) {
            attacksFromMinorPiecesI += att;
        } else {
            attacksFromMinorPiecesOther += att;
        }
        if (attackedFromKnight(figure, board))
            if (figure.getColor() == myColor) {
                attacksFromMinorPiecesI++;
            } else {
                attacksFromMinorPiecesOther++;
            }
        if (attackedFromPawn(figure, board))
            if (figure.getColor() == myColor) {
                attacksFromMinorPiecesI++;
            } else {
                attacksFromMinorPiecesOther++;
            }

        return 500 + (int) (1.5 * movement + 0.5);
    }

    private int evalKnight(Board board, Figure figure) {
        int distance = arrCenterManhattanDistance[figure.getPosition()];
        double o = (3.0 * (4 - distance));
        double round = (o >= 0 ? 0.5 : -0.5);
        int i = (int) (o + round);
        return 300 + i;
    }

    private int evalBishop(Board board, Figure figure) {
        Integer movement = new Integer(0);
        int att = 0;
        att += attackedFromBishop(figure, board, new Integer(0));

        if (figure.getColor() == myColor) {
            attacksFromMinorPiecesI += att;
        } else {
            attacksFromMinorPiecesOther += att;
        }

        if (attackedFromKnight(figure, board))
            if (figure.getColor() == myColor) {
                attacksFromMinorPiecesI++;
            } else {
                attacksFromMinorPiecesOther++;
            }
        if (attackedFromPawn(figure, board))
            if (figure.getColor() == myColor) {
                attacksFromMinorPiecesI++;
            } else {
                attacksFromMinorPiecesOther++;
            }
        return 300 + 2 * movement;
    }

    private int evalKing(Board board, Figure figure) {

        Integer movement = new Integer(0);
        int att = 0;
        att += attackedFromRook(figure, board, movement);
        att += attackedFromBishop(figure, board, new Integer(0));

        if (figure.getColor() == myColor) {
            attacksFromMinorPiecesI += att;
        } else {
            attacksFromMinorPiecesOther += att;
        }

        if (attackedFromKnight(figure, board))
            if (figure.getColor() == myColor) {
                attacksFromMinorPiecesI++;
            } else {
                attacksFromMinorPiecesOther++;
            }
        if (attackedFromPawn(figure, board))
            if (figure.getColor() == myColor) {
                attacksFromMinorPiecesI++;
            } else {
                attacksFromMinorPiecesOther++;
            }
        if (attackedFromQueen)
            if (figure.getColor() == myColor) {
                attacksFromMinorPiecesI++;
            } else {
                attacksFromMinorPiecesOther++;
            }

        return 10000;
    }

    private int evalQueen(Board board, Figure figure) {
        Integer movement = new Integer(0);
        int att = 0;
        att += attackedFromRook(figure, board, movement);
        att += attackedFromBishop(figure, board, new Integer(0));

        if (figure.getColor() == myColor) {
            attacksFromMinorPiecesI += att;
        } else {
            attacksFromMinorPiecesOther += att;
        }

<<<<<<< HEAD
=======
        attacksFromMinorPieces += attackedFromBishop(figure, board, movement);
>>>>>>> 26c72d3a05fcd102047e90c3d3bcd1519909e889
        if (attackedFromKnight(figure, board))
            if (figure.getColor() == myColor) {
                attacksFromMinorPiecesI++;
            } else {
                attacksFromMinorPiecesOther++;
            }
        if (attackedFromPawn(figure, board))
            if (figure.getColor() == myColor) {
                attacksFromMinorPiecesI++;
            } else {
                attacksFromMinorPiecesOther++;
            }
        return 900 + movement;
    }

    private int attackedFromRook(Figure f, Board board, Integer movement) {
        int pos = f.getPosition();
        Color color = f.getColor();
        int ret = 0;
        for (int i = pos + 0x10; checkIfOnBoard(i); i += 0x10) {
            movement++;
            if (board.getFigure(i) != null && board.getFigure(i).getColor() != f.getColor()) {
                if (board.getFigure(i).getType() == Figure.FigureType.QUEEN) {
                    attackedFromQueen = true;
                    break;
                } else if (board.getFigure(i).getType() == Figure.FigureType.ROOK) {
                    ret++;
                    break;
                }
            }
            if (board.getFigure(i) != null && board.getFigure(i).getColor() == color) {
                movement--;
                break;
            }
        }
        for (int i = pos - 0x10; checkIfOnBoard(i); i -= 0x10) {
            movement++;
            if (board.getFigure(i) != null && board.getFigure(i).getColor() != f.getColor()) {
                if (board.getFigure(i).getType() == Figure.FigureType.QUEEN) {
                    attackedFromQueen = true;
                    break;
                } else if (board.getFigure(i).getType() == Figure.FigureType.ROOK) {
                    ret++;
                    break;
                }
            }
            if (board.getFigure(i) != null && board.getFigure(i).getColor() == color) {
                movement--;
                break;
            }
        }
        for (int i = pos + 0x01; checkIfOnBoard(i); i += 0x01) {
            movement++;
            if (board.getFigure(i) != null && board.getFigure(i).getColor() != f.getColor()) {
                if (board.getFigure(i).getType() == Figure.FigureType.QUEEN) {
                    attackedFromQueen = true;
                    break;
                } else if (board.getFigure(i).getType() == Figure.FigureType.ROOK) {
                    ret++;
                    break;
                }
            }
            if (board.getFigure(i) != null && board.getFigure(i).getColor() == color) {
                movement--;
                break;
            }
        }
        for (int i = pos - 0x01; checkIfOnBoard(i); i -= 0x01) {
            movement++;
            if (board.getFigure(i) != null && board.getFigure(i).getColor() != f.getColor()) {
                if (board.getFigure(i).getType() == Figure.FigureType.QUEEN) {
                    attackedFromQueen = true;
                    break;
                } else if (board.getFigure(i).getType() == Figure.FigureType.ROOK) {
                    ret++;
                    break;
                }
            }
            if (board.getFigure(i) != null && board.getFigure(i).getColor() == color) {
                movement--;
                break;
            }
        }
        return ret;
    }

    private int attackedFromBishop(Figure f, Board board, Integer movement) {
        int pos = f.getPosition();
        Color color = f.getColor();
        int ret = 0;
        for (int i = pos + 0x11; checkIfOnBoard(i); i += 0x11) {
            movement++;
            if (board.getFigure(i) != null && board.getFigure(i).getColor() != f.getColor()) {
                if (board.getFigure(i).getType() == Figure.FigureType.QUEEN) {
                    attackedFromQueen = true;
                    break;
                } else if (board.getFigure(i).getType() == Figure.FigureType.ROOK) {
                    ret++;
                    break;
                }
            }
            if (board.getFigure(i) != null && board.getFigure(i).getColor() == color) {
                movement--;
                break;
            }
        }
        for (int i = pos - 0x11; checkIfOnBoard(i); i -= 0x11) {
            movement++;
            if (board.getFigure(i) != null && board.getFigure(i).getColor() != f.getColor()) {
                if (board.getFigure(i).getType() == Figure.FigureType.QUEEN) {
                    attackedFromQueen = true;
                    break;
                } else if (board.getFigure(i).getType() == Figure.FigureType.ROOK) {
                    ret++;
                    break;
                }
            }
            if (board.getFigure(i) != null && board.getFigure(i).getColor() == color) {
                movement--;
                break;
            }
        }
        for (int i = pos + 0x0F; checkIfOnBoard(i); i += 0x0F) {
            movement++;
            if (board.getFigure(i) != null && board.getFigure(i).getColor() != f.getColor()) {
                if (board.getFigure(i).getType() == Figure.FigureType.QUEEN) {
                    attackedFromQueen = true;
                    break;
                } else if (board.getFigure(i).getType() == Figure.FigureType.ROOK) {
                    ret++;
                    break;
                }
            }
            if (board.getFigure(i) != null && board.getFigure(i).getColor() == color) {
                movement--;
                break;
            }
        }
        for (int i = pos - 0x0F; checkIfOnBoard(i); i -= 0x0F) {
            movement++;
            if (board.getFigure(i) != null && board.getFigure(i).getColor() != f.getColor()) {
                if (board.getFigure(i).getType() == Figure.FigureType.QUEEN) {
                    attackedFromQueen = true;
                    break;
                } else if (board.getFigure(i).getType() == Figure.FigureType.ROOK) {
                    ret++;
                    break;
                }
            }
            if (board.getFigure(i) != null && board.getFigure(i).getColor() == color) {
                movement--;
                break;
            }
        }
        return ret;
    }

    private boolean attackedFromKing(Figure f, Board board) {
        int pos = f.getPosition();
        Color color = f.getColor();
        if (checkPositionColorType(color, pos, Figure.FigureType.KING, -0x11, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KING, -0x10, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KING, -0x01, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KING, -0x0F, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KING, 0x11, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KING, 0x0f, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KING, 0x10, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KING, 0x01, board)) {
            return true;
        }
        return false;
    }

    private boolean attackedFromKnight(Figure f, Board board) {
        int pos = f.getPosition();
        Color color = f.getColor();
        if (checkPositionColorType(color, pos, Figure.FigureType.KNIGHT, -0x21, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KNIGHT, -0x1f, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KNIGHT, -0x12, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KNIGHT, -0x0e, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KNIGHT, 0x21, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KNIGHT, 0x1f, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KNIGHT, 0x12, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.KNIGHT, 0x0e, board)) {
            return true;
        }
        return false;
    }

    private boolean attackedFromPawn(Figure f, Board board) {
        Color color = f.getColor();
        int pos = f.getPosition();
        int sign = (color == Color.white) ? 1 : -1;
        if (checkPositionColorType(color, pos, Figure.FigureType.PAWN, 0x11 * sign, board) ||
                checkPositionColorType(color, pos, Figure.FigureType.PAWN, 0x0f * sign, board)) {
            return true;
        }

        return false;
    }


    private boolean checkPositionColorType(Color color, int figurePos, Figure.FigureType figure, int move, Board board) {
        int pos = figurePos + move;
        return checkIfOnBoard(pos) && board.getFigures()[pos] != null && board.getFigures()[pos].getType() == figure && board.getFigures()[pos].getColor() != color;
    }

    /**
     * Returns the loop condition for the checking of check
     *
     * @param i The current place
     * @return True if the condition is true
     */
    private boolean checkIfOnBoard(int i) {
        return i > 0 && (i & 0x88) <= 0;
    }


}
