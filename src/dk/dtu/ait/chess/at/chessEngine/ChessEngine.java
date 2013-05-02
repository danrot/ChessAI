/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.dtu.ait.chess.at.chessEngine;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;
import dk.dtu.ait.chess.at.chessAi.ChessAI;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Harrasserf
 */
public class ChessEngine {
    
    private Scanner scan;
    private Board board;
    private ChessAI chessAi;
    private boolean running;
    
    public ChessEngine()
    {
        scan = new Scanner(System.in);
        this.chessAi = new ChessAI();
        board = new Board();
        running = false;
    }
    
    public ChessEngine(ChessAI ai) 
    {
        super();
        this.chessAi = ai;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ChessEngine chessEngine = new ChessEngine();
        chessEngine.run();
    }
    
    private void run()
    {
        this.running = true;
        while (running)
        {
            String nextCmd = scan.next();
            if (nextCmd.equals("go"))
            {
                boolean result = doMove(board);
            }
            else if (nextCmd.equals("quit"))
            {
                running = false;
            }
            else if (nextCmd.matches("([a-h][1-8]){2}"))
            {
                String newPos = nextCmd.substring(2);
                String oldPos = nextCmd.substring(0, 2);
                int indexOld = Integer.parseInt(oldPos, 16);
                int indexNew = Integer.parseInt(newPos, 16);
                Move recieved = new Move(indexOld, indexNew, null, null, true); //TODO get Figures from board
                this.board.apply(recieved);
                //Move m = doMove(board);
                System.out.println("Regex true");
            }
        }
    }
    
    private boolean doMove(Board board)
    {
        Move move = this.chessAi.getMove(board);
        boolean result = this.board.apply(move);
                        
        assert result;
        if (result)
        {
            String s = this.convert(move);
            System.out.println(s);
        }
        return result;

    }
    
    private String convert(Move m)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("move ");
        builder.append(Integer.toHexString(m.getOldField()));
        builder.append(Integer.toHexString(m.getNewField()));
        
        
        return builder.toString();
    }
    
}
