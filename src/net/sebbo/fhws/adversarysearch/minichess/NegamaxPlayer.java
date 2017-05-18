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
    private boolean debug = false;
    private ArrayList<Move> bestMoves = new ArrayList<>();
    private Move bestMove;

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

    private void debug(String path, String text) {
        if(!this.debug) {
            return;
        }

        System.out.println("NegamaxPlayer[" + path + "] " + text);
    }

    @Override
    public Move getMove(Board b) throws IOException {
        int bestScore = negamax(b, this.depth, "");
        System.out.println("> Best score is " + bestScore);
        System.out.println("> " + this.bestMoves.size() + " good moves found.");
        System.out.println("> Best Move: " + this.bestMove);

        if(bestMoves.size() == 0) {
            return null;
        }

        int move_num = (int) Math.round(Math.random() * (bestMoves.size() - 1));
        return bestMoves.get(move_num);
    }

    private int negamax(Board board, int depth, String path) {
        int score;

        if(depth == 0) {
            score = board.getHeuristicScore(board.getCurrentMoveColor(), this.debug);
            this.debug(path, "score = " + score);
            return score;
        }

        LinkedList<Move> opportunities = board.listNextMoves();
        Board tmpBoard;
        char state_of_the_game;
        int bestValue = Integer.MIN_VALUE;
        int tmpValue;

        this.debug(path, "try " + opportunities.size() + " moves…");
        for(Move m : opportunities) {
            this.debug(path + "/" + m, "Start");
            tmpBoard = board.clone();
            state_of_the_game = tmpBoard.move(m);

            if(this.debug) {
                System.out.println("\nNew Board after move " + m + " with state " + state_of_the_game + ":\n" + tmpBoard.toReadableString() + "\n");
            }

            if(state_of_the_game == this.color) {
                tmpValue = Integer.MAX_VALUE;
                this.debug(path + "/" + m, "oh, i won, set tmpValue = " + tmpValue);
            }
            else if(state_of_the_game == (this.color == 'W' ? 'B' : 'W')) {
                tmpValue = Integer.MIN_VALUE;
                this.debug(path + "/" + m, "oh, i lost, set tmpValue = " + tmpValue);
            }
            else {
                tmpValue = /*-1 * */negamax(tmpBoard, depth - 1, path + "/" + m);
                this.debug(path + "/" + m, "game resumes, set tmpValue = " + tmpValue);
            }

            if(depth == this.depth) {
                this.debug(path + "/" + m, "This is a root Node");

                if(tmpValue > bestValue) {
                    this.debug(path + "/" + m, "Clear results, because tmpValue (" + tmpValue + ") > bestValue (" + bestValue + ")");
                    this.bestMoves.clear();
                }
                if(tmpValue >= bestValue) {
                    this.debug(path + "/" + m, "Add result, because tmpValue (" + tmpValue + ") >= bestValue (" + bestValue + ")");
                    this.bestMoves.add(m);
                }
            }
            
            if(tmpValue > bestValue) {
                bestValue = tmpValue;
                this.debug(path + "/" + m, "Set bestValue to " + tmpValue);

                if(depth == this.depth) {
                    this.debug(path + "/" + m, "Save current move " + m + " as best move");
                    this.bestMove = m;
                }
            }
        }

        this.debug(path, "bestValue = " + bestValue);
        return bestValue;
    }

    @Override
    public void setMove(Move m) throws IOException {

    }
}
