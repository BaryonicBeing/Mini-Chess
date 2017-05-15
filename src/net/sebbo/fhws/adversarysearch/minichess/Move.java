package net.sebbo.fhws.adversarysearch.minichess;

/**
 * Created by max on 15.05.17.
 */
public class Move {
    int[] from = new int[2];
    int[] to = new int[2];

    public Move(String fromto){
        char[] positions = fromto.toCharArray();
        this.from[0] = positions[0] - 97;
        this.from[1] = positions[1] - 48;
        this.to[0] = positions[2] - 97;
        this.to[1] = positions[3] - 48;
    }

    public String toString(){
        return "Moved from " + from[0] + "" + from[1] + " to " + to[0] + "" + to[1] + ".";
    }
}
