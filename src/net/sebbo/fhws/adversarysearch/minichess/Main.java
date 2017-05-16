package net.sebbo.fhws.adversarysearch.minichess;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
/*
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
    */

    public static void main(String[] args) throws Exception {
        Player p1 = null;
        Player p2 = null;
        Scanner scan = new Scanner(System.in);
        String input;
        String imcs_game_id;

            System.out.println("Is Player 1 human, npc or imcs?");
            input = scan.next();

            if (input.equals("human")) {
                p1 = new HumanPlayer();
            } else if (input.equals("npc")) {
                p1 = new RandomPlayer();
            } else if (input.equals("imcs")) {
                System.out.println("Please type in the id.");
                imcs_game_id = scan.next();
                p1 = new InternetAcceptPlayer(imcs_game_id);
            }

            System.out.println("Is Player 2 human, npc or imcs?");
            input = scan.next();

            if (input.equals("human")) {
                p2 = new HumanPlayer();
            } else if (input.equals("npc")) {
                p2 = new RandomPlayer();
            } else if (input.equals("imcs")){
                System.out.println("Please type in the id.");
                imcs_game_id = scan.next();
                p2 = new InternetAcceptPlayer(imcs_game_id);
            }

            if (p1 == null || p2 == null) return;


            Game game = new Game(p1, p2);
  //      System.out.println("p1 color is " + p1.getColor() + "\np2 color is " + p2.getColor());

        game.run();
        }

}
