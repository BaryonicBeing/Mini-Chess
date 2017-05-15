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
        char c = pos.toCharArray()[0];
        this.row = pos.toCharArray()[1];
        if(c == 'a'){
            this.col = 0;
        }else{
            this.col = c - 'a';
        }
    }

    public String toString(){
        char toReturn = this.col == 0 ? 'a' :(char) ('a' + this.col);
        return toReturn + "" + this.row;
    }
}
