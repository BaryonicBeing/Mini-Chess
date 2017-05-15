package net.sebbo.fhws.adversarysearch.minichess;

import java.util.LinkedList;

/**
 * Created by sebbo on 15.05.17.
 */
public class MoveGenerator {
    public LinkedList<Move> moveList(Square piece) {
        LinkedList<Move> results = new LinkedList<Move>();
        char type = piece.getFigureType(),
             color = piece.getFigureColor();
        int direction = color == 'B' ? -1 : 1;

        // queen, king
        if(type == 'q' || type == 'k') {
            results.addAll(this.symmScan(piece.row, piece.col, 0, 1, type == 'k', '1'));
            results.addAll(this.symmScan(piece.row, piece.col, 1, 1, type == 'k', '1'));
            return results;
        }

        // rook, bishop
        if(type == 'r' || type == 'b') {
            results.addAll(this.symmScan(piece.row, piece.col, 0, 1, type == 'b', type == 'r' ? '1' : '0'));

            if(type == 'b') {
                results.addAll(this.symmScan(piece.row, piece.col, 1, 1, false, '1'));
            }

            return results;
        }

        // knight
        if(type == 'n') {
            results.addAll(this.symmScan(piece.row, piece.col, 1, 2, true, '1'));
            results.addAll(this.symmScan(piece.row, piece.col, -1, 2, true, '1'));

            return results;
        }

        // knight
        if(type == 'p') {
            results.addAll(this.symmScan(piece.row, piece.col, -1, direction, true, 'o'));
            results.addAll(this.symmScan(piece.row, piece.col, 1, direction, true, 'o'));
            results.addAll(this.symmScan(piece.row, piece.col, 0, direction, true, '0'));

            return results;
        }

        return results;
    }

    public LinkedList<Move> moveScan(Board board) {
        LinkedList<Move> results = new LinkedList<Move>();
        return results;
    }

    public LinkedList<Move> symmScan(int x, int y, int dx, int dy, boolean stopShort, char capture) {
        return new LinkedList<Move>();
    }
}
