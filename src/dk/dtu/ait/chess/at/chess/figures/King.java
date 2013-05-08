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
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
public class King extends Figure {
    public King(int position, Color color) {
        super(position, color);
    }

    @Override
    public List<Move> getMoves(Board board) {
        ArrayList<Move> ret = new ArrayList<Move>(5);
        Move u = new Move();
        Move d = new Move();
        Move l = new Move();
        Move r = new Move();

        u.setOldField(position);
        d.setOldField(position);
        l.setOldField(position);
        r.setOldField(position);

        u.setOldFigure(this);
        d.setOldFigure(this);
        r.setOldFigure(this);
        l.setOldFigure(this);

        if (board.check(u))
            ret.add(u);
        if (board.check(r))
            ret.add(r);
        if (board.check(l))
            ret.add(l);
        if (board.check(d))
            ret.add(d);

        return ret;
    }

    @Override
    public FigureType getType() {
        return FigureType.KING;
    }
}
