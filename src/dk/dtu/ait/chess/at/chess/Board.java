package dk.dtu.ait.chess.at.chess;

import dk.dtu.ait.chess.at.chess.figures.Figure;
import dk.dtu.ait.chess.at.chess.figures.Queen;

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

    /**
     * Returns the figure which stands on the board on the given position
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
            if (!move.getSpecial()) {
                //Non-special moves
                board[move.getNewField()] = move.getOldFigure();
                board[move.getOldField()] = null;
                move.getOldFigure().setPosition(move.getNewField());
                move.getNewFigure().setPosition(-1); //Highlight the figure as captured
            } else {
                //TODO special moves
            }
            return true;
        }
        return false;
    }

    /**
     * Undo the given move
     * @param move The move to undo
     */
    public void undo(Move move) {
        if (!move.getSpecial()) {
            board[move.getNewField()] = move.getNewFigure();
            board[move.getOldField()] = move.getOldFigure();
            move.getOldFigure().setPosition(move.getOldField());
            move.getNewFigure().setPosition(move.getNewField());
        } else {

        }
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
        if ((move.getNewField() & BOARD_MASK) > 0) {
            //Move is not allowed if the position is out of the board
            return false;
        }
        if (board[move.getNewField()].getColor() == move.getOldFigure().getColor()) {
            //Move is also not allowed if the new position is the same color as the old one, because it is not
            //possible to capture its own figure
            return false;
        }

        //These checkings differ on the figure
        String figure = move.getOldFigure().getName();
        int newField = move.getNewField();
        int oldField = move.getOldField();

        if (figure.equals("King")) {
            //King is only allowed to move one field
            if (!(newField + 0x11 == oldField &&
                    newField + 0x10 == oldField &&
                    newField + 0x09 == oldField &&
                    newField + 0x01 == oldField &&
                    newField - 0x01 == oldField &&
                    newField - 0x09 == oldField &&
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
            if (checkBishopMove(newField, oldField)) {
                return false;
            }
        } else if (figure.equals("Knight")) {
            if (!(newField - 0x21 == oldField &&
                    newField - 0x19 == oldField &&
                    newField - 0x12 == oldField &&
                    newField - 0x08 == oldField &&
                    newField + 0x21 == oldField &&
                    newField + 0x19 == oldField &&
                    newField + 0x12 == oldField &&
                    newField + 0x08 == oldField
            )) {
                return false;
            }
        } else if (figure.equals("Rook")) {
            if (checkRookMove(newField, oldField)) {
                return false;
            }
        } else if (figure.equals("Pawn")) {
            //Pawns are only allowed to move forward, except they can capture another figure
            int sign = (move.getOldFigure().getColor() == Color.BLACK) ? -1 : 1;
            if (!(newField - 0x10 * sign == oldField ||
                    ((newField - 0x09 * sign == oldField || newField - 0x11 * sign == oldField) && move.getNewFigure().getColor() != move.getOldFigure().getColor()) ||
                    (newField - 0x20 * sign == oldField && move.getOldFigure().hasMoved())
            )) {
                return false;
            }
        }

        move.setNewFigure(board[move.getNewField()]);
        return true;
    }

    public List<Move> getAllPossibleMoves(Color color) {
        ArrayList<Move> retVal = new ArrayList<Move>(250);
        for (int i = 0; i < board.length; i++) {
            if(board[i] != null)
            if (board[i].getColor() == color) {

                retVal.addAll(board[i].getMoves(this));

                if (board[i].getName().equalsIgnoreCase("pawn")) {
                    if (board[i].getColor() == Color.white) {
                        if (board[i].getPosition() >= 0x60) {
                            Move m = new Move();
                            Queen q = new Queen(board[i].getPosition() + 0x10, board[i].getColor());
                            m.setOldField(board[i].getPosition());
                            m.setNewField(q.getPosition());
                            m.setNewFigure(q);
                            m.setOldFigure(board[i]);
                            m.setSpecial(true);
                            retVal.add(m);

                        }
                    } else {
                        if (board[i].getPosition() <= 0x17) {
                            Move m = new Move();
                            Queen q = new Queen(board[i].getPosition() - 0x10, board[i].getColor());
                            m.setOldField(board[i].getPosition());
                            m.setNewField(q.getPosition());
                            m.setNewFigure(q);
                            m.setOldFigure(board[i]);
                            m.setSpecial(true);
                            retVal.add(m);
                        }

                    }
                } else if (board[i].getName().equalsIgnoreCase("king")) {
                    if (!board[i].hasMoved()) {
                        if (board[i].getColor() == Color.white) {
                            if (board[0].getName().equalsIgnoreCase("rook") && !board[0].hasMoved()) {          //Queenside casteling white
                                Move m = new Move();
                                m.setOldField(board[i].getPosition());
                                m.setNewField(0x02);
                                m.setOldFigure(board[i]);
                                m.setSpecial(true);
                                retVal.add(m);
                            } else if (board[7].getName().equalsIgnoreCase("rook") && !board[7].hasMoved()) {     //Kingside casteling white
                                Move m = new Move();
                                m.setOldField(board[i].getPosition());
                                m.setNewField(0x06);
                                m.setOldFigure(board[i]);
                                m.setSpecial(true);
                                retVal.add(m);
                            }
                        } else {
                            if (board[0x70].getName().equalsIgnoreCase("rook") && !board[0x70].hasMoved()) {          //Queenside casteling black
                                Move m = new Move();
                                m.setOldField(board[i].getPosition());
                                m.setNewField(0x72);
                                m.setOldFigure(board[i]);
                                m.setSpecial(true);
                                retVal.add(m);
                            } else if (board[0x77].getName().equalsIgnoreCase("rook") && !board[0x77].hasMoved()) {     //Kingside casteling black
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
        if (!(Math.floor(step / 0x09) == 0 || Math.floor(step / 0x11) == 0)) {
            return true;
        }
        //Check if there has been another figure on the way
        int sign = (newField - oldField) / Math.abs(newField - oldField);
        int loop = 0;
        if (Math.floor(step / 0x09) == 0) {
            loop = sign * 0x09;
        } else {
            loop = sign * 0x11;
        }
        for (int i = oldField; i < newField; i += loop) {
            if (getFigure(i) != null) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRookMove(int newField, int oldField) {
        //Check if rook has not left his row/column
        if (!((newField & 0x80) == (oldField & 0x80) &&
                (newField & 0x08) == (oldField & 0x08)
        )) {
            return true;
        }
        //Check if there has been another figure on the way
        int sign = (newField - oldField) / Math.abs(newField - oldField);
        int loop = 0;
        if ((newField & 0x80) == (oldField & 0x80)) {
            loop = sign * 0x10;
        } else {
            loop = sign * 0x01;
        }
        for (int i = oldField; i < newField; i += loop) {
            if (getFigure(i) != null) {
                return true;
            }
        }
        return false;
    }

    public Figure[] getFigures() {
        return board;
    }

    public boolean isFinished()
    {

    }
}
