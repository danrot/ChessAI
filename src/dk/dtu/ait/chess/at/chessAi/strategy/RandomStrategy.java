package dk.dtu.ait.chess.at.chessAi.strategy;

import dk.dtu.ait.chess.at.chess.Board;

import java.awt.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: johannes
 * Date: 04.05.13
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class RandomStrategy implements Strategy{
    @Override
    public int evaluateBoard(Board b, Color c) {
        return new Random().nextInt();
    }
}
