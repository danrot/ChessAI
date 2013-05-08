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
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class Knight extends Figure {
    public Knight(int position, Color color) {
        super(position, color);
    }

    @Override
    public List<Move> getMoves(Board board) {
        ArrayList<Move> ret = new ArrayList<Move>(8);

        Move tUoL = new Move();
        tUoL.setOldField(position);
        tUoL.setOldFigure(this);
        tUoL.setNewField(position + 0x20 - 0x01);

        if (board.check(tUoL))
            ret.add(tUoL);

        Move tUoR = new Move();
        tUoR.setOldField(position);
        tUoR.setOldFigure(this);
        tUoR.setNewField(position + 0x20 + 0x01);

        if (board.check(tUoR))
            ret.add(tUoR);

        Move tLoU = new Move();
        tLoU.setOldField(position);
        tLoU.setOldFigure(this);
        tLoU.setNewField(position + 0x10 - 0x02);

        if (board.check(tLoU))
            ret.add(tLoU);

        Move oDtL = new Move();
        oDtL.setOldField(position);
        oDtL.setOldFigure(this);
        oDtL.setNewField(position - 0x10 - 0x02);

        if (board.check(oDtL))
            ret.add(oDtL);

        Move tDoL = new Move();
        tDoL.setOldField(position);
        tDoL.setOldFigure(this);
        tDoL.setNewField(position - 0x20 - 0x01);

        if (board.check(tDoL))
            ret.add(tDoL);

        Move oUtR = new Move();
        oUtR.setOldField(position);
        oUtR.setOldFigure(this);
        oUtR.setNewField(position + 0x10 + 0x02);

        if (board.check(oUtR))
            ret.add(oUtR);

        Move oDtR = new Move();
        oDtR.setOldField(position);
        oDtR.setOldFigure(this);
        oDtR.setNewField(position - 0x10 + 0x02);

        if (board.check(oDtR))
            ret.add(oDtR);

        Move tDoR = new Move();
        tDoR.setOldField(position);
        tDoR.setOldFigure(this);
        tDoR.setNewField(position - 0x20 + 0x01);

        if (board.check(tDoR))
            ret.add(tDoR);
        return ret;
    }

    @Override
    public FigureType getType() {
        return FigureType.KNIGHT;
    }
}
