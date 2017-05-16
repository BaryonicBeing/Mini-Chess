package net.sebbo.fhws.adversarysearch.minichess;

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
        System.out.println("Square test " + b.getAllSquares()[0].toString());
        MoveGenerator moveGen = new MoveGenerator(b);
        int move_num = (int) (Math.random() * moveGen.moveList().size());

        for(Move m : moveGen.moveList()){
            if(move_num-- == 0)
                return m;
        }
        return null;
    }

    @Override
    public void setMove(Move m) {
        // do nothing
    }
}
