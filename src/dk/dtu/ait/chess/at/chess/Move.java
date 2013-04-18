package dk.dtu.ait.chess.at.chess;

import dk.dtu.ait.chess.at.chess.figures.Figure;

/**
 * This class represents a move.
 *
 * Therefore it saves the value of the field and the figure on this field where the moves starts ("old"-values),
 * and the field where the move goes ("new"-values). Additional there is a special-variable, containing if there is
 * a move like a pawn promotion or a castling.
 */
public class Move {
    /**
     * The field on which the move starts
     */
    private int oldField;
    /**
     * The field on which the move ends
     */
    private int newField;
    /**
     * Indicates if this instance is a special move
     */
    private boolean special;
    /**
     * The figure standing on the starting (old) field
     */
    private Figure oldFigure;
    /**
     * The figure standing on the ending (new) field
     */
    private Figure newFigure;

    /**
     *
     * @param oldField The field on which the move starts
     * @param newField The field on which the move ends
     * @param oldFigure The figure standing on the starting (old) field
     * @param newFigure The figure standing on the ending (new) field
     * @param special Indicates if this instance is a special move
     */
    public Move(int oldField, int newField, Figure oldFigure, Figure newFigure, boolean special) {
        this.oldField = oldField;
        this.newField = newField;
        this.special = special;
        this.oldFigure = oldFigure;
        this.newFigure = newFigure;
    }

    /**
     * Returns the field on which the move starts
     * @return The field on which the move starts
     */
    public int getOldField() {
        return oldField;
    }

    /**
     * Returns the field on which the move ends
     * @return The field on which the move ends
     */
    public int getNewField() {
        return newField;
    }

    /**
     * Returns if the move is a special one
     * @return True if the move is a special one, otherwise false
     */
    public boolean getSpecial() {
        return special;
    }

    /**
     * Returns the figure standing on the starting field
     * @return The figure standing on the starting field
     */
    public Figure getOldFigure() {
        return oldFigure;
    }

    /**
     * Returns the figure standing on the ending field
     * @return The figure standing on the ending field
     */
    public Figure getNewFigure() {
        return newFigure;
    }
}
