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
    public List<Move> getMoves(Board board, Integer first) {
        ArrayList<Move> ret = new ArrayList<Move>(8);
        for (int i = 0; i < moves[position].length; i++) {
            Move m = new Move(moves[position][i]);
            m.setOldFigure(this);
            ret.add(m);
        }
        return ret;
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

    static {
        generateMoves();
    }

    private static Move[][] moves;

    private static void generateMoves() {
        moves = new Move[128][];

        for (int ii = 0; ii < 128; ii++) {

            if ((ii & 0x88) == 0) {
                ArrayList<Move> ret = new ArrayList<Move>(30);
                //up
                for (int i = 1; i < 8; i++) {
                    Move u = new Move();
                    u.setOldField(ii);
                    u.setNewField(ii + i * 0x10);

                    if ((u.getNewField() & 0x88) == 0 && u.getNewField() >= 0)
                        ret.add(u);
                }
                //right
                for (int i = 1; i < 8; i++) {

                    Move r = new Move();
                    r.setOldField(ii);
                    r.setNewField(ii + i * 0x01);

                    if ((r.getNewField() & 0x88) == 0 && r.getNewField() >= 0)
                        ret.add(r);
                }
                //down
                for (int i = 1; i < 8; i++) {
                    Move d = new Move();
                    d.setOldField(ii);
                    d.setNewField(ii - i * 0x10);

                    if ((d.getNewField() & 0x88) == 0 && d.getNewField() >= 0)
                        ret.add(d);

                }
                //left
                for (int i = 1; i < 8; i++) {
                    Move l = new Move();
                    l.setOldField(ii);
                    l.setNewField(ii - i * 0x01);

                    if ((l.getNewField() & 0x88) == 0 && l.getNewField() >= 0)
                        ret.add(l);

                }

                for (int i = 1; i < 8; i++) {

                    Move ru = new Move();
                    ru.setOldField(ii);
                    ru.setNewField(ii + i * 0x11);
                    if ((ru.getNewField() & 0x88) == 0 && ru.getNewField() >= 0)
                        ret.add(ru);

                    Move lu = new Move();
                    lu.setOldField(ii);
                    lu.setNewField(ii + i * (0x10 - 0x01));
                    if ((lu.getNewField() & 0x88) == 0 && lu.getNewField() >= 0)
                        ret.add(lu);

                    Move rd = new Move();
                    rd.setOldField(ii);
                    rd.setNewField(ii - i * (0x10 + 0x01));
                    if ((rd.getNewField() & 0x88) == 0 && rd.getNewField() >= 0)
                        ret.add(rd);

                    Move ld = new Move();
                    ld.setOldField(ii);
                    ld.setNewField(ii - i * 0x11);
                    if ((ld.getNewField() & 0x88) == 0 && ld.getNewField() >= 0)
                        ret.add(ld);
                }
                moves[ii] = ret.toArray(new Move[0]);
            }
        }
    }

    @Override
    public FigureType getType() {
        return FigureType.QUEEN;
    }
}
