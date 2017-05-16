package net.sebbo.fhws.adversarysearch.minichess;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by max on 16.05.17.
 */
public class InternetAcceptPlayer implements Player {
    IMCSClient client;
    char color;

    public InternetAcceptPlayer(String id) throws Exception {
        this.client = new IMCSClient("imcs.svcs.cs.pdx.edu", "3589", "ILLEGAL_CHARACTER_AWESOME_NAME", "chessmaster");
        this.color = this.client.accept(id, '?');
    }
    public InternetAcceptPlayer(String id, char color) throws Exception {
        this.client = new IMCSClient("imcs.svcs.cs.pdx.edu", "3589", "ILLEGAL_CHARACTER_AWESOME_NAME", "chessmaster");
        this.color = this.client.accept(id, color);
    }

    @Override
    public char getColor() {
        return this.color;
    }

    @Override
    public void setColor(char color) {
        // do noting
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
