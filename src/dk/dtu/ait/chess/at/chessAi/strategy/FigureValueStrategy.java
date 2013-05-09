package dk.dtu.ait.chess.at.chessAi.strategy;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.figures.Figure;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: johannes
 * Date: 04.05.13
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
public class FigureValueStrategy implements Strategy{
    @Override
    public int evaluateBoard(Board board, Color myColor) {
        int retVal = 0;

        for(Figure f : board.getFigures())
        {

            if(f != null)
            {
               int sign = f.getColor() == myColor ? 1 : -1;
               switch (f.getType())
               {
                   case QUEEN:
                       retVal += 900 * sign;
                       break;
                   case KING:
                       retVal += 10000 * sign;
                       break;
                   case BISHOP:
                       retVal += 300 * sign;
                       break;
                   case KNIGHT:
                       retVal += 300 * sign;
                       break;
                   case ROOK:
                       retVal += 500 * sign;
                       break;
                   case PAWN:
                       retVal += 100 * sign;
                       break;
               }
            }
        }
        return retVal;
    }
}
