package net.sebbo.fhws.adversarysearch.minichess;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static Move cpu_move(LinkedList<Move> moves){
        int move_pos = (int) (Math.random() * moves.size());
        Move secure_move = moves.get(0);
        System.out.println("move_pos = " + move_pos);
        for(Move m : moves){
            if(move_pos-- == 0)
                return m;
        }
        return secure_move;
    }
/*
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        MoveGenerator mg = new MoveGenerator(board);
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Mini Chess!");
        System.out.println(board.toString());
        System.out.print("Please enter a move:\n>");
        String input = scan.next();
        board.move(new Move(board, input));

        while(!input.equals("0000")){
            //mg = new MoveGenerator(board);
            board.move(cpu_move(mg.moveList()));
            System.out.println(board.toString());
            System.out.print(">");
            input = scan.next();

            board.move(new Move(board, input));
        }
    }
    */

    public static void main(String[] args) throws Exception{
        RandomPlayer hans = new RandomPlayer();
        HumanPlayer ich = new HumanPlayer();

        Board board = new Board();
        //MoveGenerator mg = new MoveGenerator(board);
        System.out.println(board.toString());
        Move m1 = ich.makeMove(board);
        board.move(m1);

        System.out.println("_____________________");
        System.out.println(board.toString());
        System.out.println("_____________________");
        Move m2 = hans.makeMove(board);
        board.move(m2);

        System.out.println("_____________________");
        System.out.println(board.toString());
        System.out.println("_____________________");
    }
}
