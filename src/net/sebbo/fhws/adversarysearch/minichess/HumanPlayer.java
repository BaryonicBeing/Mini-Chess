package net.sebbo.fhws.adversarysearch.minichess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by max on 16.05.17.
 */
public class HumanPlayer implements Player {

    @Override
    public boolean isLegal(Board b, Move move) {
        MoveGenerator moveGen = new MoveGenerator(b);
        LinkedList<Move> move_list = moveGen.moveList(move.from);
        for(Move m : move_list){
            System.out.println(m.toString());
            if(m.to == move.to){
                return true;
            }
        }
        return false;
    }

    @Override
    public Move makeMove(Board b){
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        Move toReturn = new Move(b, input);
        if(isLegal(b, toReturn)){
            return toReturn;
        }
        /** Muss ich nochmal drüber gucken */
        return null;
    }
}
