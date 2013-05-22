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

    static {
        generateMoves();
    }

    @Override
    public List<Move> getMoves(Board board, Integer first) {
        ArrayList<Move> ret = new ArrayList<Move>(8);
        if (getColor() == Color.WHITE) {

            for (int i = 0; i < whiteMoves[position].length; i++) {
                Move m = new Move(whiteMoves[position][i]);
                m.setOldFigure(this);

                ret.add(m);
            }
        } else {
            for (int i = 0; i < blackMoves[position].length; i++) {
                Move m = new Move(blackMoves[position][i]);
                m.setOldFigure(this);

                ret.add(m);

            }
        }
        return ret;
    }


    @Override
    public List<Move> getMoves(Board board, ArrayList<Move> firsMoves, Integer first) {
        ArrayList<Move> ret = new ArrayList<Move>(3);

        if (getColor() == Color.WHITE) {

            for (int i = 0; i < whiteMoves[position].length; i++) {
                Move m = new Move(whiteMoves[position][i]);
                m.setOldFigure(this);
                if (m.getNewField() == first) {
                    firsMoves.add(m);
                } else {
                    ret.add(m);
                }
            }
        } else {
            for (int i = 0; i < blackMoves[position].length; i++) {
                Move m = new Move(blackMoves[position][i]);
                m.setOldFigure(this);
                if (m.getNewField() == first) {
                    firsMoves.add(m);
                } else {
                    ret.add(m);
                }
            }
        }
        return ret;
    }

    private static Move[][] blackMoves;
    private static Move[][] whiteMoves;

    private static void generateMoves() {
        blackMoves = new Move[128][];
        whiteMoves = new Move[128][];
        for (int i = 16; i < 104; i++)
            if ((i & 0x88) == 0){
            List<Move> retWhite = new ArrayList<Move>(3);
            List<Move> retBlack = new ArrayList<Move>(3);

            {       //white
                Move captureLeft = new Move();
                captureLeft.setNewField(i + 0x0F);
                captureLeft.setOldField(i);


                if (i >= 0x60 && i <= 0x67) {
                    captureLeft.setSpecial(true);
                }
                if ((captureLeft.getNewField() & 0x88) == 0 && captureLeft.getNewField() >= 0) {

                    retWhite.add(captureLeft);
                }

                Move captureRight = new Move();
                captureRight.setNewField(i + 0x11);
                captureRight.setOldField(i);

                if (i >= 0x60 && i <= 0x67) {
                    captureRight.setSpecial(true);
                }
                if ((captureRight.getNewField() & 0x88) == 0 && captureRight.getNewField() >= 0) {

                    retWhite.add(captureRight);
                }

                Move m1 = new Move();
                m1.setNewField(i + 0x10);
                m1.setOldField(i);
                if (i >= 0x60 && i <= 0x67) {
                    m1.setSpecial(true);
                }

                if ((m1.getNewField() & 0x88) == 0 && m1.getNewField() >= 0) {

                    retWhite.add(m1);
                }
                if (i >= 0x10 && i <= 0x17) {
                    Move m2 = new Move();
                    m2.setNewField(i + 0x20);
                    m2.setOldField(i);

                    if ((m2.getNewField() & 0x88) == 0 && m2.getNewField() >= 0) {
                        retWhite.add(m2);
                    }
                }

            }
            whiteMoves[i] = retWhite.toArray(new Move[0]);

                {     //black
                Move captureLeft = new Move();
                captureLeft.setNewField(i - 0x0F);
                captureLeft.setOldField(i);
                if (i >= 0x10 && i <= 0x17) {
                    captureLeft.setSpecial(true);
                }
                if ((captureLeft.getNewField() & 0x88) == 0 && captureLeft.getNewField() >= 0) {

                    retBlack.add(captureLeft);
                }
                Move captureRight = new Move();
                captureRight.setNewField(i - 0x11);
                captureRight.setOldField(i);

                if (i >= 0x10 && i <= 0x17) {
                    captureRight.setSpecial(true);
                }
                if ((captureRight.getNewField() & 0x88) == 0 && captureRight.getNewField() >= 0) {

                    retBlack.add(captureRight);
                }

                Move m1 = new Move();
                m1.setNewField(i - 0x10);
                m1.setOldField(i);

                if (i >= 0x10 && i <= 0x17) {
                    m1.setSpecial(true);
                }

                if ((m1.getNewField() & 0x88) == 0 && m1.getNewField() >= 0) {
                    retBlack.add(m1);
                }

                if (i >= 0x60 && i <= 0x67) {
                    Move m2 = new Move();
                    m2.setNewField(i - 0x20);
                    m2.setOldField(i);


                    if ((m2.getNewField() & 0x88) == 0 && m2.getNewField() >= 0) {
                        retBlack.add(m2);
                    }
                }
            }
            blackMoves[i] = retBlack.toArray(new Move[0]);

        }

    }

    @Override
    public FigureType getType() {
        return FigureType.PAWN;
    }
}
