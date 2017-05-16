package net.sebbo.fhws.adversarysearch.minichess;

import java.util.LinkedList;

/**
 * Created by max on 16.05.17.
 */
public class RandomPlayer implements Player {
    char color;

    @Override
    public char getColor() {
        return this.color;
    }

    @Override
    public void setColor(char color) {
        this.color = color;
    }

    @Override
    public boolean isLegal(Board board, Move move) {
        MoveGenerator moveGen = new MoveGenerator(board);
        for(Move m : moveGen.moveList(move.from)){
            if(m.to == move.to){
                return true;
            }
        }
        return false;
    }

    @Override
    public Move getMove(Board b) {
        MoveGenerator moveGen = new MoveGenerator(b);
        LinkedList<Move> opportunities = moveGen.moveList();

        System.out.println("> Hmm, found " + opportunities.size() + " opportunities to make a move…");
        if(opportunities.size() == 0) {
            return null;
        }

        int move_num = (int) Math.round(Math.random() * (opportunities.size() - 1));
        System.out.print("> Pick #" + move_num + ": ");

        Move myOpportunity = opportunities.get(move_num);
        System.out.println(myOpportunity);

        return myOpportunity;
    }

    @Override
    public void setMove(Move m) {
        // do nothing
    }
}
