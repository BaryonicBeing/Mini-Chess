package net.sebbo.fhws.adversarysearch.minichess;

/**
 * Created by max on 16.05.17.
 */
public class RandomPlayer implements Player {
    @Override
    public boolean isLegal(Move move) {
        return false;
    }

    @Override
    public Move makeMove(Board b) {
        return null;
    }
}
