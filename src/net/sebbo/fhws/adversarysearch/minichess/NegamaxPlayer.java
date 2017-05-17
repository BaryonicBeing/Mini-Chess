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


        Board b_copy;
        int value;
        int bestScore = 0;
        ArrayList<Move> bestMove = new ArrayList<>();
        LinkedList<Move> opportunities = b.listNextMoves();

        if(opportunities.size() == 0) {
            return null;
        }

        for(Move m : opportunities){
            b_copy = b.clone();
            b_copy.move(m);
            value = b_copy.getHeuristicScore() * (b.getCurrentMoveColor() == 'W' ? 1 : -1);

            if(value == bestScore) {
                bestMove.add(m);
            }
            else if(value > bestScore) {
                bestMove = new ArrayList<>();
                bestScore = value;
                bestMove.add(m);
            }
        }

        int move_num = (int) Math.round(Math.random() * (bestMove.size() - 1));
        return bestMove.get(move_num);
    }

    @Override
    public void setMove(Move m) throws IOException {

    }
}
