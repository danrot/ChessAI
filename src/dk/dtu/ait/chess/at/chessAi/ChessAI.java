package dk.dtu.ait.chess.at.chessAi;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;
import dk.dtu.ait.chess.at.chessAi.strategy.Strategy;

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
    private Strategy strategy;
    
    public ChessAI(Strategy strategy, int seconds)
    {
        running = false;
        timer = new Timer();
        this.strategy = strategy;
        this.seconds = seconds;
        this.color = Color.BLACK;
    }

    public ChessAI(Strategy strategy) {
        this(strategy, 10);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public Move getMove(Board board) {
        Move m = new Move();
        running = true;
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
    
    private int max(Board board, int alpha, int beta, int searchDepth, int maxSearchDepth, Move next)
    {
        if(board.isFinished() || searchDepth == maxSearchDepth)
        {
            return strategy.evaluateBoard(board, color);
        }
        Color c = (color == Color.BLACK ? Color.BLACK : Color.WHITE); 
        List<Move> childs = board.getAllPossibleMoves(c);
        while (alpha < beta && running)
        {
            if (childs.isEmpty())
            {
                break;
            }
            board.apply(childs.get(0));
            int v = min(board, alpha, beta, searchDepth+1, maxSearchDepth, next);
            board.undo(childs.get(0));
            
            if (v > alpha)
            {
                alpha = v;
                next.setNewField(childs.get(0).getNewField());
                next.setOldField(childs.get(0).getOldField());
                next.setNewFigure(childs.get(0).getNewFigure());
                next.setOldFigure(childs.get(0).getOldFigure());
                next.setSpecial(childs.get(0).getSpecial());
            }
            childs.remove(0);
        }
        return alpha;
    }
    
    private int min(Board board, int alpha, int beta, int searchDepth, int maxSearchDepth, Move next)
    {
        if(board.isFinished() || searchDepth == maxSearchDepth)
        {
            return strategy.evaluateBoard(board, color);
        }
        Color c = (color == Color.BLACK ? Color.WHITE : Color.BLACK); 
        List<Move> childs = board.getAllPossibleMoves(c);
        while (alpha < beta && running)
        {
            if (childs.isEmpty())
            {
                break;
            }

            board.apply(childs.get(0));
            int v = max(board, alpha, beta, searchDepth+1, maxSearchDepth, next);
            board.undo(childs.get(0));
            
            if (v < beta)
            {
                beta = v;
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
