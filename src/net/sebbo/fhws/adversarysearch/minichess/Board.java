package net.sebbo.fhws.adversarysearch.minichess;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by Sebastian Pekarek on 15.05.17.
 */
public class Board {
    private char[][] squares = new char[5][6];
    private String parseError = null;
    private int moveNum = 1;
    private char onMove;

    public Board() {
        this.parseString(
            "1 W\n" +
            "  kqbnr\n" +
            "  ppppp\n" +
            "  .....\n" +
            "  .....\n" +
            "  PPPPP\n" +
            "  RNBQK"
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

        for(; i <= importString.length(); i += 1) {
            thisChar = importString.charAt(i);

            if(thisChar == '\n' && column == (squares[0].length - 1) && row <= squares.length) {
                row += 1;
                column = 0;
            }
            else if(thisChar == '\n' && column == (squares[0].length - 1)) {
                throw new Exception("Error while parsing board: Got new line, but board is already complete…");
            }
            else if(thisChar == '\n') {
                throw new Exception("Error while parsing board: Got new line, but current column is not complete…");
            }
            else if(thisChar == '.' || thisChar == ' ') {
                this.squares[row][column] = thisChar;
                column += 1;
            }
        }
    }

    public String toString() {
        StringBuilder output = new StringBuilder();

        for (char[] columns : this.squares) {
            for (char column : columns) {
                output.append(column);
            }
        }

        return output.toString();
    }

    public void print(Writer exportWriter) throws java.io.IOException {
        exportWriter.write(this.toString());
    }

    public static void main(String[] args){

        String toCompare = "1 W\n" +
                "  kqbnr\n" +
                "  ppppp\n" +
                "  .....\n" +
                "  .....\n" +
                "  PPPPP\n" +
                "  RNBQK";

        System.out.println("START TESTING!");

        Board standardBoard = new Board();
        System.out.println("Starting test for standard constructor!");
        if(standardBoard != null){
            System.out.println("not null test successful!");
        }else{
            System.out.println("not null test failed!");
        }

        if(standardBoard.toString().equals(toCompare)){
            System.out.println("toString test successful!");
        }else{
            System.out.println("toString test failed!");
            System.out.println(standardBoard.parseError);
        }

        System.out.println("Start test for constructor with String as parameter!");
        Board stringBoard = new Board("  kqbnr\n" +
                "  ppppp\n" +
                "  .....\n" +
                "  .....\n" +
                "  PPPPP\n" +
                "  RNBQK");
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
    }
}
