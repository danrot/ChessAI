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
public class Bishop extends Figure {


    public Bishop(int position, Color color) {
        super(position, color);
    }

    static {
        generateMoves();
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
        ArrayList<Move> ret = new ArrayList<Move>(15);
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
        for (int j = 0; j < 128; j++) {

            if ((j & 0x88) == 0) {
                ArrayList<Move> tmpMoves = new ArrayList<Move>(28);
                for (int i = 1; i < 8; i++) {

                    Move ru = new Move();
                    ru.setOldField(j);
                    ru.setNewField(j + i * 0x11);
                    if ((ru.getNewField() & 0x88) == 0 && ru.getNewField() >= 0)
                        tmpMoves.add(ru);

                    Move lu = new Move();
                    lu.setOldField(j);
                    lu.setNewField(j + i * (0x0F));
                    if ((lu.getNewField() & 0x88) == 0 && lu.getNewField() >= 0)
                        tmpMoves.add(lu);

                    Move rd = new Move();
                    rd.setOldField(j);
                    rd.setNewField(j - i * (0x0F));
                    if ((rd.getNewField() & 0x88) == 0 && rd.getNewField() >= 0)
                        tmpMoves.add(rd);

                    Move ld = new Move();
                    ld.setOldField(j);
                    ld.setNewField(j - i * 0x11);
                    if ((ld.getNewField() & 0x88) == 0 && ld.getNewField() >= 0)
                        tmpMoves.add(ld);
                }

                moves[j] = tmpMoves.toArray(new Move[1]);
            }
        }
    }

    @Override
    public FigureType getType() {
        return FigureType.BISHOP;
    }


}
