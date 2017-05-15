package net.sebbo.fhws.adversarysearch.minichess;

/**
 * Created by max on 15.05.17.
 */
public class Move {
    Square from;
    Square to;

    public Move(Board board, String fromto){
        char[] positions = fromto.toCharArray();

        this.from = board.getSquareByPosition(positions[0], positions[1]);
        this.to = board.getSquareByPosition(positions[2], positions[3]);
    }

    public String toString(){
        return "Move " + this.from.getFigureName() + " from " + from.toString() + " to " + to.toString() + ".";
    }
}
