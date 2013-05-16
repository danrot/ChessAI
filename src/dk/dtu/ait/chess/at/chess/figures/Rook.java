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

    static {
        generateMoves();
    }

    @Override
    public List<Move> getMoves(Board board, ArrayList<Move> firsMoves, Integer first) {
        ArrayList<Move> ret = new ArrayList<Move>(50);
        for (int i = 0; i < moves[position].length; i++) {
            Move m = new Move(moves[position][i]);
            m.setOldFigure(this);
            if (m.getNewField() == first) {
                firsMoves.add(m);
            } else {
                ret.add(m);
            }
        }
        return ret;
    }

    private static Move[][] moves;

    private static void generateMoves() {
        moves = new Move[128][];

        for (int ii = 0; ii < 128; ii++) {
            ArrayList<Move> ret = new ArrayList<Move>(30);
            //up
            for (int i = 1; i < 8; i++) {
                Move u = new Move();
                u.setOldField(ii);
                switch (i) {
                    case 1:
                        u.setNewField(ii + 0x10);
                        break;
                    case 2:
                        u.setNewField(ii + 0x20);
                        break;
                    case 3:
                        u.setNewField(ii + 0x30);
                        break;
                    case 4:
                        u.setNewField(ii + 0x40);
                        break;
                    case 5:
                        u.setNewField(ii + 0x50);
                        break;
                    case 6:
                        u.setNewField(ii + 0x60);
                        break;
                    case 7:
                        u.setNewField(ii + 0x70);
                        break;
                    case 8:
                        u.setNewField(ii + 0x80);
                        break;
                    default:
                        throw new RuntimeException();
                }
                if ((u.getNewField() & 0x88) == 0 && u.getNewField() >= 0)
                    ret.add(u);
            }
            //right
            for (int i = 1; i < 8; i++) {

                Move r = new Move();
                r.setOldField(ii);
                switch (i) {
                    case 1:
                        r.setNewField(ii + 0x01);
                        break;
                    case 2:
                        r.setNewField(ii + 0x02);
                        break;
                    case 3:
                        r.setNewField(ii + 0x03);
                        break;
                    case 4:
                        r.setNewField(ii + 0x04);
                        break;
                    case 5:
                        r.setNewField(ii + 0x05);
                        break;
                    case 6:
                        r.setNewField(ii + 0x06);
                        break;
                    case 7:
                        r.setNewField(ii + 0x07);
                        break;
                    case 8:
                        r.setNewField(ii + 0x08);
                        break;
                    default:
                        throw new RuntimeException();
                }
                if ((r.getNewField() & 0x88) == 0 && r.getNewField() >= 0)
                    ret.add(r);
            }
            //down
            for (int i = 1; i < 8; i++) {
                Move d = new Move();
                d.setOldField(ii);
                switch (i) {
                    case 1:
                        d.setNewField(ii - 0x10);
                        break;
                    case 2:
                        d.setNewField(ii - 0x20);
                        break;
                    case 3:
                        d.setNewField(ii - 0x30);
                        break;
                    case 4:
                        d.setNewField(ii - 0x40);
                        break;
                    case 5:
                        d.setNewField(ii - 0x50);
                        break;
                    case 6:
                        d.setNewField(ii - 0x60);
                        break;
                    case 7:
                        d.setNewField(ii - 0x70);
                        break;
                    case 8:
                        d.setNewField(ii - 0x80);
                        break;
                    default:
                        throw new RuntimeException();
                }
                if ((d.getNewField() & 0x88) == 0 && d.getNewField() >= 0)
                    ret.add(d);

            }
            //left
            for (int i = 1; i < 8; i++) {
                Move l = new Move();
                l.setOldField(ii);
                switch (i) {
                    case 1:
                        l.setNewField(ii - 0x01);
                        break;
                    case 2:
                        l.setNewField(ii - 0x02);
                        break;
                    case 3:
                        l.setNewField(ii - 0x03);
                        break;
                    case 4:
                        l.setNewField(ii - 0x04);
                        break;
                    case 5:
                        l.setNewField(ii - 0x05);
                        break;
                    case 6:
                        l.setNewField(ii - 0x06);
                        break;
                    case 7:
                        l.setNewField(ii - 0x07);
                        break;
                    case 8:
                        l.setNewField(ii - 0x08);
                        break;
                    default:
                        throw new RuntimeException();
                }
                if ((l.getNewField() & 0x88) == 0 && l.getNewField() >= 0)
                    ret.add(l);

            }
            moves[ii] = ret.toArray(new Move[0]);
        }

    }

    @Override
    public FigureType getType() {
        return FigureType.ROOK;
    }
}
