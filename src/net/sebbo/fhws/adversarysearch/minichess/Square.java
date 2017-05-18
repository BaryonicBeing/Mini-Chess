package net.sebbo.fhws.adversarysearch.minichess;

import java.io.IOException;

/**
 * Created by max on 15.05.17.
 */
public class Square{

    Board b;
    int col, row;
    char occupiedBy = '.';

    public Square(Board b, int r, int c, char occupiedBy) throws IndexOutOfBoundsException{
        if(r >= b.getBoardHeight() || c >= b.getBoardWidth()) {
            throw new IndexOutOfBoundsException("Square indices out of bounds!");
        }

        this.col = c;
        this.row = r;
        this.occupiedBy = occupiedBy;
        this.b = b;
    }

    public Square(Board b, String pos) throws IndexOutOfBoundsException{
        char[] position = pos.toCharArray();
        if (position[0] >= 'e' || position[1] >= '5') {
            throw new IndexOutOfBoundsException("Square indices out of bounds!");
        }

        this.col = position[0] - 97;
        this.row = position[1] - 48;
    }

    public String toString(){
        return "" + ((char) ('a' + this.col)) + (this.b.getBoardHeight() - this.row);
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
    public int getFigureHeuristicScore() {
        char type = this.getFigureType();

        if(type == 'k') {
            return 50;
        }
        if(type == 'q') {
            return 9;
        }
        if(type == 'r') {
            return 5;
        }
        if(type == 'n' || type == 'b') {
            return 3;
        }
        if(type == '.') {
            return -5;
        }

        return 1;
    }
    public char getFigureColor() {
        return Character.isLowerCase(this.occupiedBy) ? 'B' : 'W';
    }
    public String getFigureReadableEmoji() {
        switch (this.occupiedBy) {
            case 'K':
                return "♚";
            case 'Q':
                return "♛";
            case 'B':
                return "♝";
            case 'N':
                return "♞";
            case 'R':
                return "♜";
            case 'P':
                return "♟";
            case 'k':
                return "♔";
            case 'q':
                return "♕";
            case 'b':
                return "♗";
            case 'n':
                return "♘";
            case 'r':
                return "♖";
            case 'p':
                return "♙";
            default:
                return " ";
        }
    }


    public static void main(String[] args) throws Exception {
        Board b = new Board();
        System.out.println("START TESTING CLASS SQUARE!");
        System.out.println("Start test for standard constructor!");
        Square s = new Square(b , 0, 0, '.');
        String r = s.toString();
        if (!r.equals("a6")) {
            throw new Exception("Ups: Square(0, 0).toString() does not return `a6` but `" + r + "`");
        }

        System.out.println("Test successful!");
        System.out.println("Start test for toString method!");

        s = new Square(b, 3, 4, '.');
        r = s.toString();
        if (!r.equals("e3")) {
            throw new Exception("Ups: Square(3, 4).toString() does not return `e3` but `" + r + "`");
        }

        System.out.println("Test successful!");
        System.out.println("END TESTING CLASS SQUARE!");
    }
}
