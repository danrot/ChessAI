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
            if(f.getColor() == myColor)
            {
               switch (f.getType())
               {
                   case QUEEN:
                       retVal += 900;
                       break;
                   case KING:
                       retVal += 10000;
                       break;
                   case BISHOP:
                       retVal += 300;
                       break;
                   case KNIGHT:
                       retVal += 300;
                       break;
                   case ROOK:
                       retVal += 500;
                       break;
                   case PAWN:
                       retVal += 100;
                       break;
               }
            }
        }
        return retVal;
    }
}
