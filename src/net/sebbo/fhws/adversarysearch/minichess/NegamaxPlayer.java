package net.sebbo.fhws.adversarysearch.minichess;

import sun.awt.image.ImageWatched;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by max on 17.05.17.
 */
public class NegamaxPlayer implements Player {
    private char color;
    private int depth;
    ArrayList<Move> bestMoves = new ArrayList<>();
    Move bestMove;

    public NegamaxPlayer(int depth){this.depth = depth;}

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
        return true;
    }

    @Override
    public Move getMove(Board b) throws IOException {
        int bestScore = negamax(b, this.depth);
        System.out.println("> Best score is " + bestScore);
        System.out.println("> " + this.bestMoves.size() + " moves found.");

        int move_num = (int) Math.round(Math.random() * (bestMoves.size() - 1));
        return bestMoves.get(move_num);

        //return this.bestMove;
    }

    private int negamax(Board board, int depth){
        if(depth == 0) {
            return board.getHeuristicScore();
        }

        LinkedList<Move> opportunities = board.listNextMoves();
        Board tmpBoard;
        char state_of_the_game;
        int bestValue = Integer.MIN_VALUE;
        int tmpValue;

        for(Move m : opportunities){
            tmpBoard = board.clone();
            state_of_the_game = tmpBoard.move(m);

            if(state_of_the_game == 'B' || state_of_the_game == 'W') {
                tmpValue = -tmpBoard.getHeuristicScore();
            } else
                tmpValue = -1 * negamax(tmpBoard, depth - 1);

            if(depth == this.depth) {
                if(tmpValue > bestValue) {
                    this.bestMoves.clear();
                }
                if(tmpValue >= bestValue) {
                    this.bestMoves.add(m);
                }
            }

            if(tmpValue > bestValue) {
                bestValue = tmpValue;

                if(depth == this.depth) {
                    this.bestMove = m;
                }
            }
        }

        return bestValue;
    }

    @Override
    public void setMove(Move m) throws IOException {

    }
}
