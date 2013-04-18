/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.dtu.ait.chess.at.chessEngine;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;
import dk.dtu.ait.chess.at.chessAi.ChessAI;
import java.util.Scanner;

/**
 *
 * @author Harrasserf
 */
public class ChessEngine {
    
    private Scanner scan;
    private Board board;
    private ChessAI chessAi;
    
    public ChessEngine()
    {
        scan = new Scanner(System.in);
        board = new Board();
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
        while (true)
        {
            String nextCmd = scan.next();
            if (nextCmd.equals("go"))
            {
                this.doMove(board);
            }
        }
    }
    
    private Move doMove(Board board)
    {
        return this.chessAi.getMove(board);
    }
    
}
