package net.sebbo.fhws.adversarysearch.minichess;

import java.io.IOException;

/**
 * Created by max on 16.05.17.
 */
public class InternetOfferPlayer implements Player {
    IMCSClient client;
    char color = '?';
    String id;

    @Override
    public char getColor() {
        return this.color;
    }

    @Override
    public void setColor(char color) {
        // do noting
    }

    @Override
    public void setup() throws IOException {
        this.client = new IMCSClient("imcs.svcs.cs.pdx.edu", "3589", "ILLEGAL_CHARACTER_AWESOME_NAME", "chessmaster");
        this.color = this.client.offer('?') == 'B' ? 'W' : 'B';
    }

    @Override
    public boolean isLegal(Board b, Move move) {
        return true;
    }

    @Override
    public Move getMove(Board b) throws IOException {
        String moveStr = this.client.getMove();
        return new Move(b, moveStr);
    }

    @Override
    public void setMove(Move m) throws IOException {
        this.client.sendMove(m.toString());
    }
}
