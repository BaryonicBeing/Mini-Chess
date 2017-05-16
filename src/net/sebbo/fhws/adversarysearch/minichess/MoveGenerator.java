package net.sebbo.fhws.adversarysearch.minichess;

import java.util.LinkedList;

/**
 * Created by sebbo on 15.05.17.
 */
public class MoveGenerator {
    Board board;
    boolean debug;

    public MoveGenerator(Board board) {
        this.board = board;
    }
    public MoveGenerator(Board board, boolean debug) {
        this.board = board;
        this.debug = debug;
    }

    private void debug(String text) {
        if(!this.debug) {
            return;
        }

        System.out.println("MoveGenerator: " + text);
    }

    public LinkedList<Move> moveList() {
        this.debug("moveLists()");
        LinkedList<Move> results = new LinkedList<Move>();
        for(Square piece: board.getAllSquares()) {
            if(piece.getFigureColor() == this.board.getCurrentMoveColor()) {
                results.addAll(this.moveList(piece));
            }
        }

        return results;
    }

    public LinkedList<Move> moveList(Square piece) {
        this.debug("moveLists(" + piece.toString() + ")");

        LinkedList<Move> results = new LinkedList<Move>();
        char type = piece.getFigureType(),
             color = piece.getFigureColor();
        int direction = color == 'B' ? 1 : -1;

        if(piece.getFigureColor() != this.board.getCurrentMoveColor()) {
            this.debug("moveLists(" + piece.toString() + "): Color does not match: Piece is " + piece.getFigureColor() + " but it's " + this.board.getCurrentMoveColor() + "'s turn.");
            return results;
        }

        // queen, king
        if(type == 'q' || type == 'k') {
            results.addAll(this.symmScan(piece.col, piece.row, 0, 1, type == 'k', '1'));
            results.addAll(this.symmScan(piece.col, piece.row, 1, 1, type == 'k', '1'));
            return results;
        }

        // rook, bishop
        if(type == 'r' || type == 'b') {
            results.addAll(this.symmScan(piece.col, piece.row, 0, 1, type == 'b', type == 'r' ? '1' : '0'));

            if(type == 'b') {
                results.addAll(this.symmScan(piece.col, piece.row, 1, 1, false, '1'));
            }

            return results;
        }

        // knight
        if(type == 'n') {
            results.addAll(this.symmScan(piece.col, piece.row, 1, 2, true, '1'));
            results.addAll(this.symmScan(piece.col, piece.row, -1, 2, true, '1'));

            return results;
        }

        // pawn
        if(type == 'p') {
            results.addAll(this.moveScan(piece.col, piece.row, -1, direction, true, 'o'));
            results.addAll(this.moveScan(piece.col, piece.row, 1, direction, true, 'o'));
            results.addAll(this.moveScan(piece.col, piece.row, 0, direction, true, '0'));

            return results;
        }

        this.debug("moveList(" + piece.toString() + "): Done, " + results.size() + " possible positions found.");
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
        Square piece = this.board.getSquareByPosition(y0, x0);
        Square toPiece;

        int x = x0,
            y = y0;

        do {
            x += dx;
            y += dy;

            this.debug("moveScan(): Move " + piece.getFigureName() + " at " + piece.toString() + " with " + dx + "/" + dy);

            // out of bounds
            if(x < 0 || y < 0 || x >= this.board.getBoardWidth() || y >= this.board.getBoardHeight()) {
                this.debug("            ❌ Not possible: New position out of bounds.");
                break;
            }

            toPiece = this.board.getSquareByPosition(y, x);
            this.debug("            New position: " + toPiece.toString());
            if (toPiece.isOccupied()) {

                // figures are in same color
                if (toPiece.getFigureColor() == piece.getFigureColor()) {
                    this.debug("            ❌ Not possible: New position occupied by " + toPiece.getFigureName() + " with same color.");
                    break;
                }

                if (capture == '0') {
                    this.debug("            ❌ Not possible: New position occupied by " + toPiece.getFigureName() + " and " + piece.getFigureName() + " isn't able to capture this figure.");
                    break;
                }

                stopShort = true;
            } else if (capture == 'o') {
                this.debug("            ❌ Not possible: Only allowed for capturing, but destination is not occupied.");
                break;
            }


            this.debug("            ✅ Seems legit.");
            results.add(new Move(board, y0, x0, y, x));
        } while (!stopShort);

        this.debug("moveScan(): Done, " + results.size() + " possible positions found for " + piece.getFigureName() + " at (" + x0 + "/" + y0 + ").");
        return results;
    }

    public static void main(String[] args) throws Exception {
        Board myBoard = new Board();

        System.out.println(myBoard.toString() + "\n\n");

        for(Move move: myBoard.listNextMoves(true)) {
            System.out.println(" - " + move.toString());
        }
    }
}
