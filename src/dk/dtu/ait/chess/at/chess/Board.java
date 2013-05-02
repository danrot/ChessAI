package dk.dtu.ait.chess.at.chess;

import dk.dtu.ait.chess.at.chess.figures.Figure;
import dk.dtu.ait.chess.at.chess.figures.Queen;

import java.awt.*;
import java.util.ArrayList;
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
     * The bitmask to check if the field is on the board
     */
    private final int BOARD_MASK = 0x88;

    public Figure getFigure(int position) {
        return board[position];
    }

    public boolean apply(Move move) {
        return false; //TODO Implement
    }

    /**
     * Checks if the given move is a legal one.
     *
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

        if (figure == "King") {
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
        } else if (figure == "Queen") {

        } else if (figure == "Bishop") {

        } else if (figure == "Knight") {
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
        } else if (figure == "Rook") {
            //Check if rook has not left his row/column
            if (!((newField & 0x80) == (oldField & 0x80) &&
                    (newField & 0x08) == (oldField & 0x08)
            )) {
                return false;
            }
            //Check if there has been another figure on the way
            int sign = (newField - oldField) / Math.abs(newField - oldField);
            int step = 0;
            if ((newField & 0x80) == (oldField & 0x80)) {
                step = sign * 0x10;
            } else {
                step = sign * 0x01;
            }
            for (int i = oldField; i < newField; i += step) {
                if (getFigure(i) != null) {
                    return false;
                }
            }
        } else if (figure == "Pawn") {
            //Pawns are only allowed to move forward, except they can capture another figure
            if (!(newField - 0x10 == oldField ||
                    ((newField - 0x09 == oldField || newField - 0x11 == oldField) && move.getNewFigure().getColor() != move.getOldFigure().getColor()) ||
                    (newField - 0x20 == oldField && move.getOldFigure().hasMoved())
            )) {
                return false;
            }
        }

        move.setNewFigure(board[move.getNewField()]);
        return true;
    }

    public List<Move> getAllPossibleMoves()
    {
        ArrayList<Move> retVal = new ArrayList<Move>(250);


        return retVal;
    }
}
