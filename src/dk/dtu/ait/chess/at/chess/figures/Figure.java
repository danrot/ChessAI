package dk.dtu.ait.chess.at.chess.figures;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *   This class is the base class for all the figures
 *
 *   It defines the main functions a figure must be able to perform
 */
public abstract class Figure {

    protected int position;
    private Color color;
    protected ArrayList<Integer> possibleMoves = new ArrayList<Integer>(50);
    protected boolean hasMoved;


    public Figure(int position, Color color)
    {
        this.color = color;
        this.position = position;
    }

    /**
     * Returns the position of the Figure on the Board.
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Returns the color of the figure
     * @return the color of the figure
     */
    public Color color()
    {
        return color;
    }

    public boolean hasMoved()
    {
       return hasMoved;
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
        return this.getClass().getName().split(".")[this.getClass().getName().split(".").length-1];
    }

}
