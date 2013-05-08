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
public class Queen extends Figure {
    public Queen(int position, Color color) {
        super(position, color);
    }

    @Override
    public List<Move> getMoves(Board board) {
        ArrayList<Move> ret = new ArrayList<Move>(30);
        //up
        for (int i = 1; i < 8; i++) {
            Move u = new Move();
            u.setOldField(position);
            u.setOldFigure(this);
            switch (i) {
                case 1:
                    u.setNewField(position + 0x10);
                    break;
                case 2:
                    u.setNewField(position + 0x20);
                    break;
                case 3:
                    u.setNewField(position + 0x30);
                    break;
                case 4:
                    u.setNewField(position + 0x40);
                    break;
                case 5:
                    u.setNewField(position + 0x50);
                    break;
                case 6:
                    u.setNewField(position + 0x60);
                    break;
                case 7:
                    u.setNewField(position + 0x70);
                    break;
                case 8:
                    u.setNewField(position + 0x80);
                    break;
                default:
                    throw new RuntimeException();
            }
            if (board.check(u))
                ret.add(u);
            else
                break;
        }
        //right
        for (int i = 1; i < 8; i++) {

            Move r = new Move();
            r.setOldField(position);
            r.setOldFigure(this);
            switch (i) {
                case 1:
                    r.setNewField(position + 0x01);
                    break;
                case 2:
                    r.setNewField(position + 0x02);
                    break;
                case 3:
                    r.setNewField(position + 0x03);
                    break;
                case 4:
                    r.setNewField(position + 0x04);
                    break;
                case 5:
                    r.setNewField(position + 0x05);
                    break;
                case 6:
                    r.setNewField(position + 0x06);
                    break;
                case 7:
                    r.setNewField(position + 0x07);
                    break;
                case 8:
                    r.setNewField(position + 0x08);
                    break;
                default:
                    throw new RuntimeException();
            }
            if (board.check(r))
                ret.add(r);
            else
                break;
        }
        //down
        for (int i = 1; i < 8; i++) {
            Move d = new Move();
            d.setOldField(position);
            d.setOldFigure(this);
            switch (i) {
                case 1:
                    d.setNewField(position - 0x10);
                    break;
                case 2:
                    d.setNewField(position - 0x20);
                    break;
                case 3:
                    d.setNewField(position - 0x30);
                    break;
                case 4:
                    d.setNewField(position - 0x40);
                    break;
                case 5:
                    d.setNewField(position - 0x50);
                    break;
                case 6:
                    d.setNewField(position - 0x60);
                    break;
                case 7:
                    d.setNewField(position - 0x70);
                    break;
                case 8:
                    d.setNewField(position - 0x80);
                    break;
                default:
                    throw new RuntimeException();
            }
            if (board.check(d))
                ret.add(d);
            else
                break;
        }
        //left
        for (int i = 1; i < 8; i++) {
            Move l = new Move();
            l.setOldField(position);
            l.setOldFigure(this);
            switch (i) {
                case 1:
                    l.setNewField(position - 0x01);
                    break;
                case 2:
                    l.setNewField(position - 0x02);
                    break;
                case 3:
                    l.setNewField(position - 0x03);
                    break;
                case 4:
                    l.setNewField(position - 0x04);
                    break;
                case 5:
                    l.setNewField(position - 0x05);
                    break;
                case 6:
                    l.setNewField(position - 0x06);
                    break;
                case 7:
                    l.setNewField(position - 0x07);
                    break;
                case 8:
                    l.setNewField(position - 0x08);
                    break;
                default:
                    throw new RuntimeException();
            }
            if (board.check(l))
                ret.add(l);
            else
                break;
        }


        for (int I = 0; I < 4; I++) {
            for (int i = 1; i <= 7; i++) {
                Move m = new Move();
                m.setOldField(position);
                m.setOldFigure(this);

                switch (I) {
                    case 0:
                        switch (i) {
                            case 1:
                                m.setNewField(position + 0x11);
                                break;
                            case 2:
                                m.setNewField(position + 0x22);
                                break;
                            case 3:
                                m.setNewField(position + 0x33);
                                break;
                            case 4:
                                m.setNewField(position + 0x44);
                                break;
                            case 5:
                                m.setNewField(position + 0x55);
                                break;
                            case 6:
                                m.setNewField(position + 0x66);
                                break;
                            case 7:
                                m.setNewField(position + 0x77);
                                break;
                        }
                        break;
                    case 1:
                        switch (i) {
                            case 1:
                                m.setNewField(position - 0x11);
                                break;
                            case 2:
                                m.setNewField(position - 0x22);
                                break;
                            case 3:
                                m.setNewField(position - 0x33);
                                break;
                            case 4:
                                m.setNewField(position - 0x44);
                                break;
                            case 5:
                                m.setNewField(position - 0x55);
                                break;
                            case 6:
                                m.setNewField(position - 0x66);
                                break;
                            case 7:
                                m.setNewField(position - 0x77);
                                break;
                        }
                        break;
                    case 2:
                        switch (i) {
                            case 1:
                                m.setNewField(position - 0x01 + 0x10);
                                break;
                            case 2:
                                m.setNewField(position - 0x02 + 0x20);
                                break;
                            case 3:
                                m.setNewField(position - 0x03 + 0x30);
                                break;
                            case 4:
                                m.setNewField(position - 0x04 + 0x40);
                                break;
                            case 5:
                                m.setNewField(position - 0x05 + 0x50);
                                break;
                            case 6:
                                m.setNewField(position - 0x06 + 0x60);
                                break;
                            case 7:
                                m.setNewField(position - 0x07 + 0x70);
                                break;
                        }
                        break;
                    case 3:
                        switch (i) {
                            case 1:
                                m.setNewField(position + 0x01 - 0x10);
                                break;
                            case 2:
                                m.setNewField(position + 0x02 - 0x20);
                                break;
                            case 3:
                                m.setNewField(position + 0x03 - 0x30);
                                break;
                            case 4:
                                m.setNewField(position + 0x04 - 0x40);
                                break;
                            case 5:
                                m.setNewField(position + 0x05 - 0x50);
                                break;
                            case 6:
                                m.setNewField(position + 0x06 - 0x60);
                                break;
                            case 7:
                                m.setNewField(position + 0x07 - 0x70);
                                break;
                        }
                        break;
                }
                if (board.check(m)) {
                    ret.add(m);
                } else {
                    break;
                }
            }
        }

        return ret;

    }

    @Override
    public FigureType getType() {
        return FigureType.QUEEN;
    }
}
