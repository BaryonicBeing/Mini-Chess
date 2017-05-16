package net.sebbo.fhws.adversarysearch.minichess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by max on 16.05.17.
 */
public class HumanPlayer implements Player {
    char color;

    @Override
    public char getColor() {
        return this.color;
    }

    @Override
    public void setColor(char color) {
        this.color = color;
    }

    @Override
    public boolean isLegal(Board b, Move move) {
        MoveGenerator moveGen = new MoveGenerator(b);
        LinkedList<Move> move_list = moveGen.moveList(move.from);
        for(Move m : move_list){
            if(m.to.toString().equals(move.to.toString())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Move getMove(Board b){
        System.out.println("> Please enter your move (xy-xy):");
        System.out.print("> ");
        Scanner scan = new Scanner(System.in);
        while (true) {
            String input = scan.next();
            Move toReturn = new Move(b, input);
            if (isLegal(b, toReturn)) {
                return toReturn;
            }

            System.out.println("> \n> Move is not legal. Here are some legal ones, pick wisely:");
            for(Move move: b.listNextMoves()) {
                System.out.println(">    - " + move.toString());
            }
            System.out.print("> \n> ");
        }
    }

    @Override
    public void setMove(Move m) {
        // do nothing
    }
}
