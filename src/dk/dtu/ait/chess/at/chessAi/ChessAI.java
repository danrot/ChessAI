package dk.dtu.ait.chess.at.chessAi;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;
import java.awt.Color;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 18.04.13
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class ChessAI {
    
    private boolean running;
    private Timer timer;
    private int seconds;
    private Color color;
    
    public ChessAI()
    {
        running = false;
        timer = new Timer();
    }
    
    public ChessAI(int seconds)
    {
        super();
        this.seconds = seconds; 
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public Move getMove(Board board) {
        
        Move m = new Move();
        int i = 1;
        timer.schedule(new AITimerTask(), seconds*1000 - 50);
        while(running)
        {
            this.move(board, i, m);
            i++;
        }
        return m;
    }
    
    public void move(Board board, int searchDepth, Move next) {
        this.max(board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, searchDepth, next);
    }
    
    private int max(Board state, int alpha, int beta, int searchDepth, int maxSearchDepth, Move next)
    {
        if(state.isFinished() || searchDepth == maxSearchDepth)
        {
            return this.staticEvaluation(state, searchDepth);
        }
        
        List<Move> childs = state.getAllPossibleMoves();
        while (alpha < beta && running)
        {
            if (childs.isEmpty())
            {
                break;
            }
            state.apply(childs.get(0));
            int v = min(state, alpha, beta, searchDepth+1, maxSearchDepth, next);
            
            if (v > alpha)
            {
                alpha = v;
                if (searchDepth == 0)
                {
                    next.setNewField(childs.get(0).getNewField());
                    next.setOldField(childs.get(0).getOldField());
                    next.setNewFigure(childs.get(0).getNewFigure());
                    next.setOldFigure(childs.get(0).getOldFigure());
                    next.setSpecial(childs.get(0).getSpecial());
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
            return this.staticEvaluation(state, searchDepth);
        }
        
        List<Move> childs = state.getAllPossibleMoves();
        while (alpha < beta && running)
        {
            if (childs.isEmpty())
            {
                break;
            }
            
            state.apply(childs.get(0));
            int v = max(state, alpha, beta, searchDepth+1, maxSearchDepth, next);
            if (v < beta)
            {
                beta = v;
                if (searchDepth == 1)
                {
                    next.setNewField(childs.get(0).getNewField());
                    next.setOldField(childs.get(0).getOldField());
                    next.setNewFigure(childs.get(0).getNewFigure());
                    next.setOldFigure(childs.get(0).getOldFigure());
                    next.setSpecial(childs.get(0).getSpecial());
                }
            }
            childs.remove(0);
        }
        return beta;
    }
    
    private class AITimerTask extends TimerTask
    {
        @Override
        public void run() {
            running = false;
        }
        
    }
}