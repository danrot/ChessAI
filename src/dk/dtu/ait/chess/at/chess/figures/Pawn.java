package dk.dtu.ait.chess.at.chess.figures;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;

import java.awt.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johannes
 * Date: 18.04.13
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class Pawn extends Figure {
    public Pawn(int position, Color color) {
        super(position, color);
    }

    @Override
    public List<Move> getMoves(Board board) {
        return null; //TODO implement
    }
}
