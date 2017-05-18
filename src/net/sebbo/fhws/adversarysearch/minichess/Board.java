package net.sebbo.fhws.adversarysearch.minichess;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private char onMove = 'W';

    public Board() throws Exception {
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

    public Board(String importString) throws Exception {
        this.parseString(importString);
    }
    public Board(Reader importReader) throws Exception {
        this.parseString(importReader.toString());
    }

    @Override
    public Board clone() {
        try {
            return new Board(
                this.toString()
            );
        }
        catch(Exception error) {
            return null;
        }
    }

    private void parseString(String importString) throws Exception {
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

        for(i = importString.indexOf("\n") + 1; i < importString.length(); i += 1) {
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
                this.squares[row][column] = new Square(this, row, column, '.');
                column += 1;
            }
            else {
                this.squares[row][column] = new Square(this, row, column, thisChar);
                column += 1;
            }
        }
    }

    public char move(Move m){
        Square from = m.from;
        Square to = m.to;
        char won = '?';

        // If a King is captured, the game is over with a win for other side
        if(this.squares[to.row][to.col].occupiedBy == 'K') {
            won = 'B';
        }
        else if(this.squares[to.row][to.col].occupiedBy == 'k') {
            won = 'W';
        }

        // Make the Move
        this.squares[to.row][to.col].occupiedBy = this.squares[from.row][from.col].occupiedBy;
        this.squares[from.row][from.col].occupiedBy = '.';

        if(won != '?') {
            return won;
        }

        // If a Pawn moves to its last rank, promote it to a Queen
        if(this.squares[to.row][to.col].occupiedBy == 'P' && to.row == 0) {
            this.squares[to.row][to.col].occupiedBy = 'Q';
        }
        else if(this.squares[to.row][to.col].occupiedBy == 'p' && to.row == this.squares.length - 1) {
            this.squares[to.row][to.col].occupiedBy = 'q';
        }

        moveNum++;
        onMove = moveNum % 2 == 0 ? 'B' : 'W';

        // If the move number becomes 81, the game is over and a tie
        if(this.moveNum >= 81) {
            return '=';
        }

        // if enemy is not able to move: lost
        if(this.listNextMoves().size() == 0) {
            return '=';
        }

        return '?';
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
    public String toReadableString() {
        StringBuilder o = new StringBuilder();


        // Header
        o.append("╔═════");
        for(int i = 0; i < this.getBoardWidth(); i += 1) {
            o.append("╦═════");
        }
        o.append("╗\n");

        o.append("║     ");
        for(int i = 0; i < this.getBoardWidth(); i += 1) {
            o.append("║  " + ((char) ('A' + i)) + "  ");
        }
        o.append("║\n");

        o.append("╠═════");
        for(int i = 0; i < this.getBoardWidth(); i += 1) {
            o.append("╬═════");
        }
        o.append("╣\n");


        // Body
        for(int i = 0; i < this.getBoardHeight(); i += 1) {
            o.append("║  " + (this.getBoardHeight() - i) + "  ║");

            for(Square piece: this.squares[i]) {
                o.append("  " + piece.getFigureReadableEmoji() + "  ║");
            }

            o.append("\n");


            if(i != this.getBoardHeight() - 1) {
                o.append("╠═════");
                for (int j = 0; j < this.getBoardWidth(); j += 1) {
                    o.append("╬═════");
                }
                o.append("╣\n");
            }else{
                o.append("╚═════");
                for (int j = 0; j < this.getBoardWidth(); j += 1) {
                    o.append("╩═════");
                }
                o.append("╝\n");
            }
        }

        return o.toString();
    }


    public void print(Writer exportWriter) throws java.io.IOException {
        exportWriter.write(this.toString());
    }


    public Square getSquareByPosition(String positionStr) {
        char[] position = positionStr.toCharArray();
        return squares[position[1] - 48][position[0] - 97];
    }
    public Square getSquareByPosition(char row, char column) {
        return squares[row - 48][column - 97];
    }
    public Square getSquareByPosition(int row, int column) {
        return squares[row][column];
    }

    public int getBoardWidth() {
        return this.squares[0].length;
    }
    public int getBoardHeight() {
        return this.squares.length;
    }

    public int getMoveNr() {
        return this.moveNum;
    }

    public char getCurrentMoveColor() {
        return this.onMove;
    }

    public Square[] getAllSquares() {
        int length = this.squares.length * this.squares[0].length;
        Square[] result = new Square[length];
        int i = 0;

        for (Square[] columns : this.squares) {
            for (Square piece : columns) {
                result[i] = piece;
                i += 1;
            }
        }

        return result;
    }

    public int getHeuristicScore() {
        return this.getHeuristicScore(this.getCurrentMoveColor(), false);
    }
    public int getHeuristicScore(char color) {
        return this.getHeuristicScore(color, false);
    }
    public int getHeuristicScore(char color, boolean logging) {
        int score = 0;
        int tmpScore;

        if(logging) System.out.println("\n#### HEURISTIC START ####");
        for(Square piece: this.getAllSquares()) {
            if(piece.isOccupied()) {
                if(logging) System.out.print(
                    "- Piece " + piece.toString() + " is occupied by " +
                    (piece.getFigureColor() == color ? "my" : "enemy's") + " " + piece.getFigureName() +
                    " (" + piece.getFigureHeuristicScore() + ") -> "
                );

                tmpScore = (piece.getFigureColor() == color ? 1 : -1) * piece.getFigureHeuristicScore();
                score += tmpScore;

                if(logging) System.out.println(
                    tmpScore + " -> Score is now " + score
                );
            }
        }

        if(logging) System.out.println("#### HEURISTIC END ####\n");

        return score;
    }

    public LinkedList<Move> listNextMoves() {
        MoveGenerator mg = new MoveGenerator(this);
        return mg.moveList();
    }
    public LinkedList<Move> listNextMoves(boolean debug) {
        MoveGenerator mg = new MoveGenerator(this, debug);
        return mg.moveList();
    }
    public LinkedList<Move> listMovesFor(Square piece) {
        MoveGenerator mg = new MoveGenerator(this);
        return mg.moveList(piece);
    }
    public LinkedList<Move> listMovesFor(Square piece, boolean debug) {
        MoveGenerator mg = new MoveGenerator(this, debug);
        return mg.moveList(piece);
    }

    public static void main(String[] args) throws Exception {

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

        System.out.println("\n## Start test for constructor with Reader as parameter!");
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

}
