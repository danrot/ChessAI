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
    public List<Move> getMoves(Board board) {
        ArrayList<Move> ret = new ArrayList<Move>(3);

        if (getColor() == Color.WHITE) {

            for (int i = 0; i < whiteMoves[position].length; i++) {
                Move m = new Move(whiteMoves[position][i]);
                m.setOldFigure(this);
                if (board.check(m)) {
                    ret.add(m);
                }
            }
        } else {
            for (int i = 0; i < blackMoves[position].length; i++) {
                Move m = new Move(blackMoves[position][i]);
                m.setOldFigure(this);
                if (board.check(m)) {
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
        for (int i = 0; i < 128; i++) {
            List<Move> retWhite = new ArrayList<Move>(3);
            List<Move> retBlack = new ArrayList<Move>(3);

            {
                Move captureLeft = new Move();
                captureLeft.setNewField(i + 0x10 - 0x01);
                captureLeft.setOldField(i);


                if (i >= 0x60 && i <= 0x67) {
                    captureLeft.setSpecial(true);
                }
                if ((captureLeft.getNewField() & 0x10) > 0 && captureLeft.getNewField() > 0) {

                    retWhite.add(captureLeft);
                }

                Move captureRight = new Move();
                captureRight.setNewField(i + 0x11);
                captureRight.setOldField(i);

                if (i >= 0x60 && i <= 0x67) {
                    captureRight.setSpecial(true);
                }
                if ((captureRight.getNewField() & 0x10) > 0 && captureRight.getNewField() > 0) {

                    retWhite.add(captureRight);
                }

                Move m1 = new Move();
                m1.setNewField(i + 0x10);
                m1.setOldField(i);
                if (i >= 0x60 && i <= 0x67) {
                    m1.setSpecial(true);
                }

                if ((m1.getNewField() & 0x10) > 0 && m1.getNewField() > 0) {

                    retWhite.add(m1);
                }
                if (i <= 0x17) {
                    Move m2 = new Move();
                    m2.setNewField(i + 0x20);
                    m2.setOldField(i);

                    if ((m2.getNewField() & 0x10) > 0 && m2.getNewField() > 0) {
                        retWhite.add(m2);
                    }
                }

            }
            whiteMoves[i] = retWhite.toArray(new Move[0]);
            {
                Move captureLeft = new Move();
                captureLeft.setNewField(i - 0x10 - 0x01);
                captureLeft.setOldField(i);
                if (i >= 0x10 && i <= 0x17) {
                    captureLeft.setSpecial(true);
                }
                if ((captureLeft.getNewField() & 0x10) > 0 && captureLeft.getNewField() > 0) {

                    retBlack.add(captureLeft);
                }
                Move captureRight = new Move();
                captureRight.setNewField(i - 0x11);
                captureRight.setOldField(i);

                if (i >= 0x10 && i <= 0x17) {
                    captureRight.setSpecial(true);
                }
                if ((captureRight.getNewField() & 0x10) > 0 && captureRight.getNewField() > 0) {

                    retBlack.add(captureRight);
                }

                Move m1 = new Move();
                m1.setNewField(i - 0x10);
                m1.setOldField(i);

                if (i >= 0x10 && i <= 0x17) {
                    m1.setSpecial(true);
                }

                if ((m1.getNewField() & 0x10) > 0 && m1.getNewField() > 0) {
                    retBlack.add(m1);
                }

                if (i >= 60) {
                    Move m2 = new Move();
                    m2.setNewField(i - 0x20);
                    m2.setOldField(i);


                    if ((m2.getNewField() & 0x10) > 0 && m2.getNewField() > 0) {
                        retBlack.add(m2);
                    }
                }
            }
            blackMoves[i] = retBlack.toArray(new Move[0]);

        }

    }


    public List<Move> getMoves_alt(Board board) {
        List<Move> ret = new ArrayList<Move>(2);

        if (getColor() == Color.white) {
            Move captureLeft = new Move();
            captureLeft.setNewField(position + 0x10 - 0x01);
            captureLeft.setOldField(position);
            captureLeft.setOldFigure(this);


            if (position >= 0x60 && position <= 0x67) {
                captureLeft.setSpecial(true);
            }
            if (board.check(captureLeft)) {

                ret.add(captureLeft);
            }

            Move captureRight = new Move();
            captureRight.setNewField(position + 0x11);
            captureRight.setOldField(position);
            captureRight.setOldFigure(this);


            if (position >= 0x60 && position <= 0x67) {
                captureRight.setSpecial(true);
            }
            if (board.check(captureRight)) {

                ret.add(captureRight);
            }

            Move m1 = new Move();
            m1.setNewField(position + 0x10);
            m1.setOldField(position);
            m1.setOldFigure(this);

            if (position >= 0x60 && position <= 0x67) {
                m1.setSpecial(true);
            }

            if (board.check(m1)) {

                ret.add(m1);
            }
            if (!hasMoved()) {
                Move m2 = new Move();
                m2.setNewField(position + 0x20);
                m2.setOldField(position);
                m2.setOldFigure(this);

                if (board.check(m2)) {
                    ret.add(m2);
                }
            }
        } else if (getColor() == Color.black) {

            Move captureLeft = new Move();
            captureLeft.setNewField(position - 0x10 - 0x01);
            captureLeft.setOldField(position);
            captureLeft.setOldFigure(this);
            if (position >= 0x10 && position <= 0x17) {
                captureLeft.setSpecial(true);
            }
            if (board.check(captureLeft)) {

                ret.add(captureLeft);
            }
            Move captureRight = new Move();
            captureRight.setNewField(position - 0x11);
            captureRight.setOldField(position);
            captureRight.setOldFigure(this);

            if (position >= 0x10 && position <= 0x17) {
                captureRight.setSpecial(true);
            }
            if (board.check(captureRight)) {

                ret.add(captureRight);
            }

            Move m1 = new Move();
            m1.setNewField(position - 0x10);
            m1.setOldField(position);
            m1.setOldFigure(this);

            if (position >= 0x10 && position <= 0x17) {
                m1.setSpecial(true);
            }

            if (board.check(m1)) {

                ret.add(m1);
            }

            if (!hasMoved()) {
                Move m2 = new Move();
                m2.setNewField(position - 0x20);
                m2.setOldField(position);
                m2.setOldFigure(this);


                if (board.check(m2)) {

                    ret.add(m2);
                }
            }
        }

        return ret;

    }

    @Override
    public FigureType getType() {
        return FigureType.PAWN;
    }
}
