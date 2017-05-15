package net.sebbo.fhws.adversarysearch.minichess;

/**
 * Created by max on 15.05.17.
 */
public class Square {

    int col, row;
    String piece;

    public Square(int c, int r, String p){
        this.col = c;
        this.row = r;
        this.piece = p;
    }

    public Square(String pos, String p){
        char[] position = pos.toCharArray();
        this.col = position[0] - 97;
        this.row = position[1] - 48;
        this.piece = p;
    }

    public String toString(){
        char toReturn = this.col == 0 ? 'a' :(char) ('a' + this.col);
        return toReturn + "" + this.row + " occupied by a " + this.piece;
    }
}
