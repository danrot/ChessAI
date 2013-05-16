package dk.dtu.ait.chess.at.test.chessAi.strategy;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chessAi.strategy.Strategy;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 16.05.13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public class FigureValueAdvancedStrategy {
    Strategy s;

    @Before
    public void setup() {
        s = new dk.dtu.ait.chess.at.chessAi.strategy.FigureValueAdvancedStrategy();
    }

    @Test
    public void startWithZero() {
        Board b = new Board();
        assertEquals("Evaluation value for standard board is not 2.", 2, s.evaluateBoard(b, Color.black));
    }
}
