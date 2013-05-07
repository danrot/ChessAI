package dk.dtu.ait.chess.at.chess.figures;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;

import java.awt.*;
import java.util.List;

/**
 *   This class is the base class for all the figures
 *
 *   It defines the main functions a figure must be able to perform
 */
public abstract class Figure {

    public enum FigureType {
     QUEEN, KING, BISHOP, KNIGHT, PAWN, ROOK
    }
    protected int position;
    private Color color;
    protected boolean hasMoved;


    public Figure(int position, Color color)
    {
        this.color = color;
        this.position = position;
        this.hasMoved = false;
    }

    /**
     * Returns the position of the Figure on the Board.
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the figure on the board
     * @param position The new position of the figure
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Returns the getColor of the figure
     * @return the getColor of the figure
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Returns true if the figure has already been moved, otherwise false
     * @return True if the figure has already been moved, otherwise false
     */
    public boolean hasMoved()
    {
       return hasMoved;
    }

    /**
     * Sets if the figure has already been moved
     * @param hasMoved Defines if the figure has already been moved
     */
    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * This function is used to get all the Moves the figure can execute.
     * @return a list with all the possible moves inside.
     */
    public abstract List<Move> getMoves(Board board);


    /**
     * Returns the name of the figure. Not sure if ever used.
     * @return the name of the figure
     */
    public String getName()
    {
        String[] name = this.getClass().getName().split("\\.");
        return name[name.length - 1];
    }

    public abstract FigureType getType();

}
