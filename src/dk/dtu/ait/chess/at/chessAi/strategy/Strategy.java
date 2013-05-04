package dk.dtu.ait.chess.at.chessAi.strategy;

import dk.dtu.ait.chess.at.chess.Board;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: johannes
 * Date: 04.05.13
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public interface Strategy {
    public int evaluateBoard(Board board, Color myColor);
}
