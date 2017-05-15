package net.sebbo.fhws.adversarysearch.minichess;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Board board = new Board();
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Mini Chess!");
        System.out.println(board.toString());
        System.out.print("Please enter a move:\n>");
        String input = scan.next();
        board.move(new Move(input));
        while(!input.equals("0000")){
            System.out.println(board.toString());
            System.out.print(">");
            input = scan.next();
            board.move(new Move(input));
        }
    }
}
