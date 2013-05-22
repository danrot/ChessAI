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
            ArrayList<Move> tmpMoves = new ArrayList<Move>(28);
            for (int I = 0; I < 4; I++) {
                for (int i = 1; i <= 7; i++) {
                    Move m = new Move();
                    m.setOldField(j);
                    //   m.setOldFigure(this);

                    switch (I) {
                        case 0:   //      Right up
                            switch (i) {
                                case 1:
                                    m.setNewField(j + 0x11);
                                    break;
                                case 2:
                                    m.setNewField(j + 0x22);
                                    break;
                                case 3:
                                    m.setNewField(j + 0x33);
                                    break;
                                case 4:
                                    m.setNewField(j + 0x44);
                                    break;
                                case 5:
                                    m.setNewField(j + 0x55);
                                    break;
                                case 6:
                                    m.setNewField(j + 0x66);
                                    break;
                                case 7:
                                    m.setNewField(j + 0x77);
                                    break;
                            }
                            break;
                        case 1:     // left down
                            switch (i) {
                                case 1:
                                    m.setNewField(j - 0x11);
                                    break;
                                case 2:
                                    m.setNewField(j - 0x22);
                                    break;
                                case 3:
                                    m.setNewField(j - 0x33);
                                    break;
                                case 4:
                                    m.setNewField(j - 0x44);
                                    break;
                                case 5:
                                    m.setNewField(j - 0x55);
                                    break;
                                case 6:
                                    m.setNewField(j - 0x66);
                                    break;
                                case 7:
                                    m.setNewField(j - 0x77);
                                    break;
                            }
                            break;
                        case 2:                    // left up
                            switch (i) {
                                case 1:
                                    m.setNewField(j - 0x01 + 0x10);
                                    break;
                                case 2:
                                    m.setNewField(j - 0x02 + 0x20);
                                    break;
                                case 3:
                                    m.setNewField(j - 0x03 + 0x30);
                                    break;
                                case 4:
                                    m.setNewField(j - 0x04 + 0x40);
                                    break;
                                case 5:
                                    m.setNewField(j - 0x05 + 0x50);
                                    break;
                                case 6:
                                    m.setNewField(j - 0x06 + 0x60);
                                    break;
                                case 7:
                                    m.setNewField(j - 0x07 + 0x70);
                                    break;
                            }
                            break;
                        case 3:                   // right down
                            switch (i) {
                                case 1:
                                    m.setNewField(j + 0x01 - 0x10);
                                    break;
                                case 2:
                                    m.setNewField(j + 0x02 - 0x20);
                                    break;
                                case 3:
                                    m.setNewField(j + 0x03 - 0x30);
                                    break;
                                case 4:
                                    m.setNewField(j + 0x04 - 0x40);
                                    break;
                                case 5:
                                    m.setNewField(j + 0x05 - 0x50);
                                    break;
                                case 6:
                                    m.setNewField(j + 0x06 - 0x60);
                                    break;
                                case 7:
                                    m.setNewField(j + 0x07 - 0x70);
                                    break;
                            }
                            break;
                    }
                    if((m.getNewField() & 0x88) == 0 && m.getNewField() >= 0)
                    tmpMoves.add(m);
                }
            }
            moves[j] = tmpMoves.toArray(new Move[1]);
        }
    }

    @Override
    public FigureType getType() {
        return FigureType.BISHOP;
    }


}
