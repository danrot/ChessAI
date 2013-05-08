package dk.dtu.ait.chess.at.chess;

import dk.dtu.ait.chess.at.chess.figures.*;

import java.awt.*;
import java.awt.font.NumericShaper;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.util.Random;

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
     * The bitmask to check if the field is on the board
     */
    private final int BOARD_MASK = 0x88;

    public Board() {
        //white figures
        for (int i = 0x10; i < 0x18; i++) {
            board[i] = new Pawn(i, Color.white);
        }
        board[0x00] = new Rook(0x00, Color.white);
        board[0x07] = new Rook(0x07, Color.white);
        board[0x01] = new Knight(0x01, Color.white);
        board[0x06] = new Knight(0x06, Color.white);
        board[0x02] = new Bishop(0x02, Color.white);
        board[0x05] = new Bishop(0x02, Color.white);
        board[0x03] = new Queen(0x03, Color.white);
        board[0x04] = new King(0x04, Color.white);

        //black figures
        for (int i = 0x60; i < 0x68; i++) {
            board[i] = new Pawn(i, Color.black);
        }

        board[0x70] = new Rook(0x70, Color.black);
        board[0x77] = new Rook(0x77, Color.black);
        board[0x71] = new Knight(0x71, Color.black);
        board[0x76] = new Knight(0x76, Color.black);
        board[0x72] = new Bishop(0x72, Color.black);
        board[0x75] = new Bishop(0x72, Color.black);
        board[0x73] = new Queen(0x73, Color.black);
        board[0x74] = new King(0x74, Color.black);
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

            if (move.getSpecial()) {
                //castling (e1g1 or e1c1 resp. e8g8 or e8c8)
                if (move.getOldFigure().getName().equals("King")) {
                    //e1g1
                    if (move.getOldField() == 0x04 && move.getNewField() == 0x06 &&
                            board[0x07] != null && board[0x07].getName().equals("Rook")) {
                        board[0x05] = board[0x07];
                        board[0x07] = null;
                        board[0x05].setPosition(0x05);
                    }
                    //e1c1
                    else if (move.getOldField() == 0x04 && move.getNewField() == 0x02 &&
                            board[0x00] != null && board[0x00].getName().equals("Rook")) {
                        board[0x03] = board[0x00];
                        board[0x00] = null;
                        board[0x03].setPosition(0x03);
                    }
                    //e8g8
                    else if (move.getOldField() == 0x74 && move.getNewField() == 0x76 &&
                            board[0x77] != null && board[0x77].getName().equals("Rook")) {
                        board[0x75] = board[0x77];
                        board[0x77] = null;
                        board[0x75].setPosition(0x75);
                    }
                    //e8c8
                    else if (move.getOldField() == 0x74 && move.getNewField() == 0x72 &&
                            board[0x70] != null && board[0x70].getName().equals("Rook")) {
                        board[0x73] = board[0x70];
                        board[0x70] = null;
                        board[0x73].setPosition(0x73);
                    }
                }
                //pawn promotion
                if (move.getOldFigure().getName().equals("Pawn")) {
                    if ((move.getNewField() & 0xf0) == 0x70 || (move.getNewField() & 0xf0) == 0x00) {
                        board[move.getNewField()] = new Queen(move.getNewField(), move.getOldFigure().getColor());
                    }
                }
            }
            move.getOldFigure().increaseMoves();
            return true;
        } else {
            System.out.println("Move from " + move.getOldFigure().getColor() + " " + move.getOldFigure().getName() + " " + Integer.toHexString(move.getOldField()) + Integer.toHexString(move.getNewField()) + " was not valid!");
        }
        return false;
    }

    /**
     * Undo the given move
     *
     * @param move The move to undo
     */
    public void undo(Move move) {
        board[move.getNewField()] = move.getNewFigure();
        board[move.getOldField()] = move.getOldFigure();
        move.getOldFigure().setPosition(move.getOldField());
        if (move.getNewFigure() != null) {
            move.getNewFigure().setPosition(move.getNewField());
        }

        if (move.getSpecial()) {
            //castling (e1g1 or e1c1 resp. e8g8 or e8c8)
            if (move.getOldFigure().getName().equals("King")) {
                //e1g1
                if (move.getOldField() == 0x04 && move.getNewField() == 0x06 &&
                        board[0x05] != null && board[0x05].getName().equals("Rook")) {
                    board[0x07] = board[0x05];
                    board[0x05] = null;
                    board[0x07].setPosition(0x07);
                }
                //e1c1
                else if (move.getOldField() == 0x04 && move.getNewField() == 0x02 &&
                        board[0x03] != null && board[0x03].getName().equals("Rook")) {
                    board[0x00] = board[0x03];
                    board[0x03] = null;
                    board[0x00].setPosition(0x00);
                }
                //e8g8
                else if (move.getOldField() == 0x74 && move.getNewField() == 0x76 &&
                        board[0x75] != null && board[0x75].getName().equals("Rook")) {
                    board[0x77] = board[0x75];
                    board[0x75] = null;
                    board[0x77].setPosition(0x77);
                }
                //e8c8
                else if (move.getOldField() == 0x74 && move.getNewField() == 0x72 &&
                        board[0x73] != null && board[0x73].getName().equals("Rook")) {
                    board[0x70] = board[0x73];
                    board[0x73] = null;
                    board[0x70].setPosition(0x70);
                }
            }
            //pawn promotion
            if (move.getOldFigure().getName().equals("Pawn")) {
                if ((move.getNewField() & 0xf0) == 0x70 || (move.getNewField() & 0xf0) == 0x00) {
                    board[move.getOldField()] = new Pawn(move.getNewField(), move.getOldFigure().getColor());
                }
            }
        }
        move.getOldFigure().decreaseMoves();
    }

    /**
     * Checks if the given move is a legal one.
     * <p/>
     * Also sets the new figure in the move, as the move itself is not aware of it.
     * However, this is not done if the move is invalid.
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
        String figure = move.getOldFigure().getName();
        int newField = move.getNewField();
        int oldField = move.getOldField();

        if (figure.equals("King")) {
            //TODO Allow castling
            //King is only allowed to move one field
            if (!(newField + 0x11 == oldField &&
                    newField + 0x10 == oldField &&
                    newField + 0x0f == oldField &&
                    newField + 0x01 == oldField &&
                    newField - 0x01 == oldField &&
                    newField - 0x0f == oldField &&
                    newField - 0x10 == oldField &&
                    newField - 0x11 == oldField
            )) {
                return false;
            }
        } else if (figure.equals("Queen")) {
            //Valid queen moves are the union of bishop and rook
            if (!(checkBishopMove(newField, oldField) || checkRookMove(newField, oldField))) {
                return false;
            }
        } else if (figure.equals("Bishop")) {
            if (!checkBishopMove(newField, oldField)) {
                return false;
            }
        } else if (figure.equals("Knight")) {
            if (!(newField - 0x21 == oldField &&
                    newField - 0x1f == oldField &&
                    newField - 0x12 == oldField &&
                    newField - 0x0e == oldField &&
                    newField + 0x21 == oldField &&
                    newField + 0x1f == oldField &&
                    newField + 0x12 == oldField &&
                    newField + 0x0e == oldField
            )) {
                return false;
            }
        } else if (figure.equals("Rook")) {
            if (!checkRookMove(newField, oldField)) {
                return false;
            }
        } else if (figure.equals("Pawn")) {
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

    public List<Move> getAllPossibleMoves(Color color) {
        ArrayList<Move> retVal = new ArrayList<Move>(250);
        for (int i = 0; i < board.length; i++) {
            if (board[i] != null)
                if (board[i].getColor() == color) {

                    retVal.addAll(board[i].getMoves(this));

                    if (board[i].getName().equalsIgnoreCase("king")) {
                        if (!board[i].hasMoved()) {
                            if (board[i].getColor() == Color.white) {
                                if (board[0x00] != null && board[0x00].getName().equalsIgnoreCase("rook") && !board[0x00].hasMoved() &&
                                        board[0x01] == null && board[0x02] == null && board[0x03] == null) {          //Queenside casteling white
                                    Move m = new Move();
                                    m.setOldField(board[i].getPosition());
                                    m.setNewField(0x02);
                                    m.setOldFigure(board[i]);
                                    m.setSpecial(true);
                                    retVal.add(m);
                                } else if (board[0x07] != null && board[0x07].getName().equalsIgnoreCase("rook") && !board[0x07].hasMoved() &&
                                        board[0x05] == null && board[0x06] == null) {     //Kingside casteling white
                                    Move m = new Move();
                                    m.setOldField(board[i].getPosition());
                                    m.setNewField(0x06);
                                    m.setOldFigure(board[i]);
                                    m.setSpecial(true);
                                    retVal.add(m);
                                }
                            } else {
                                if (board[0x70] != null && board[0x70].getName().equalsIgnoreCase("rook") && !board[0x70].hasMoved()
                                        && board[0x71] == null && board[0x72] == null && board[0x73] == null) {          //Queenside casteling black
                                    Move m = new Move();
                                    m.setOldField(board[i].getPosition());
                                    m.setNewField(0x72);
                                    m.setOldFigure(board[i]);
                                    m.setSpecial(true);
                                    retVal.add(m);
                                } else if (board[0x77] != null && board[0x77].getName().equalsIgnoreCase("rook") && !board[0x77].hasMoved() &&
                                        board[0x75] == null && board[0x76] == null) {     //Kingside casteling black
                                    Move m = new Move();
                                    m.setOldField(board[i].getPosition());
                                    m.setNewField(0x76);
                                    m.setOldFigure(board[i]);
                                    m.setSpecial(true);
                                    retVal.add(m);
                                }
                            }
                        }
                    }
                }
        }

        return retVal;

    }


    private boolean checkBishopMove(int newField, int oldField) {
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
        for (int i = oldField + loop; i <= newField && i >= oldField; i += loop) {
            if (getFigure(i) != null) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRookMove(int newField, int oldField) {
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
        for (int i = oldField + loop; i <= newField && i >= oldField; i += loop) {
            if (getFigure(i) != null) {
                return false;
            }
        }
        return true;
    }

    public Figure[] getFigures() {
        return board;
    }

    public boolean isFinished() {
        return false;
    }
}
