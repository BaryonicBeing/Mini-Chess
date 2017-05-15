package net.sebbo.fhws.adversarysearch.minichess;

import java.util.LinkedList;

/**
 * Created by sebbo on 15.05.17.
 */
public class MoveGenerator {
    Board board;

    public MoveGenerator(Board board) {
        this.board = board;
    }

    public LinkedList<Move> moveList() {
        LinkedList<Move> results = new LinkedList<Move>();
        for(Square piece: board.getAllSquares()) {
            System.out.println(piece.toString());
            if(piece.getFigureColor() == board.getCurrentMoveColor()) {
                results.addAll(this.moveList(piece));
            }
        }

        return results;
    }

    public LinkedList<Move> moveList(Square piece) {
        LinkedList<Move> results = new LinkedList<Move>();
        char type = piece.getFigureType(),
             color = piece.getFigureColor();
        int direction = color == 'B' ? 1 : -1;

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

        // pawn
        if(type == 'p') {
            results.addAll(this.moveScan(piece.row, piece.col, -1, direction, true, 'o'));
            results.addAll(this.moveScan(piece.row, piece.col, 1, direction, true, 'o'));
            results.addAll(this.moveScan(piece.row, piece.col, 0, direction, true, '0'));

            return results;
        }

        return results;
    }

    public LinkedList<Move> symmScan(int x, int y, int dx, int dy, boolean stopShort, char capture) {
        LinkedList<Move> results = new LinkedList<Move>();

        for(int i = 1; i <= 4; i += 1) {
            results.addAll(this.moveScan(x, y, dx, dy, stopShort, capture));

            int t = dy;
            dy = dx;
            dx = t;

            dy *= -1;
        }

        return results;
    }

    public LinkedList<Move> moveScan(int x0, int y0, int dx, int dy, boolean stopShort, char capture) {
        LinkedList<Move> results = new LinkedList<Move>();
        Square piece = this.board.getSquareByPosition(x0, y0);

        int x = x0,
            y = y0;

        do {
            x += dx;
            y += dy;

            System.out.println("\nMove from " + x0 + "/" + y0 + "  -- (" + dx + "/" + dy + ") --->  to " + x + "/" + y);

            // out of bounds
            if(x < 0 || y < 0 || x >= this.board.getBoardWidth() || y >= this.board.getBoardHeight()) {
                System.out.println(" -> Out of bounds");
                break;
            }

            // there is a piece at x/y
            if(this.board.getSquareByPosition(x, y).isOccupied()) {

                // figures are in same color
                if(this.board.getSquareByPosition(x, y).getFigureColor() == piece.getFigureColor()) {
                    System.out.println(" -> Occupied by same color");
                    break;
                }

                if(capture == '0') {
                    System.out.println(" -> Not able to capture");
                    break;
                }

                stopShort = true;
            }
            else if(capture == 'o') {
                System.out.println(" -> Not occupied, but only capture allowed");
                break;
            }

            System.out.println(" -> Seems legit");
            results.add(new Move(board, x0, y0, x, y));
        } while (stopShort);

        return results;
    }

    public static void main(String[] args){
        Board myBoard = new Board();

        System.out.println(myBoard.toString() + "\n\n");

        System.out.println("\n Possible Moves for A1:");
        for(Move move: myBoard.listMovesFor(myBoard.getSquareByPosition("a1"))) {
            System.out.println(" - " + move.toString());
        }
    }
}
