package dk.dtu.ait.chess.at.chess.figures;


import dk.dtu.ait.chess.at.chess.Board;
import dk.dtu.ait.chess.at.chess.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johannes
 * Date: 18.04.13
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class Bishop extends Figure {


    public Bishop(int position, Color color) {
        super(position, color);
    }

    @Override
    public List<Move> getMoves(Board board) {
        ArrayList<Move> ret = new ArrayList<Move>(20);


        for (int I = 0; I < 4; I++) {
            for (int i = 1; i <= 7; i++) {
                Move m = new Move();
                m.setOldField(position);
                m.setOldFigure(this);

                switch (I) {
                    case 0:
                        switch (i) {
                            case 1:
                                m.setNewField(position + 0x11);
                                break;
                            case 2:
                                m.setNewField(position + 0x22);
                                break;
                            case 3:
                                m.setNewField(position + 0x33);
                                break;
                            case 4:
                                m.setNewField(position + 0x44);
                                break;
                            case 5:
                                m.setNewField(position + 0x55);
                                break;
                            case 6:
                                m.setNewField(position + 0x66);
                                break;
                            case 7:
                                m.setNewField(position + 0x77);
                                break;
                        }
                        break;
                    case 1:
                        switch (i) {
                            case 1:
                                m.setNewField(position - 0x11);
                                break;
                            case 2:
                                m.setNewField(position - 0x22);
                                break;
                            case 3:
                                m.setNewField(position - 0x33);
                                break;
                            case 4:
                                m.setNewField(position - 0x44);
                                break;
                            case 5:
                                m.setNewField(position - 0x55);
                                break;
                            case 6:
                                m.setNewField(position - 0x66);
                                break;
                            case 7:
                                m.setNewField(position - 0x77);
                                break;
                        }
                        break;
                    case 2:
                        switch (i) {
                            case 1:
                                m.setNewField(position - 0x01 + 0x10);
                                break;
                            case 2:
                                m.setNewField(position - 0x02 + 0x20);
                                break;
                            case 3:
                                m.setNewField(position - 0x03 + 0x30);
                                break;
                            case 4:
                                m.setNewField(position - 0x04 + 0x40);
                                break;
                            case 5:
                                m.setNewField(position - 0x05 + 0x50);
                                break;
                            case 6:
                                m.setNewField(position - 0x06 + 0x60);
                                break;
                            case 7:
                                m.setNewField(position - 0x07 + 0x70);
                                break;
                        }
                        break;
                    case 3:
                        switch (i) {
                            case 1:
                                m.setNewField(position + 0x01 - 0x10);
                                break;
                            case 2:
                                m.setNewField(position + 0x02 - 0x20);
                                break;
                            case 3:
                                m.setNewField(position + 0x03 - 0x30);
                                break;
                            case 4:
                                m.setNewField(position + 0x04 - 0x40);
                                break;
                            case 5:
                                m.setNewField(position + 0x05 - 0x50);
                                break;
                            case 6:
                                m.setNewField(position + 0x06 - 0x60);
                                break;
                            case 7:
                                m.setNewField(position + 0x07 - 0x70);
                                break;
                        }
                        break;
                }
                if (board.check(m)) {
                    ret.add(m);
                } else {
                    break;
                }
            }
        }
        return ret;
    }
}
