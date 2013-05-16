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

    public Move(Move move)
    {
        this.oldFigure = move.oldFigure;
        this.newFigure = move.newFigure;
        this.newField = move.newField;
        this.oldField = move.oldField;
        this.special = move.special;
    }

    public Move(){}

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

    /**
     * Sets the field on which the move ends
     * @param newField The field on which the move ends
     */
    public void setNewField(int newField) {
        this.newField = newField;
    }

    /**
     * Sets the figure standing on the ending field
     * @param newFigure The figure on the ending field
     */
    public void setNewFigure(Figure newFigure) {
        this.newFigure = newFigure;
    }

    /**
     * Sets the field on which the move starts
     * @param oldField The field on which the move starts
     */
    public void setOldField(int oldField) {
        this.oldField = oldField;
    }

    /**
     * Sets the figure standing on the starting field
     * @param oldFigure The figure on the starting field
     */
    public void setOldFigure(Figure oldFigure) {
        this.oldFigure = oldFigure;
    }

    /**
     * Sets if the move is a special one
     * @param special True if the move is a special one, otherwise false
     */
    public void setSpecial(boolean special) {
        this.special = special;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (newField != move.newField) return false;
        if (oldField != move.oldField) return false;
        if (special != move.special) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = oldField;
        result = 31 * result + newField;
        result = 31 * result + (special ? 1 : 0);
        return result;
    }
}
