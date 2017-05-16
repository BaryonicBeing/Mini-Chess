package net.sebbo.fhws.adversarysearch.minichess;

import java.io.IOException;

/**
 * Created by max on 16.05.17.
 */
public class HumanPlayer implements Player {

    private Board board;
    private MoveGenerator moveGen;

    public HumanPlayer(Board b, MoveGenerator mg){
        this.board = b;
        this.moveGen = mg;
    }

    @Override
    public boolean isLegal(Move move) {
        for(Move m : moveGen.moveList(move.from)){
            if(m.to == move.to){
                return true;
            }
        }
        return false;
    }

    @Override
    public Move makeMove(String move_str){
        Move toReturn = new Move(board, move_str);
        if(isLegal(toReturn)){
            return toReturn;
        }
        /** Muss ich nochmal drüber gucken */
        return null;
    }
}
