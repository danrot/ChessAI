package dk.dtu.ait.chess.at.chessAi;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 18.04.13
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class ChessAI {
    
    public Move getMove(Board board) {
        
        Move m = null;
        for(int i = 0; i < 10; i++)
        {
            m = this.move(board, 10);
        }
        return m;
    }
    
    public Move move(Board board, int searchDepth) {
        Move next = new Move();
        this.max(board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, searchDepth, next);
        return next;
    }
    
    private int max(Board state, int alpha, int beta, int searchDepth, int maxSearchDepth, Move next)
    {
        if(state.isFinished() || searchDepth == maxSearchDepth)
        {
            return this.staticEvaluation(state, searchDepth);
        }
        
        List<Move> childs = state.getPossibleMoves();
        while (alpha < beta)
        {
            if (childs.isEmpty())
            {
                break;
            }
            int v = min(childs.get(0).getField(), alpha, beta, searchDepth+1, maxSearchDepth, next);
            
            if (v > alpha)
            {
                alpha = v;
                if (searchDepth == 0)
                {
                    next.setField(childs.get(0).getField());
                    next.setMove(childs.get(0).getMove());
                }
            }
            childs.remove(0);
        }
        return alpha;
    }
    
    private int min(Board state, int alpha, int beta, int searchDepth, int maxSearchDepth, Move next)
    {
        if(state.isFinished() || searchDepth == maxSearchDepth)
        {
            return this.staticEvaluation(state, searchDept);
        }
        
        List<Move> childs = state.getPossibleMoves(opponent.sign);
        while (alpha < beta)
        {
            if (childs.isEmpty())
            {
                break;
            }
            int v = max(childs.get(0).getField(), alpha, beta, searchDept+1, next);
            if (v < beta)
            {
                beta = v;
                if (searchDept == 1)
                {
                    next.setField(childs.get(0).getField());
                    next.setMove(childs.get(0).getMove());
                }
            }
            childs.remove(0);
        }
        return beta;
    }
}
