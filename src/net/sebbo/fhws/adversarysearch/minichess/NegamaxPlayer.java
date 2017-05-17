package net.sebbo.fhws.adversarysearch.minichess;

import java.io.IOException;

/**
 * Created by max on 17.05.17.
 */
public class NegamaxPlayer implements Player {

    private char color;
    private int depth;

    public int getDepth(){
        return this.depth;
    }

    public void setDepth(int depth){
        this.depth = depth;
    }

    @Override
    public char getColor() {
        return this.color;
    }

    @Override
    public void setColor(char color) {
        this.color = color;
    }

    @Override
    public void setup() throws IOException {

    }

    @Override
    public boolean isLegal(Board board, Move move) {
        return false;
    }

    @Override
    public Move getMove(Board b) throws IOException {
        return null;
    }

    @Override
    public void setMove(Move m) throws IOException {

    }
}
