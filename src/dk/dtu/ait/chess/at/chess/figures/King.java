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

    static {
        generateMoves();
    }


    @Override
    public List<Move> getMoves(Board board) {
        ArrayList<Move> ret = new ArrayList<Move>(8);
        for (int i = 0; i < moves[position].length; i++) {
            Move m = new Move(moves[position][i]);
            m.setOldFigure(this);
            if (board.check(m)) {
                ret.add(m);
            }
        }
        return ret;
    }

    public List<Move> getMoves_alt(Board board) {
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

        u.setNewField(position + 0x10);
        d.setNewField(position - 0x10);
        l.setNewField(position - 0x01);
        r.setNewField(position + 0x01);

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

    private static Move[][] moves;

    private static void generateMoves() {
        moves = new Move[128][];
        for (int i = 0; i < 128; i++) {
            ArrayList<Move> tmpMoves = new ArrayList<Move>(8);

            Move u = new Move();
            Move d = new Move();
            Move l = new Move();
            Move r = new Move();

            u.setOldField(i);
            d.setOldField(i);
            l.setOldField(i);
            r.setOldField(i);


            u.setNewField(i + 0x10);
            d.setNewField(i - 0x10);
            l.setNewField(i - 0x01);
            r.setNewField(i + 0x01);

            Move ul = new Move();
            Move ur = new Move();
            Move dl = new Move();
            Move dr = new Move();

            ul.setOldField(i);
            ur.setOldField(i);
            dl.setOldField(i);
            dr.setOldField(i);


            ul.setNewField(i + 0x10 - 0x01);
            ur.setNewField(i + 0x10 + 0x01);
            dl.setNewField(i - 0x10 - 0x01);
            dr.setNewField(i - 0x10 + 0x01);

            if ((d.getNewField() & 0x10) > 0 && d.getNewField() > 0)
                tmpMoves.add(d);
            if ((u.getNewField() & 0x10) > 0 && u.getNewField() > 0)
                tmpMoves.add(u);
            if ((l.getNewField() & 0x10) > 0 && l.getNewField() > 0)
                tmpMoves.add(l);
            if ((r.getNewField() & 0x10) > 0 && r.getNewField() > 0)
                tmpMoves.add(r);
            if ((ul.getNewField() & 0x10) > 0 && ul.getNewField() > 0)
                tmpMoves.add(ul);
            if ((ur.getNewField() & 0x10) > 0 && ur.getNewField() > 0)
                tmpMoves.add(ur);
            if ((dl.getNewField() & 0x10) > 0 && dl.getNewField() > 0)
                tmpMoves.add(dl);
            if ((dr.getNewField() & 0x10) > 0 && dr.getNewField() > 0)
                tmpMoves.add(dr);

            moves[i] = tmpMoves.toArray(new Move[0]);
        }
    }

    @Override
    public FigureType getType() {
        return FigureType.KING;
    }
}
