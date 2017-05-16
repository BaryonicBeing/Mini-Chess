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
        //System.out.println("Move from = " + move.toString());
        LinkedList<Move> move_list = moveGen.moveList(move.from);
        //System.out.println(move_list.size());
        for(Move m : move_list){
            System.out.println("GOT " + move.toString() + "\nNEED " + m.toString());
            if(m.to == move.to){
                return true;
            }
        }
        return false;
    }

    @Override
    public Move getMove(Board b){
        Scanner scan = new Scanner(System.in);
        while (true) {
            String input = scan.next();
            Move toReturn = new Move(b, input);
            if (isLegal(b, toReturn)) {
                return toReturn;
            }
        }
    }

    @Override
    public void setMove(Move m) {
        // do nothing
    }
}
