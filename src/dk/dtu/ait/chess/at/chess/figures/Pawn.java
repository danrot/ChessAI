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

    @Override
    public List<Move> getMoves(Board board) {
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
