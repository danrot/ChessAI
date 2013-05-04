/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.dtu.ait.chess.at.chessEngine;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;
import dk.dtu.ait.chess.at.chess.figures.Queen;
import dk.dtu.ait.chess.at.chessAi.ChessAI;
import dk.dtu.ait.chess.at.chessAi.strategy.FigureValueStrategy;

import java.awt.Color;
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
        this.chessAi = new ChessAI(new FigureValueStrategy());
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
    
    /**
     * Running the Chess Engine (Wait for command for XBoard)
     */
    private void run()
    {
        this.running = true;
        while (running)
        {
            String nextCmd = scan.next();
            if (nextCmd.equals("go"))
            {
               if(!doMove(board))
               {
                   System.out.println("Wrong Move!!!");
               }
            }
            else if(nextCmd.equals("black"))
            {
                this.chessAi.setColor(Color.BLACK);
            }
            else if(nextCmd.equals("white"))
            {
                this.chessAi.setColor(Color.WHITE);
            }
            else if (nextCmd.equals("quit"))
            {
                running = false;
            }
            else if (nextCmd.matches("([a-h][1-8]){2}[q]?"))
            {
                //Parse String vom recieved String
                String newPos = nextCmd.substring(2, 4);
                String oldPos = nextCmd.substring(0, 2);
                
                //Convert Row and Colum numbers to Interger Board Position
                Integer colOldPos = (int)oldPos.charAt(0)-97;
                Integer colNewPos = (int)newPos.charAt(0)-97;
                Integer rowOldPos = (int)oldPos.charAt(1)-49;
                Integer rowNewPos = (int)newPos.charAt(1)-49;
                
                //Add Row and Column number to one String
                String oldPosParsed = colOldPos.toString() + rowOldPos.toString();
                String newPosParsed = colNewPos.toString() + rowNewPos.toString();
                
                //Convert String to hexadecimal Integer
                int indexOld = Integer.parseInt(oldPosParsed, 16);
                int indexNew = Integer.parseInt(newPosParsed, 16);
                
                //Create Move
                Move recieved = new Move();
                recieved.setNewField(indexNew);
                recieved.setOldField(indexOld);
                recieved.setOldFigure(this.board.getFigure(indexOld));
                recieved.setNewFigure(this.board.getFigure(indexNew));
                
                //check for special Moves
                if(nextCmd.endsWith("q"))
                {
                    recieved.setSpecial(true);
                    recieved.setNewFigure(new Queen(indexNew, recieved.getOldFigure().getColor()));
                }
                else if(isCastlingMove(nextCmd))
                {
                    recieved.setSpecial(true);
                    recieved.setNewFigure(null);
                }
                
                //Apply move on the board
                this.board.apply(recieved);
                if (!doMove(board))
                {
                    System.out.println("Wrong move!!!");
                }
                System.out.println("Regex true");
            }
        }
    }
    
    /**
     * Finds a new Move from the chessAI applies the move to the board and sends the move to Xboard
     * @param board the current game board
     * @return true if a Move was found, false otherwise
     */
    private boolean doMove(Board board)
    {
        Move move = this.chessAi.getMove(board);
        if (move == null)
        {
            return false;
        }
        boolean result = this.board.apply(move);
                        
        assert result;
        if (result)
        {
            String s = this.convert(move);
            System.out.println(s);
        }
        return result;

    }
    
    /**
     * Converts a Move to the Protocol used to communicate with xBoard
     * @param m
     * @return 
     */
    private String convert(Move m)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("move ");
        builder.append(Integer.toHexString(m.getOldField()));
        builder.append(Integer.toHexString(m.getNewField()));
        
        if(m.getNewFigure().getName().equals("Queen") && m.getOldFigure().getName().equals("Pawn") && m.getSpecial())
        {
            builder.append("q");
        }
        
        return builder.toString();
    }

    /**
     * checks wheter the recieved command is a casteling move
     * @param nextCmd the recieved command
     * @return true if recieved Move is a castelling move, otherwise false
     */
    private boolean isCastlingMove(String nextCmd) {
        return nextCmd.equals("e1c1") || nextCmd.equals("e1g1") || nextCmd.equals("e8c8") || nextCmd.equals("e8g8");
    }
    
}
