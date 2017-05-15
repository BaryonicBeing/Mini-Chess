package net.sebbo.fhws.adversarysearch.minichess;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by Sebastian Pekarek on 15.05.17.
 */
public class Board {
    private Square[][] squares = new Square[5][6];
    private String parseError = null;
    private int moveNum = 1;
    private char onMove;

    void Board() {
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

    void Board(String importString) {
        this.parseString(importString);
    }
    void Board(Reader importReader) {
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
        int row = 0;
        int column = 0;
        char thisChar;

        for(int i = 0; i <= importString.length(); i += 1) {
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
                this.squares[row][column] = new Square(column, row, thisChar);
                column += 1;
            }
        }
    }

    public String toString() {
        StringBuilder output = new StringBuilder();

        for (Square[] columns : this.squares) {
            for (Square column : columns) {
                output.append(
                    column.toString()
                );
            }
        }

        return output.toString();
    }

    public void print(Writer exportWriter) {
        // @todo
    }
}
