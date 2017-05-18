package net.sebbo.fhws.adversarysearch.minichess;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Player p1 = setupPlayer(1);
        Player p2 = setupPlayer(2);

        Game game = new Game(p1, p2);
        game.run();
    }
    private static Player setupPlayer(int number) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("\nWhat type of player should be player " + number + "?");

        while(true) {
            System.out.println("Allowed Values: human, random, accept, offer, negamax");
            System.out.print("> ");
            input = scanner.next();

            if(input.equals("human")) {
                return new HumanPlayer();
            }
            if (input.equals("random")) {
                return new RandomPlayer();
            }
            if (input.equals("negamax")) {
                int depth = 1;
                System.out.println("How many steps shall negamax forsee?");
                System.out.print("> ");
                depth = scanner.nextInt();
                return new NegamaxPlayer(depth);
            }
            if (input.equals("offer")) {
                return new InternetOfferPlayer();
            }
            if (input.equals("accept")) {
                System.out.println("Nice! Please enter the game id you want to join…");
                System.out.print("> ");

                try {
                    return new InternetAcceptPlayer(scanner.next());
                }
                catch(Exception error) {
                    System.out.println("Ups. Seems we have a problem here: " + error.toString());
                }
            }
        }
    }

}
