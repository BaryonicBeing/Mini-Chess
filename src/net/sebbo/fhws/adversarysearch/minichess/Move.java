package net.sebbo.fhws.adversarysearch.minichess;

/**
 * Created by max on 15.05.17.
 */
public class Move {
    Square from;
    Square to;

    public Move(Board board, String fromto){
        char[] positions = fromto.toCharArray();

        this.from = board.getSquareByPosition((char)(positions[1]-1), positions[0]);
        this.to = board.getSquareByPosition((char)(positions[4]-1), positions[3]);
    }
    public Move(Board board, int fromRow, int fromColumn, int toRow, int toColumn){
        this.from = board.getSquareByPosition(fromRow, fromColumn);
        this.to = board.getSquareByPosition(toRow, toColumn);
    }

    public String toString(){
        return "Move " + this.from.getFigureName() + " from " + from.toString() + " to " + to.toString() + ".";
    }
}
