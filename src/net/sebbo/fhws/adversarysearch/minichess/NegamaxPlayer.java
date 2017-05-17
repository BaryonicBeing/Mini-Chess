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

    /*
    To negamax on a board b with depth limit d:
  if d = 0 return heuristic score of b, no move
  best_move <- none
  best_value <- loss
  for each move m on b
      make m on a copy of b to get a new board b'
      if b' is a finished board of value v' (win, lose, draw)
          v <- v'
      else
          v', _ <- negamax-score b' (d - 1)
          v <- - v'
      if v >= best_value
          best_move <- m
          best_value <- v
  return best_move, best_value

   To negamax on a board b:
   if the game is over, return the game value
   for each move m on b
       make m on a copy of b to get a new board b'
       v <- - negamax b'
   return some random greatest v found
     */

    @Override
    public Move getMove(Board b) throws IOException {

        //Board b_copy;
        //int value;
        int bestScore = 0;
        //LinkedList<Move> opportunities = b.listNextMoves();

        //if(opportunities.size() == 0) {
        //    return null;
        //}
/*
        for(Move m : opportunities){
            b_copy = b.clone();
            b_copy.move(m);
            value = b_copy.getHeuristicScore() * (b.getCurrentMoveColor() == 'W' ? 1 : -1);

            if(value == bestScore) {
                bestMoves.add(m);
            }
            else if(value > bestScore) {
                bestMoves = new ArrayList<>();
                bestScore = value;
                bestMoves.add(m);
            }
        }
*/
        bestScore = negamax(b, this.depth);
        System.out.println("best score is " + bestScore);
        int move_num = (int) Math.round(Math.random() * (bestMoves.size() - 1));
        return bestMoves.get(move_num);
    }

    private int negamax(Board board, int depth){

        if(depth == 0){ board.getHeuristicScore();}

        LinkedList<Move> opportunities = board.listNextMoves();
        Board b_copy;
        int bestValue = 0;
        int bestScore = 0;
        char state_of_the_game;

        //if (opportunities.size() == 0) return null;

        for(Move m : opportunities){
            b_copy = board.clone();
            state_of_the_game = b_copy.move(m);
            //bestValue = b_copy.getHeuristicScore() * (board.getCurrentMoveColor() == 'W' ? 1 : -1);

            if(state_of_the_game != '?'){
                return b_copy.getHeuristicScore() * (board.getCurrentMoveColor() == this.color ? 1 : -1);
            }else if(depth != 0){
                bestValue = negamax(b_copy, depth-1);
            }

            if(bestValue == bestScore) {
                bestMoves.add(m);
            }
            else if(bestValue > bestScore) {
                bestMoves = new ArrayList<>();
                bestScore = bestValue;
                bestMoves.add(m);
            }
        }

        return bestValue;
    }

    @Override
    public void setMove(Move m) throws IOException {

    }
}
