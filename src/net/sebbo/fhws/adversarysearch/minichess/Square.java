package net.sebbo.fhws.adversarysearch.minichess;

/**
 * Created by max on 15.05.17.
 */
public class Square {

    int col, row;

    public Square(int c, int r){
        this.col = c;
        this.row = r;
    }

    public Square(String pos){
        char[] position = pos.toCharArray();
        this.col = position[0] - 97;
        this.row = position[1] - 48;
    }

    public String toString(){
        char toReturn = this.col == 0 ? 'a' :(char) ('a' + this.col);
        return toReturn + "" + this.row;
    }
}
