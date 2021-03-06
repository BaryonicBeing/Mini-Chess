package net.sebbo.fhws.adversarysearch.minichess;

/**
 * Created by max on 15.05.17.
 */
public class Move {
    Square from;
    Square to;

    public Move(Board board, String fromTo){
        char[] positions = fromTo.toCharArray();

        this.from = board.getSquareByPosition(Character.forDigit('6'-positions[1],10), positions[0]);
        this.to = board.getSquareByPosition(Character.forDigit('6'-positions[4], 10), positions[3]);
    }
    public Move(Board board, int fromRow, int fromColumn, int toRow, int toColumn){
        //System.out.println(fromRow + " digitiert zu " + (board.getBoardHeight() - fromRow));

        this.from = board.getSquareByPosition((board.getBoardHeight()-fromRow)-1, fromColumn);
        this.to = board.getSquareByPosition((board.getBoardHeight()-toRow)-1, toColumn);
    }
    public Move(Board board, Square from, Square to){
        this.from = from;
        this.to = to;
    }

    public String toString(){
        return from.toString() + "-" + to.toString();
    }
}
