package dk.dtu.ait.chess.at.chessAi.strategy;

import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;
import dk.dtu.ait.chess.at.chess.figures.Figure;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: johannes
 * Date: 13.05.13
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class ZobristStrategy implements Strategy {


    private static long[][] zobristTable = new long[128][12];


    private HashMap<Long, Integer> zobristValues = new HashMap<Long, Integer>(Integer.MAX_VALUE / 25);

    private Strategy strategy = null;


    public ZobristStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    private static void initZobrist() {
        Random rand = new Random(140041);

        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 12; j++) {
                zobristTable[i][j] = Math.abs(rand.nextLong());
            }
        }
    }

    static {
        initZobrist();
    }

    private long getHashValue(Board board) {

        long hash = 0;
        for(int i = 0; i < 8; i++){
            for(int j = i * 16; j< (i*16+8);j++){
                Figure f = board.getFigure(j);
                if (f != null) {
                    int multiplikator = (f.getColor() == Color.WHITE ? 0 : 6);
                    hash ^= zobristTable[j][(f.getType().ordinal() + 1) + multiplikator - 1];
                }
            }
        }
        return hash;
    }


    @Override
    public int evaluateBoard(Board board, Color myColor) {
        long zobristHash = getHashValue(board);
        if (zobristValues.containsKey(zobristHash)) {
            return zobristValues.get(zobristHash);
        } else {
            return strategy.evaluateBoard(board, myColor);
        }
    }
}
