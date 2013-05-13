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
        ArrayList<Move>  ret = new ArrayList<Move>(8);
        for(int i = 0; i < moves[position].length; i++)
        {
            Move m = new Move(moves[position][i]);
            m.setOldFigure(this);
            ret.add(m);
        }
        return  ret;
    }

    static {
        generateMoves();
    }

    private static Move[][] moves;

    private static void generateMoves()
    {
        moves = new Move[128][];

        for(int i = 0; i < 128; i++)
        {
            ArrayList<Move> ret = new ArrayList<Move>(8);

            Move tUoL = new Move();
            tUoL.setOldField(i);
            tUoL.setNewField(i + 0x20 - 0x01);

            if((tUoL.getNewField() & 0x88) == 0 && tUoL.getNewField() > 0)
                ret.add(tUoL);

            Move tUoR = new Move();
            tUoR.setOldField(i);
            tUoR.setNewField(i + 0x20 + 0x01);

            if((tUoR.getNewField() & 0x88) == 0 && tUoR.getNewField() > 0)
                ret.add(tUoR);

            Move tLoU = new Move();
            tLoU.setOldField(i);
            tLoU.setNewField(i + 0x10 - 0x02);

            if((tLoU.getNewField() & 0x88) == 0 && tLoU.getNewField() > 0)
                ret.add(tLoU);

            Move oDtL = new Move();
            oDtL.setOldField(i);
            oDtL.setNewField(i - 0x10 - 0x02);

            if((oDtL.getNewField() & 0x88) == 0 && oDtL.getNewField() > 0)
                ret.add(oDtL);

            Move tDoL = new Move();
            tDoL.setOldField(i);
            tDoL.setNewField(i - 0x20 - 0x01);

            if((tDoL.getNewField() & 0x88) == 0 && tDoL.getNewField() > 0)
                ret.add(tDoL);

            Move oUtR = new Move();
            oUtR.setOldField(i);
            oUtR.setNewField(i + 0x10 + 0x02);

            if((oUtR.getNewField() & 0x88) == 0 && oUtR.getNewField() > 0)
                ret.add(oUtR);

            Move oDtR = new Move();
            oDtR.setOldField(i);
            oDtR.setNewField(i - 0x10 + 0x02);

            if((oDtR.getNewField() & 0x88) == 0 && oDtR.getNewField() > 0)
                ret.add(oDtR);

            Move tDoR = new Move();
            tDoR.setOldField(i);
            tDoR.setNewField(i - 0x20 + 0x01);

            if((tDoR.getNewField() & 0x88) == 0 && tDoR.getNewField() > 0)
                ret.add(tDoR);

            moves[i] = ret.toArray(new Move[0]);
        }
    }

    @Override
    public FigureType getType() {
        return FigureType.KNIGHT;
    }
}
