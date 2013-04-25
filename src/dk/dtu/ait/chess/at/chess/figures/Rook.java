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
public class Rook extends Figure {
    public Rook(int position, Color color) {
        super(position, color);
    }

    @Override
    public List<Move> getMoves(Board board) {
        ArrayList<Move> ret = new ArrayList<Move>(30);
        for (int i = 1; i < 8; i++) {
            Move u = new Move();
            Move r = new Move();
            Move l = new Move();
            Move d = new Move();

            u.setOldField(position);
            r.setOldField(position);
            l.setOldField(position);
            d.setOldField(position);

            u.setOldFigure(this);
            r.setOldFigure(this);
            l.setOldFigure(this);
            d.setOldFigure(this);

            switch (i) {
                case 1:
                    u.setNewField(position + 0x10);
                    r.setNewField(position + 0x01);
                    l.setNewField(position - 0x01);
                    d.setNewField(position - 0x10);
                    break;
                case 2:
                    u.setNewField(position + 0x20);
                    r.setNewField(position + 0x02);
                    l.setNewField(position - 0x02);
                    d.setNewField(position - 0x20);
                    break;
                case 3:
                    u.setNewField(position + 0x30);
                    r.setNewField(position + 0x03);
                    l.setNewField(position - 0x03);
                    d.setNewField(position - 0x30);
                    break;
                case 4:
                    u.setNewField(position + 0x40);
                    r.setNewField(position + 0x04);
                    l.setNewField(position - 0x04);
                    d.setNewField(position - 0x40);
                    break;
                case 5:
                    u.setNewField(position + 0x50);
                    r.setNewField(position + 0x05);
                    l.setNewField(position - 0x05);
                    d.setNewField(position - 0x50);
                    break;
                case 6:
                    u.setNewField(position + 0x60);
                    r.setNewField(position + 0x06);
                    l.setNewField(position - 0x06);
                    d.setNewField(position - 0x60);
                    break;
                case 7:
                    u.setNewField(position + 0x70);
                    r.setNewField(position + 0x07);
                    l.setNewField(position - 0x07);
                    d.setNewField(position - 0x70);
                    break;
                case 8:
                    u.setNewField(position + 0x80);
                    r.setNewField(position + 0x08);
                    l.setNewField(position - 0x08);
                    d.setNewField(position - 0x80);
                    break;
                default:
                    throw new RuntimeException();
            }
            if (board.check(u))
                ret.add(u);
            if (board.check(r))
                ret.add(r);
            if (board.check(l))
                ret.add(l);
            if (board.check(d))
                ret.add(d);
        }
        //TODO Casteling!!!!
        return ret;
    }
}
