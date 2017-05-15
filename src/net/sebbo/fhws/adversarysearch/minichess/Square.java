package net.sebbo.fhws.adversarysearch.minichess;

/**
 * Created by max on 15.05.17.
 */
public class Square {

    int col, row;
    char occupiedBy = '.';

    public Square(int c, int r, char occupiedBy){
        this.col = c;
        this.row = r;
        this.occupiedBy = occupiedBy;
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


    public char getOccupiedBy() {
        return this.occupiedBy;
    }
    public boolean isOccupied() {
        return this.occupiedBy != '.';
    }

    public char getFigureType() {
        return Character.toLowerCase(this.occupiedBy);
    }
    public String getFigureName() {
        char type = this.getFigureType();
        StringBuilder output = new StringBuilder();

        switch (type) {
            case 'q':
                output.append("Queen");
                break;
            case 'k':
                output.append("King");
                break;
            case 'r':
                output.append("Rook");
                break;
            case 'b':
                output.append("Bishop");
                break;
            case 'n':
                output.append("Knight");
                break;
            case 'p':
                output.append("Pawn");
                break;
        }

        return output.toString();
    }
    public char getFigureColor() {
        return Character.isLowerCase(this.occupiedBy) ? 'B' : 'W';
    }


    public static void main(String[] args) throws Exception {
        System.out.println("START TESTING CLASS SQUARE!");
        System.out.println("Start test for standard constructor!");
        Square s = new Square(0, 0, '.');
        String r = s.toString();
        if (!r.equals("a0")) {
            throw new Exception("Ups: Square(0, 0).toString() does not return `a0` but `" + r + "`");
        }

        System.out.println("Test successful!");
        System.out.println("Start test for toString method!");

        s = new Square(5, 5, '.');
        r = s.toString();
        if (!r.equals("f5")) {
            throw new Exception("Ups: Square(5, 5).toString() does not return `f5` but `" + r + "`");
        }

        System.out.println("Test successful!");
        System.out.println("Start test for constructor with string as parameter");

        s = new Square("c4");
        r = s.toString();
        if (!r.equals("c4")) {
            throw new Exception("Ups: Square(2, 4).toString() does not return 'c4' but '" + r + "'");
        }

        System.out.println("Test successful!");
        System.out.println("END TESTING CLASS SQUARE!");
    }
}
