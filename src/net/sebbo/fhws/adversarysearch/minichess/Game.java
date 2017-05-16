package net.sebbo.fhws.adversarysearch.minichess;

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

        if(this.player_1.getColor() == 'B' && this.player_2.getColor() != 'B') {
            this.player_1 = p2;
            this.player_2 = p1;
        }
        if(this.player_2.getColor() == 'W' && this.player_1.getColor() != 'W') {
            this.player_1 = p2;
            this.player_2 = p1;
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

        do {
            if(this.board.getCurrentMoveColor() == 'W') {
                myPlayer = this.player_1;
            }else{
                myPlayer = this.player_2;
            }

            System.out.println("\n=====================================\n");
            System.out.println(
                "Move #" + this.board.getMoveNr() + " / " +
                "Player " + (this.board.getCurrentMoveColor() == 'W' ? "⬜️" : "⬛️") +
                "\n"
            );
            System.out.println(this.board.toString() + "\n");
            Move myMove = myPlayer.getMove(this.board);

            System.out.println("\nPlayer plays " + myMove + "…");

            moveResponse = this.board.move(myMove);
            if(this.board.getCurrentMoveColor() == 'W') {
                this.player_2.setMove(myMove);
            }else{
                this.player_1.setMove(myMove);
            }

            if(moveResponse == 'B') {
                System.out.println("\n=====================================");
                System.out.println("⬛️🙌🎉");
            }
            else if(moveResponse == 'W') {
                System.out.println("\n=====================================");
                System.out.println("⬜️️🙌🎉");
            }
            else if(moveResponse == '=') {
                System.out.println("\n=====================================");
                System.out.println("👬🙌🎉");
            }
            else {
                System.out.println("Looks like an awesome move… 🙌");
            }
        } while (moveResponse == '?');
    }
}
