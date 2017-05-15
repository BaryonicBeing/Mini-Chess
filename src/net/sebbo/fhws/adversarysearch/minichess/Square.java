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


    public static void main(String[] args) throws Exception {
        Square s = new Square(0, 0);
        String r = s.toString();
        if(!r.equals("a0")) {
            throw new Exception("Ups: Square(0, 0).toString() returns not `a0` but `" + r + "`");
        }

        s = new Square(5, 5);
        r = s.toString();
        if(!r.equals("f5")) {
            throw new Exception("Ups: Square(5, 5).toString() returns not `f5` but `" + r + "`");
        }
    }
}
