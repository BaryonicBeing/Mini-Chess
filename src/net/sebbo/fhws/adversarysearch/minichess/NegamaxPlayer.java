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
    private long iterativeClockEnd;
    private int iterativeClockCounter = 0;
    private ArrayList<Move> bestMoves = new ArrayList<>();
    private Move bestMove;

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

    private void debug(String path, int depth, String text) {
        if(!this.debug) {
            return;
        }

        System.out.print("> ");
        for(int i = 0; i < this.depth - depth; i += 1) {
            System.out.print("    ");
        }
        System.out.println("NegamaxPlayer[" + path + "] " + text);
    }

    @Override
    public Move getMove(Board b) throws IOException {
        Move bestMove = null;
        Move tmpMove;

        int bestScore;
        this.iterativeClockEnd = System.currentTimeMillis() + 5000;

        for(int i = 1; i <= 25; i += 1) {
            System.out.println("> \n> \n> \n> Iteration #" + i);

            this.bestMoves.clear();
            this.depth = i;
            bestScore = negamax(b, i, "");
            if(i > 1 && this.shouldStopIteration()) {
                System.out.println("> Timeout, stop iteration");
                break;
            }

            System.out.println("> > Best score is " + bestScore);
            System.out.println("> > " + this.bestMoves.size() + " good moves found:");
            for(Move m: this.bestMoves) {
                System.out.println("> >   - " + m);
            }

            if(bestMoves.size() == 0) {
                return null;
            }

            bestScore = (int) Math.round(Math.random() * (bestMoves.size() - 1));
            tmpMove = bestMoves.get(bestScore);

            bestMove = tmpMove;
        }

        return bestMove;
    }
    private boolean shouldStopIteration() {
        if(this.iterativeClockCounter++ < 1000) {
            return false;
        }

        if(this.iterativeClockEnd < System.currentTimeMillis()) {
            return true;
        }

        this.iterativeClockCounter = 0;
        return false;
    }

    private int negamax(Board board, int depth, String path) {
        int score;

        if(this.shouldStopIteration()) {
            return 0;
        }

        if(depth == 0) {
            score = board.getHeuristicScore(board.getCurrentMoveColor(), this.debug);
            this.debug(path, depth, "score = " + score);
            return score;
        }

        LinkedList<Move> opportunities = board.listNextMoves();
        if(opportunities.size() == 0) {
            score = board.getHeuristicScore(board.getCurrentMoveColor(), this.debug);
            System.out.println(board.toReadableString());
            this.debug(path, depth, "0 opportunities -> score = " + score);
            return score;
        }

        Board tmpBoard;
        char state_of_the_game;
        int bestValue = -20000;
        int tmpValue;

        this.debug(path, depth, "try " + opportunities.size() + " moves…");
        for(Move m : opportunities) {
            this.debug(path + "/" + m, depth, "Start");
            tmpBoard = board.clone();
            state_of_the_game = tmpBoard.move(m);

            if(this.shouldStopIteration()) {
                return 0;
            }

            if(state_of_the_game == this.color) {
                tmpValue = 20000;
                this.debug(path + "/" + m, depth, "oh, i won, set tmpValue = " + tmpValue);
            }
            else if(state_of_the_game == (this.color == 'W' ? 'B' : 'W')) {
                tmpValue = -20000;
                this.debug(path + "/" + m, depth, "oh, i lost, set tmpValue = " + tmpValue);
            }
            else {
                tmpValue = -1 * negamax(tmpBoard, depth - 1, path + "/" + m);
                tmpValue += (tmpValue > 0 ? -1 : 1) * (this.depth - depth);
                this.debug(path + "/" + m, depth, "game resumes, result was " + state_of_the_game + ", set tmpValue = " + tmpValue);
            }

            if(depth == this.depth) {
                this.debug(path + "/" + m, depth, "This is a root Node");

                if(tmpValue > bestValue) {
                    this.debug(path + "/" + m, depth, "Clear results, because tmpValue (" + tmpValue + ") > bestValue (" + bestValue + ")");
                    this.bestMoves.clear();
                }
                if(tmpValue >= bestValue) {
                    this.debug(path + "/" + m, depth, "Add result, because tmpValue (" + tmpValue + ") >= bestValue (" + bestValue + ")");
                    this.bestMoves.add(m);
                }
            }
            
            if(tmpValue > bestValue) {
                bestValue = tmpValue;
                this.debug(path + "/" + m, depth, "Set bestValue to " + tmpValue);

                if(depth == this.depth) {
                    this.debug(path + "/" + m, depth, "Save current move " + m + " as best move");
                    this.bestMove = m;
                }
            }
        }

        this.debug(path, depth, "bestValue = " + bestValue);
        return bestValue;
    }

    @Override
    public void setMove(Move m) throws IOException {

    }
}
