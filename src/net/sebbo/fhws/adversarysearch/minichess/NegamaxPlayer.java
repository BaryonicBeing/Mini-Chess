package net.sebbo.fhws.adversarysearch.minichess;

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
        int bestScore = negamax(b, this.depth, "");
        System.out.println("> Best score is " + bestScore);
        System.out.println("> " + this.bestMoves.size() + " good moves found:");
        for(Move m: this.bestMoves) {
            System.out.println(">    - " + m);
        }

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
