package dk.dtu.ait.chess.at.chess.figures;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;

import java.awt.*;
import java.util.ArrayList;
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
        List<Move> ret = new ArrayList<Move>(2);


        Move m1 = new Move();
        m1.setNewField(position + 0x01);
        m1.setOldField(position);
        m1.setNewFigure(this);
        if (board.check(m1))
            ret.add(m1);

        if (!hasMoved) {
            Move m2 = new Move();
            m2.setNewField(position + 0x02);
            m2.setOldField(position);
            m2.setNewFigure(this);
            if (board.check(m2))
                ret.add(m2);
        }
        return ret;

    }
}
