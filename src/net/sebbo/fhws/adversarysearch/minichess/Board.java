package net.sebbo.fhws.adversarysearch.minichess;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.stream.Stream;

/**
 * Created by Sebastian Pekarek on 15.05.17.
 *
 * white = Upper chars
 * black = Lower chars
 */
public class Board {
    private Square[][] squares = new Square[6][5];
    private String parseError = null;
    private int moveNum = 1;
    private char onMove;

    public Board() {
        this.parseString(
            "1 W\n" +
            "kqbnr\n" +
            "ppppp\n" +
            ".....\n" +
            ".....\n" +
            "PPPPP\n" +
            "RNBQK"
        );
    }

    public Board(String importString) {
        this.parseString(importString);
    }
    public Board(Reader importReader) {
        this.parseString(importReader.toString());
    }

    private void parseString(String importString) {
        try {
            this.parseStringHandler(importString);
        }
        catch(Exception error) {
            this.parseError = error.toString();
        }
    }
    private void parseStringHandler(String importString) throws Exception {
        int i = 0, row = 0, column = 0;
        char thisChar;

        this.moveNum = Integer.parseInt(
            importString.substring(0, importString.indexOf(" "))
        );

        if(importString.charAt(importString.indexOf(" ") + 1) == 'W') {
            this.onMove = 'W';
        }else{
            this.onMove = 'B';
        }

        for(i = importString.indexOf("\n") + 1; i <= importString.length(); i += 1) {
            thisChar = importString.charAt(i);

            if(thisChar == '\n' && column == squares[0].length && row < squares.length) {
                row += 1;
                column = 0;
            }
            else if(thisChar == '\n' && column == squares[0].length) {
                throw new Exception("Error while parsing board: Got new line, but board is already complete…");
            }
            else if(thisChar == '\n') {
                throw new Exception(
                    "Error while parsing board: Got new line, but current row (" + row + ") is not complete. "+
                    "Has only column " + column + " of " + squares[0].length + "…"
                );
            }
            else if(thisChar == '.' || thisChar == ' ') {
                this.squares[row][column] = new Square(row, column, '.');
                column += 1;
            }
            else {
                this.squares[row][column] = new Square(row, column, thisChar);
                column += 1;
            }
        }
    }

    public void move(Move m){
        Square from = m.from;
        Square to = m.to;
        squares[to.row][to.col] = squares[from.row][from.col];
        squares[from.row][from.col] = '.';
    }

    public String toString() {
        StringBuilder output = new StringBuilder();

        output.append(moveNum);
        output.append(' ');
        output.append(onMove);

        for (Square[] columns : this.squares) {
            output.append("\n");

            for (Square column : columns) {
                output.append(column.getOccupiedBy());
            }
        }

        return output.toString();
    }

    public void print(Writer exportWriter) throws java.io.IOException {
        exportWriter.write(this.toString());
    }
/**

    public Square getSquareByPosition(String positionStr) {
        char[] position = positionStr.toCharArray();
        return squares[position[1] - 48][position[0] - 97];
    }
    public Square getSquareByPosition(char row, char column) {
        return squares[row - 48][column - 97];
    }

    public static void main(String[] args){

        String toCompare = "1 W\n" +
                "kqbnr\n" +
                "ppppp\n" +
                ".....\n" +
                ".....\n" +
                "PPPPP\n" +
                "RNBQK";

        System.out.println("# Board Unit Tests");

        Board standardBoard = new Board();
        System.out.println("\n## Starting test for standard constructor!");
        if(standardBoard != null){
            System.out.println("not null test successful!");
        }else{
            System.out.println("not null test failed!");
        }

        if(standardBoard.toString().equals(toCompare)){
            System.out.println("toString test successful!");
        }else{
            System.out.println("toString test failed!\n" + standardBoard.toString());
            System.out.println(standardBoard.parseError);
        }

        System.out.println("\n## Start test for constructor with String as parameter!");
        toCompare = "37 W\n" +
                ".....\n" +
                "...k.\n" +
                ".P...\n" +
                ".K...\n" +
                ".....\n" +
                ".....";
        Board stringBoard = new Board(toCompare);
        if(stringBoard != null){
            System.out.println("not null test successful!");
        }else{
            System.out.println("not null test failed!");
        }

        if(stringBoard.toString().equals(toCompare)){
            System.out.println("toString test successful!");
        }else{
            System.out.println("toString test failed!");
            System.out.println(stringBoard.parseError);
        }

        System.out.println("## Start test for constructor with Reader as parameter!");
        Reader r = new StringReader(toCompare);
        Board readerBoard = new Board(r);

        if(readerBoard != null){
            System.out.println("not null test successful!");
        }else{
            System.out.println("not null test failed!");
        }

        if(readerBoard.toString().equals(toCompare)){
            System.out.println("toString test successful!");
        }else{
            System.out.println("toString test failed!");
            System.out.println(r.toString());
        }
    }
*/
}
