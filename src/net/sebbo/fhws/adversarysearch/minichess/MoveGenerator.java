package net.sebbo.fhws.adversarysearch.minichess;

import java.util.LinkedList;

/**
 * Created by sebbo on 15.05.17.
 */
public class MoveGenerator {
    public LinkedList<Move> moveScan(Board board) {
        LinkedList<Move> results = new LinkedList<Move>();
        return results;
    }

    public LinkedList<Move> symmScan(int x, int y, int dx, int dy, boolean stopShort, boolean capture) {
        return new LinkedList<Move>();
    }

    public LinkedList<Move> moveList(int x, int y) {
        return new LinkedList<Move>();
    }
}
