package dk.dtu.ait.chess.at.chess.figures;

import dk.dtu.ait.chess.at.Move;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johannes
 * Date: 18.04.13
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public abstract class Figure {

    private int position;
    public int getPosition(){return position;}

    public abstract List<Move> getMoves();

}
