package net.sebbo.fhws.adversarysearch.minichess;

/**
 * Created by max on 16.05.17.
 */
public interface Player {

    boolean isLegal(Board board, Move move);

    public Move makeMove(Board b);
}
