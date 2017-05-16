package net.sebbo.fhws.adversarysearch.minichess;

import java.io.IOException;

/**
 * Created by max on 16.05.17.
 */
public interface Player {
    public char getColor();
    public void setColor(char color);

    boolean isLegal(Board board, Move move);

    public Move getMove(Board b) throws IOException;
    public void setMove(Move m) throws IOException;
}
