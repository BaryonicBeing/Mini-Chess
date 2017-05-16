package net.sebbo.fhws.adversarysearch.minichess;

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
            if(m.from == move.from && m.to == move.to){
                return true;
            }
        }
        return false;
    }

    @Override
    public Move makeMove() {
        return null;
    }
}
