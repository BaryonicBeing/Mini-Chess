package net.sebbo.fhws.adversarysearch.minichess;

/**
 * Created by max on 16.05.17.
 */
public class Game {

    Board board;
    Player player_1;
    Player player_2;

    public Game(Player p1, Player p2){
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

        do {
            if(this.board.getCurrentMoveColor() == 'W') {
                myPlayer = this.player_1;
            }else{
                myPlayer = this.player_2;
            }

            System.out.println(this.board.toString());
            System.out.println("\n\n\nPlayer " + (this.board.getCurrentMoveColor() == 'W' ? "⬜️" : "⬛️"));
            Move myMove = myPlayer.makeMove(this.board);
            moveResponse = this.board.move(myMove);

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
                System.out.println("\nAWESOME! 🙌");
            }
        } while (moveResponse == '?');
    }
}
