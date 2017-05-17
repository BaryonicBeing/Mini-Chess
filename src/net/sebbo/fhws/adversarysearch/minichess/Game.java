package net.sebbo.fhws.adversarysearch.minichess;

//import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

/**
 * Created by max on 16.05.17.
 */
public class Game {

    Board board;
    Player player_1; // White
    Player player_2; // Black

    public Game(Player p1, Player p2) throws Exception {
        this.player_1 = p1;
        this.player_2 = p2;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void run() throws Exception {
        if(this.board == null) {
            this.board = new Board();
        }

        Player myPlayer;
        char moveResponse;
        int heuristicScore = 0;

        this.player_1.setup();
        this.player_2.setup();

        System.out.println("\nPreferred color of Player 1: " + this.player_1.getColor());
        System.out.println("Preferred color of Player 2: " + this.player_2.getColor());

        if(this.player_1.getColor() == 'B' && this.player_2.getColor() != 'B') {
            myPlayer = this.player_2;
            this.player_2 = this.player_1;
            this.player_1 = myPlayer;

        }
        if(this.player_2.getColor() == 'W' && this.player_1.getColor() != 'W') {
            myPlayer = this.player_2;
            this.player_2 = this.player_1;
            this.player_1 = myPlayer;
        }

        if(this.player_1.getColor() == 'B') {
            throw new Exception("Error: Player 1 wants to play black, but this is not possible currently…");
        }
        if(this.player_2.getColor() == 'W') {
            throw new Exception("Error: Player 2 wants to play white, but this is not possible currently…");
        }

        if(this.player_1.getColor() != 'W') {
            this.player_1.setColor('W');
        }
        if(this.player_2.getColor() != 'B') {
            this.player_2.setColor('B');
        }

        do {
            if(this.board.getCurrentMoveColor() == 'W') {
                myPlayer = this.player_1;
            }else{
                myPlayer = this.player_2;
            }

            System.out.println("\n=====================================\n");
            System.out.println(
                "Player " + (this.board.getCurrentMoveColor() == 'W' ? "⬜️" : "⬛️") + " || " +
                "Move #" + this.board.getMoveNr() + " || " +
                "Score: " + heuristicScore +
                "\n"
            );
            System.out.println(this.board.toString() + "\n");
            Move myMove = myPlayer.getMove(this.board);

            System.out.println("\nPlayer plays " + myMove + "…");
            if(this.board.getCurrentMoveColor() == 'W') {
                this.player_2.setMove(myMove);
            }else{
                this.player_1.setMove(myMove);
            }

            moveResponse = this.board.move(myMove);
            heuristicScore = this.board.getHeuristicScore();

            if(moveResponse == 'B') {
                System.out.println("\n=====================================");
                System.out.println("⬛️🙌🎉");
            }
            else if(moveResponse == 'W') {
                System.out.println("\n=====================================");
                System.out.println("⬜️️🙌🎉");
            }
            else if(moveResponse == '=' && heuristicScore == 0) {
                System.out.println("\n=====================================");
                System.out.println("👬🙌🎉");
            }
            else if(moveResponse == '=' && heuristicScore > 0) {
                System.out.println("\n=====================================");
                System.out.println("👬🙌🎉 (with advantage for ⬜)");
            }
            else if(moveResponse == '=' && heuristicScore < 0) {
                System.out.println("\n=====================================");
                System.out.println("👬🙌🎉 (with advantage for ⬛)");
            }
            else {
                System.out.println("Looks like an awesome move… 🙌");
            }
        } while (moveResponse == '?');
    }
}
