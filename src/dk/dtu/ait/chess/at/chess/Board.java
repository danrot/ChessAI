package dk.dtu.ait.chess.at.chess;

import dk.dtu.ait.chess.at.chess.figures.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Holds all the figures on the board and is responsible for any operation including the board itself.
 * <p/>
 * Makes use of the 0x88-Notation for a chess board (http://chessprogramming.wikispaces.com/0x88).
 */
public class Board {
    /**
     * The figures on the board, represented in the 0x88-notation
     */
    private Figure[] board = new Figure[128];

    /**
     * All the white figures
     */
    private Figure[] whiteFigures = new Figure[16];

    /**
     * All the black figures
     */
    private Figure[] blackFigures = new Figure[16];

    private final int KING = 4;

    /**
     * The bitmask to check if the field is on the board
     */
    private final int BOARD_MASK = 0x88;

    private LinkedList<Integer> positionStack = new LinkedList<Integer>();


    public Board() {
        //white figures
        board[0x04] = new King(0x04, Color.white);
        board[0x03] = new Queen(0x03, Color.white);
        board[0x00] = new Rook(0x00, Color.white);
        board[0x07] = new Rook(0x07, Color.white);
        board[0x01] = new Knight(0x01, Color.white);
        board[0x06] = new Knight(0x06, Color.white);
        board[0x02] = new Bishop(0x02, Color.white);
        board[0x05] = new Bishop(0x05, Color.white);

        for (int i = 0x00; i <= 0x07; i++) {
            whiteFigures[i] = board[i];
        }

        for (int i = 0x10; i < 0x18; i++) {
            board[i] = new Pawn(i, Color.white);
            whiteFigures[i - 0x10 + 8] = board[i];
        }

        //black figures
        board[0x74] = new King(0x74, Color.black);
        board[0x73] = new Queen(0x73, Color.black);
        board[0x70] = new Rook(0x70, Color.black);
        board[0x77] = new Rook(0x77, Color.black);
        board[0x71] = new Knight(0x71, Color.black);
        board[0x76] = new Knight(0x76, Color.black);
        board[0x72] = new Bishop(0x72, Color.black);
        board[0x75] = new Bishop(0x75, Color.black);

        for (int i = 0x70; i <= 0x77; i++) {
            blackFigures[i - 0x70] = board[i];
        }

        for (int i = 0x60; i < 0x68; i++) {
            board[i] = new Pawn(i, Color.black);
            blackFigures[i - 0x60 + 8] = board[i];
        }

    }


    /**
     * Returns the figure which stands on the board on the given position
     *
     * @param position The desired position
     * @return
     */
    public Figure getFigure(int position) {
        return board[position];
    }

    /**
     * Applies the given move on the board.
     *
     * @param move The move to apply
     * @return True if the move is applied successfully, otherwise false
     */
    public boolean apply(Move move) {
        if (check(move)) {
            //Non-special moves
            board[move.getNewField()] = move.getOldFigure();
            board[move.getOldField()] = null;
            move.getOldFigure().setPosition(move.getNewField());
            if (move.getNewFigure() != null) {
                move.getNewFigure().setPosition(-1);
            }

            if (move.getSpecial()) {
                //castling (e1g1 or e1c1 resp. e8g8 or e8c8)
                if (move.getOldFigure().getType() == Figure.FigureType.KING) {
                    //e1g1
                    if (move.getOldField() == 0x04 && move.getNewField() == 0x06 &&
                            board[0x07] != null && board[0x07].getType() == Figure.FigureType.ROOK) {
                        board[0x05] = board[0x07];
                        board[0x07] = null;
                        board[0x05].setPosition(0x05);
                    }
                    //e1c1
                    else if (move.getOldField() == 0x04 && move.getNewField() == 0x02 &&
                            board[0x00] != null && board[0x00].getType() == Figure.FigureType.ROOK) {
                        board[0x03] = board[0x00];
                        board[0x00] = null;
                        board[0x03].setPosition(0x03);
                    }
                    //e8g8
                    else if (move.getOldField() == 0x74 && move.getNewField() == 0x76 &&
                            board[0x77] != null && board[0x77].getType() == Figure.FigureType.ROOK) {
                        board[0x75] = board[0x77];
                        board[0x77] = null;
                        board[0x75].setPosition(0x75);
                    }
                    //e8c8
                    else if (move.getOldField() == 0x74 && move.getNewField() == 0x72 &&
                            board[0x70] != null && board[0x70].getType() == Figure.FigureType.ROOK) {
                        board[0x73] = board[0x70];
                        board[0x70] = null;
                        board[0x73].setPosition(0x73);
                    }
                }
                //pawn promotion
                if (move.getOldFigure().getType() == Figure.FigureType.PAWN) {
                    if ((move.getNewField() & 0xf0) == 0x70 || (move.getNewField() & 0xf0) == 0x00) {
                        board[move.getNewField()] = new Queen(move.getNewField(), move.getOldFigure().getColor());
                        Figure[] figures = (move.getOldFigure().getColor() == Color.white) ? whiteFigures : blackFigures;
                        for (int i = 8; i < 16; i++) {
                            if (figures[i] == move.getOldFigure()) {
                                figures[i] = board[move.getNewField()];
                                break;
                            }
                        }
                    }
                }
            }
            move.getOldFigure().increaseMoves();

            if (checkCheck(move.getOldFigure().getColor())) {
                positionStack.add(0, 0);    //dummy
                undo(move);
                return false;
            }
            positionStack.add(0, move.getNewField());
            return true;
        }
        return false;
    }

    /**
     * Undo the given move
     *
     * @param move The move to undo
     */
    public void undo(Move move) {
        if (move.getSpecial()) {
            //castling (e1g1 or e1c1 resp. e8g8 or e8c8)
            if (move.getOldFigure().getType() == Figure.FigureType.KING) {
                //e1g1
                if (move.getOldField() == 0x04 && move.getNewField() == 0x06 &&
                        board[0x05] != null && board[0x05].getType() == Figure.FigureType.ROOK) {
                    board[0x07] = board[0x05];
                    board[0x05] = null;
                    board[0x07].setPosition(0x07);
                }
                //e1c1
                else if (move.getOldField() == 0x04 && move.getNewField() == 0x02 &&
                        board[0x03] != null && board[0x03].getType() == Figure.FigureType.ROOK) {
                    board[0x00] = board[0x03];
                    board[0x03] = null;
                    board[0x00].setPosition(0x00);
                }
                //e8g8
                else if (move.getOldField() == 0x74 && move.getNewField() == 0x76 &&
                        board[0x75] != null && board[0x75].getType() == Figure.FigureType.ROOK) {
                    board[0x77] = board[0x75];
                    board[0x75] = null;
                    board[0x77].setPosition(0x77);
                }
                //e8c8
                else if (move.getOldField() == 0x74 && move.getNewField() == 0x72 &&
                        board[0x73] != null && board[0x73].getType() == Figure.FigureType.ROOK) {
                    board[0x70] = board[0x73];
                    board[0x73] = null;
                    board[0x70].setPosition(0x70);
                }
            }

            //pawn promotion
            if (move.getOldFigure().getType() == Figure.FigureType.PAWN) {
                if ((move.getNewField() & 0xf0) == 0x70 || (move.getNewField() & 0xf0) == 0x00) {
                    Figure[] figures = (move.getOldFigure().getColor() == Color.white) ? whiteFigures : blackFigures;
                    for (int i = 8; i < 16; i++) {
                        if (figures[i] == board[move.getNewField()]) {
                            figures[i] = move.getOldFigure();
                            break;
                        }
                    }
                }
            }
        }


        board[move.getNewField()] = move.getNewFigure();
        board[move.getOldField()] = move.getOldFigure();
        move.getOldFigure().setPosition(move.getOldField());
        if (move.getNewFigure() != null) {
            move.getNewFigure().setPosition(move.getNewField());
        }
        positionStack.remove(0);
        move.getOldFigure().decreaseMoves();
    }

    /**
     * Checks if the given move is a legal one.
     * <p/>
     * Also sets the new figure in the move, as the move itself is not aware of it.
     * However, this is not done if the move is invalid.
     * <p/>
     * This method does not check if the king is in check afterwards, as this is quite hard to achieve.
     * Instead this will be done in a seperate method call in the apply-function.
     *
     * @param move The move to check
     * @return True if the move is valid, otherwise false
     */
    public boolean check(Move move) {

        if (move.getNewField() < 0 || (move.getNewField() & BOARD_MASK) > 0) {
            //Move is not allowed if the position is out of the board
            return false;
        }

        if (board[move.getNewField()] != null && board[move.getNewField()].getColor() == move.getOldFigure().getColor()) {
            //Move is also not allowed if the new position is the same color as the old one, because it is not
            //possible to capture its own figure
            return false;
        }

        //These checkings differ on the figure
        Figure.FigureType figure = move.getOldFigure().getType();
        int newField = move.getNewField();
        int oldField = move.getOldField();

        if (figure == Figure.FigureType.KING) {
            //white king 0x04
            boolean castlingWhite = (move.getSpecial() && move.getOldFigure().getColor() == Color.WHITE && !move.getOldFigure().hasMoved() &&
                    (
                            (board[0x00] != null && !board[0x00].hasMoved() && board[0x01] == null && board[0x02] == null && board[0x03] == null && newField == 0x02) ||
                                    (board[0x07] != null && !board[0x07].hasMoved() && board[0x05] == null && board[0x06] == null && newField == 0x06)
                    )
            ) && !checkCheck(Color.white);
            boolean castlingBlack = (move.getSpecial() && move.getOldFigure().getColor() == Color.BLACK && !move.getOldFigure().hasMoved() &&
                    (
                            (board[0x70] != null && !board[0x70].hasMoved() && board[0x71] == null && board[0x72] == null && board[0x73] == null && newField == 0x72) ||
                                    (board[0x77] != null && !board[0x77].hasMoved() && board[0x75] == null && board[0x76] == null && newField == 0x76)
                    )
            ) && !checkCheck(Color.black);
            //King is only allowed to move one field
            if (!(newField + 0x11 == oldField ||
                    newField + 0x10 == oldField ||
                    newField + 0x0f == oldField ||
                    newField + 0x01 == oldField ||
                    newField - 0x01 == oldField ||
                    newField - 0x0f == oldField ||
                    newField - 0x10 == oldField ||
                    newField - 0x11 == oldField ||
                    castlingWhite ||
                    castlingBlack
            )) {
                return false;
            }
        } else if (figure == Figure.FigureType.QUEEN) {
            //Valid queen moves are the union of bishop and rook
            if (!(checkBishopMove(newField, oldField, move.getOldFigure().getColor()) || checkRookMove(newField, oldField, move.getOldFigure().getColor()))) {
                return false;
            }
        } else if (figure == Figure.FigureType.BISHOP) {
            if (!checkBishopMove(newField, oldField, move.getOldFigure().getColor())) {
                return false;
            }
        } else if (figure == Figure.FigureType.KNIGHT) {
            if (!(newField - 0x21 == oldField ||
                    newField - 0x1f == oldField ||
                    newField - 0x12 == oldField ||
                    newField - 0x0e == oldField ||
                    newField + 0x21 == oldField ||
                    newField + 0x1f == oldField ||
                    newField + 0x12 == oldField ||
                    newField + 0x0e == oldField
            )) {
                return false;
            }
        } else if (figure == Figure.FigureType.ROOK) {
            if (!checkRookMove(newField, oldField, move.getOldFigure().getColor())) {
                return false;
            }
        } else if (figure == Figure.FigureType.PAWN) {
            //Pawns are only allowed to move forward, except they can capture another figure
            int sign = (move.getOldFigure().getColor() == Color.BLACK) ? -1 : 1;
            boolean normalMove = newField - 0x10 * sign == oldField && board[newField] == null;
            boolean captureMove = (newField - 0x0f * sign == oldField || newField - 0x11 * sign == oldField) &&
                    (board[newField] != null && board[newField].getColor() != move.getOldFigure().getColor());
            boolean startMove = newField - 0x20 * sign == oldField && board[newField - 0x10 * sign] == null && !move.getOldFigure().hasMoved() && board[newField] == null;
            if (!(normalMove || captureMove || startMove)) {
                return false;
            }
        }

        move.setNewFigure(board[move.getNewField()]);
        return true;
    }

    private boolean checkCheck(Color color) {
        //Get kings position
        Figure[] figures = (color == Color.white) ? whiteFigures : blackFigures;
        int pos = figures[KING].getPosition();

        //Check if some figures can capture the king

        //Rook and Queen
        for (int i = pos + 0x10; getCheckLoopCondition(i); i += 0x10) {
            if (checkCheckRook(color, pos + 0x10, i)) {
                return true;
            }
            Figure f = getFigure(i);
            if (f != null && (f.getColor() == color || (f.getType() != Figure.FigureType.ROOK && f.getType() != Figure.FigureType.QUEEN))) {
                break;
            }
        }
        for (int i = pos - 0x10; getCheckLoopCondition(i); i -= 0x10) {
            if (checkCheckRook(color, pos - 0x10, i)) {
                return true;
            }
            Figure f = getFigure(i);
            if (f != null && (f.getColor() == color || (f.getType() != Figure.FigureType.ROOK && f.getType() != Figure.FigureType.QUEEN))) {
                break;
            }
        }
        for (int i = pos + 0x01; getCheckLoopCondition(i); i += 0x01) {
            if (checkCheckRook(color, pos + 0x01, i)) {
                return true;
            }
            Figure f = getFigure(i);
            if (f != null && (f.getColor() == color || (f.getType() != Figure.FigureType.ROOK && f.getType() != Figure.FigureType.QUEEN))) {
                break;
            }
        }
        for (int i = pos - 0x01; getCheckLoopCondition(i); i -= 0x01) {
            if (checkCheckRook(color, pos - 0x01, i)) {
                return true;
            }
            Figure f = getFigure(i);
            if (f != null && (f.getColor() == color || (f.getType() != Figure.FigureType.ROOK && f.getType() != Figure.FigureType.QUEEN))) {
                break;
            }
        }

        //Bishop and Queen (0x0f, 0x11)
        for (int i = pos + 0x11; getCheckLoopCondition(i); i += 0x11) {
            if (checkCheckBishop(color, pos + 0x11, i)) {
                return true;
            }
            Figure f = getFigure(i);
            if (f != null && (f.getColor() == color || (f.getType() != Figure.FigureType.BISHOP && f.getType() != Figure.FigureType.QUEEN))) {
                break;
            }
        }
        for (int i = pos - 0x11; getCheckLoopCondition(i); i -= 0x11) {
            if (checkCheckBishop(color, pos - 0x11, i)) {
                return true;
            }
            Figure f = getFigure(i);
            if (f != null && (f.getColor() == color || (f.getType() != Figure.FigureType.BISHOP && f.getType() != Figure.FigureType.QUEEN))) {
                break;
            }
        }
        for (int i = pos + 0x0f; getCheckLoopCondition(i); i += 0x0f) {
            if (checkCheckBishop(color, pos + 0x0f, i)) {
                return true;
            }
            Figure f = getFigure(i);
            if (f != null && (f.getColor() == color || (f.getType() != Figure.FigureType.BISHOP && f.getType() != Figure.FigureType.QUEEN))) {
                break;
            }
        }
        for (int i = pos - 0x0f; getCheckLoopCondition(i); i -= 0x0f) {
            if (checkCheckBishop(color, pos - 0x0f, i)) {
                return true;
            }
            Figure f = getFigure(i);
            if (f != null && (f.getColor() == color || (f.getType() != Figure.FigureType.BISHOP && f.getType() != Figure.FigureType.QUEEN))) {
                break;
            }
        }

        //Knight
        if (checkCheckMove(color, pos, Figure.FigureType.KNIGHT, -0x21) ||
                checkCheckMove(color, pos, Figure.FigureType.KNIGHT, -0x1f) ||
                checkCheckMove(color, pos, Figure.FigureType.KNIGHT, -0x12) ||
                checkCheckMove(color, pos, Figure.FigureType.KNIGHT, -0x0e) ||
                checkCheckMove(color, pos, Figure.FigureType.KNIGHT, 0x21) ||
                checkCheckMove(color, pos, Figure.FigureType.KNIGHT, 0x1f) ||
                checkCheckMove(color, pos, Figure.FigureType.KNIGHT, 0x12) ||
                checkCheckMove(color, pos, Figure.FigureType.KNIGHT, 0x0e)) {
            return true;
        }

        //Pawn
        int sign = (color == Color.white) ? 1 : -1;
        if (checkCheckMove(color, pos, Figure.FigureType.PAWN, 0x11 * sign) ||
                checkCheckMove(color, pos, Figure.FigureType.PAWN, 0x0f * sign)) {
            return true;
        }

        return false;
    }

    private boolean checkCheckBishop(Color color, int pos, int i) {
        return board[i] != null && board[i].getColor() != color &&
                (board[i].getType() == Figure.FigureType.BISHOP || board[i].getType() == Figure.FigureType.QUEEN ||
                        (board[i].getType() == Figure.FigureType.KING && i == pos));
    }

    private boolean checkCheckRook(Color color, int pos, int i) {
        return board[i] != null && board[i].getColor() != color &&
                (board[i].getType() == Figure.FigureType.ROOK || board[i].getType() == Figure.FigureType.QUEEN ||
                        (board[i].getType() == Figure.FigureType.KING && i == pos));
    }

    private boolean checkCheckMove(Color color, int kingPos, Figure.FigureType figure, int move) {
        int pos = kingPos + move;
        return pos >= 0 && pos < 128 && board[pos] != null && board[pos].getType() == figure && board[pos].getColor() != color;
    }

    public List<Move> getAllPossibleMoves(Color color) {
        HashSet<Move> retVal = new HashSet<Move>(250);
        ArrayList<Move> firsMoves = new ArrayList<Move>(16);
        Figure[] figures = (color == Color.white) ? whiteFigures : blackFigures;
        for (Figure f : figures) {
            if (f.getPosition() != -1) { //-1 indicates that the figure is not on the board anymore

                if (!positionStack.isEmpty())
                    retVal.addAll(f.getMoves(this, firsMoves, positionStack.get(0)));
                else
                    retVal.addAll(f.getMoves(this, 0));

                if (f.getType() == Figure.FigureType.KING) {
                    if (!f.hasMoved()) {
                        if (f.getColor() == Color.white) {
                            if (board[0x00] != null && board[0x00].getType() == Figure.FigureType.ROOK && !board[0x00].hasMoved() &&
                                    board[0x01] == null && board[0x02] == null && board[0x03] == null) {          //Queenside casteling white
                                Move m = new Move();
                                m.setOldField(f.getPosition());
                                m.setNewField(0x02);
                                m.setOldFigure(f);
                                m.setSpecial(true);
                                retVal.add(m);
                            } else if (board[0x07] != null && board[0x07].getType() == Figure.FigureType.ROOK && !board[0x07].hasMoved() &&
                                    board[0x05] == null && board[0x06] == null) {     //Kingside casteling white
                                Move m = new Move();
                                m.setOldField(f.getPosition());
                                m.setNewField(0x06);
                                m.setOldFigure(f);
                                m.setSpecial(true);
                                retVal.add(m);
                            }
                        } else {
                            if (board[0x70] != null && board[0x70].getType() == Figure.FigureType.ROOK && !board[0x70].hasMoved()
                                    && board[0x71] == null && board[0x72] == null && board[0x73] == null) {          //Queenside casteling black
                                Move m = new Move();
                                m.setOldField(f.getPosition());
                                m.setNewField(0x72);
                                m.setOldFigure(f);
                                m.setSpecial(true);
                                retVal.add(m);
                            } else if (board[0x77] != null && board[0x77].getType() == Figure.FigureType.ROOK && !board[0x77].hasMoved() &&
                                    board[0x75] == null && board[0x76] == null) {     //Kingside casteling black
                                Move m = new Move();
                                m.setOldField(f.getPosition());
                                m.setNewField(0x76);
                                m.setOldFigure(f);
                                m.setSpecial(true);
                                retVal.add(m);
                            }
                        }
                    }
                }
            }
        }
        ArrayList<Move> rt = new ArrayList<Move>(retVal.size());
        rt.addAll(0, firsMoves);
        rt.addAll(retVal);
        return rt;

    }

    /**
     * Returns true if the given move is a valid bishop move, otherwise false
     *
     * @param newField The new field
     * @param oldField The old field
     * @return True if the given move is a valid bishop move, otherwise false
     */
    private boolean checkBishopMove(int newField, int oldField, Color color) {
        double step = newField - oldField;
        //Check if move was diagonal
        if (!(step % 0x0f == 0 || step % 0x11 == 0)) {
            return false;
        }
        //Check if there has been another figure on the way
        int sign = (newField - oldField) / Math.abs(newField - oldField);
        int loop = 0;
        if (step % 0x0f == 0) {
            loop = sign * 0x0f;
        } else {
            loop = sign * 0x11;
        }

        boolean oppFigure = false;
        for (int i = oldField + loop; getLoopCondition(newField, i, sign); i += loop) {
            if (oppFigure) {
                return false;
            }
            if (getFigure(i) != null) {
                if (getFigure(i).getColor() == color) {
                    return false;
                } else {
                    oppFigure = true;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if the given move is a valid rook move, otherwise false
     *
     * @param newField The new field
     * @param oldField The old field
     * @return True if the given move is a valid rook move, otherwise false
     */
    private boolean checkRookMove(int newField, int oldField, Color color) {
        //Check if rook has not left his row/column
        if (!((newField & 0xf0) == (oldField & 0xf0) ||
                (newField & 0x0f) == (oldField & 0x0f)
        )) {
            return false;
        }
        //Check if there has been another figure on the way
        int sign = (newField - oldField) / Math.abs(newField - oldField);
        int loop = 0;
        if ((newField & 0x0f) == (oldField & 0x0f)) {
            loop = sign * 0x10;
        } else {
            loop = sign * 0x01;
        }

        boolean oppFigure = false;
        for (int i = oldField + loop; getLoopCondition(newField, i, sign); i += loop) {
            if (oppFigure) {
                return false;
            }
            if (getFigure(i) != null) {
                if (getFigure(i).getColor() == color) {
                    return false;
                } else {
                    oppFigure = true;
                }
            }
        }
        return true;
    }

    /**
     * Returns the loop condition for the bishop
     *
     * @param newField The new field of the move
     * @param i        The current place
     * @param sign     The direction of the move
     * @return True if the conidition is true
     */
    private boolean getLoopCondition(int newField, int i, int sign) {
        if (sign < 0) {
            return i >= newField;
        } else {
            return i <= newField;
        }
    }

    /**
     * Returns the loop condition for the checking of check
     *
     * @param i The current place
     * @return True if the conidition is true
     */
    private boolean getCheckLoopCondition(int i) {
        return i > 0 && (i & BOARD_MASK) <= 0;
    }

    public Figure[] getFigures() {
        return board;
    }

    public boolean isFinished() {
        return false;
    }
}
