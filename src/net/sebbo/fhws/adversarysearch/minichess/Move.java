package net.sebbo.fhws.adversarysearch.minichess;

/**
 * Created by max on 15.05.17.
 */
public class Move {
    Square from;
    Square to;

    public Move(String fromto){
        char[] positions = fromto.toCharArray();
        this.from = new Square(positions[0]+ "" + positions[1]);
        this.to = new Square(positions[2] + "" + positions[3]);
    }

    public String toString(){
        return "Moved from " + from.toString() + " to " + to.toString() + ".";
    }
}
