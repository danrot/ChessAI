package dk.dtu.ait.chess.at.chessAi.strategy;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.figures.Figure;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: johannes
 * Date: 13.05.13
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class FigureValueAdvancedStrategy implements Strategy {


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
                        retVal += evalQueen(board, f) * sign;
                        break;
                    case KING:
                        retVal += evalKing(board, f) * sign;
                        break;
                    case BISHOP:
                        retVal += evalBishop(board,f) * sign;
                        break;
                    case KNIGHT:
                        retVal += evalKnight(board,f) * sign;
                        break;
                    case ROOK:
                        retVal += evalRook(board,f) * sign;
                        break;
                    case PAWN:
                        retVal += evalPawn(board,f) * sign;
                        break;
                }
            }
        }
        return retVal;
    }

    private int evalPawn(Board board, Figure figure) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    private int evalRook(Board board, Figure figure) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    private int evalKnight(Board board, Figure figure) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    private int evalBishop(Board board, Figure figure) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    private int evalKing(Board board, Figure figure) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    private int evalQueen(Board board, Figure figure)
    {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }
}
