package net.sebbo.fhws.adversarysearch.minichess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by max on 18.05.17.
 */
public class NMax_v2 implements Player {

    private char color;
    private int depth;
    private boolean debug = false;
    private ArrayList<Move> bestMoves = new ArrayList<>();
    private Move bestMove;

    public NMax_v2(int depth) {
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
        return true;
    }

    @Override
    public Move getMove(Board b) throws IOException {
        int bestScore = nmax(b, this.depth);
        System.out.println("Found " + this.bestMoves.size() + " moves...");
        int move_to_play = (int) (Math.random() * this.bestMoves.size() - 1);
        return this.bestMoves.get(move_to_play);
    }

    public int nmax(Board b, int depth) {
        Board tmpBoard;
        int tmpValue = 0;
        char state_of_the_game;

        int score_to_return;
        LinkedList<Move> possible_moves = b.listNextMoves();

        if(depth == 0 || possible_moves.size() == 0) {
            score_to_return = b.getHeuristicScore(b.getCurrentMoveColor());
            return score_to_return;
        }

        score_to_return = -20000;

        for(Move m : possible_moves) {
            tmpBoard = b.clone();
            state_of_the_game = tmpBoard.move(m);

            if(state_of_the_game != '?'){
                if(state_of_the_game == this.color){
                    tmpValue = 20000;
                }
                if(state_of_the_game == (this.color == 'W' ? 'B' : 'W')) {
                    tmpValue = -20000;
                }
            }else{
                tmpValue = -1 * nmax(tmpBoard, depth - 1);
                tmpValue += (tmpValue > 0 ? -1 : 1) * (this.depth - depth);
            }

            if(depth == this.depth) {
                if(tmpValue > score_to_return) {
                    bestMoves.clear();
                }
                if(tmpValue >= score_to_return) {
                    bestMoves.add(m);
                    this.bestMove = m;
                }
            }
            if(tmpValue >= score_to_return) {
                score_to_return = tmpValue;

                if(depth == this.depth) {
                    this.bestMove = m;
                }
            }
        }
        return score_to_return;
    }

    @Override
    public void setMove(Move m) throws IOException {

    }
}
