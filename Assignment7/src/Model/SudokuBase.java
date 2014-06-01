package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Abstract class for a Sudoku board.
 *
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 3: Sudoku Core
 */
public abstract class SudokuBase extends java.util.Observable {

    /**
     * Rows in a region. This field is shared by many methods.
     */
    public final int rows;

    /**
     * Columns in a region. This field is shared by many methods.
     */
    public final int columns;

    /**
     * Number of elements in a region. This field is shared by many methods.
     */
    public final int size;

    // Holds values for all elements of the sudoku board. This field is shared by many methods.
    private final int[] grid;

    // Bit pattern to flag values as given. This field is shared by many methods.
    private static final int GIVEN_MASK = 0x00000100;

    // Bit pattern to remove given flag. This field is shared by many methods.
    private static final int GIVEN_UNMASK = ~ GIVEN_MASK;

    public void resetGame() {
        for (int row = 0; row < size; row++)
            for (int column = 0; column < size; column++)
            if (!isGiven(row, column))
                setValue(row, column, 0);
    }

    /**
     * The state of a row, column or region based on its values.
     */
    public enum State {COMPLETE, INCOMPLETE, ERROR};

    /**
     * Constructor. Takes the dimensions of a region. Total size of board is calculated from region dimensions.
     *
     * @param layoutRows number of rows in a region
     * @param layoutColumns number of columns in a region
     */
    public SudokuBase(int layoutRows, int layoutColumns) {
        // Store region dimensions
        rows = layoutRows;
        columns = layoutColumns;

        // store number of elements in a region. Also the length and height of the board.
        size = columns * rows;

        // Create a blank grid. length and height match size so square size.
        grid = new int[size*size];
    }

    // Given a row and column, returns index of that location in the grid array.
    private int getIndex(int row, int col) {
        // Test if row and column are within the grid.
        if(row < 0 || row >= size || col < 0 || col >= size) {
            String msg = "Error in location";
            throw new IllegalArgumentException(msg);
        }

        // returns index based on row and column
        return row * size + col;
    }

    /**
     * Gets the value from the board at the given coordinates. Coordinates are zero based.
     * @param row row on the sudoku board
     * @param col column on the sudoku board
     * @return value at the given row and column
     */
    public int getValue(int row, int col) {
       return grid[getIndex(row, col)] & GIVEN_UNMASK;
    }

    /**
     * Sets the given row and column element with the given value. Cannot overwrite given values.
     * @param row row on the sudoku board
     * @param col column on the sudoku board
     * @param value value to set
     */
    public void setValue(int row, int col, int value) {
        // Test if row and column are within the grid.
        if(value < 0 || value > size) {
          String msg = "Value out of range: " + value;
          throw new IllegalArgumentException(msg);
        }

        // Test if element is given
        if(isGiven(row, col)) {
           String msg = "Cannot set given location: " + row + ", " + col;
           throw new IllegalStateException(msg);
        }

        // Set the value
        grid[getIndex(row, col)] = value;
        setChanged();
        notifyObservers();
    }

    /**
     * Determines if the element at row and column is given.
     * @param row Row on the sudoku board
     * @param col Column on the sudoku board.
     * @return true if the value is given, false if it is not given
     */
    public boolean isGiven(int row, int col) {
        return (grid[getIndex(row, col)] & GIVEN_MASK) == GIVEN_MASK;
    }

    /**
     * Sets all current values on the board as given values.
     */
    public void fixGivens() {
        for(int i = 0; i < grid.length; i++)
           if(grid[i] != 0)
              grid[i] |= GIVEN_MASK;
        setChanged();
        notifyObservers();
    }

    /**
     * Gets state of the given row.
     * @param n zero based index of row to get state
     * @return state of the row
     */
    public abstract State getRowState(int n);

    /**
     * Gets the state of the given column.
     * @param n Index of column to check. Is zero based.
     * @return State of the column
     */
    public abstract State getColumnState(int n);

    /**
     * Get status of the given region.
     * @param n Index of the region. Zero based.
     * @return State of the region
     */
    public abstract State getRegionState(int n);

    /**
     * Gets a string representation of the entire board.
     * @return string representation of all values on the sudoku board.
     */

    public int getRegionIndex(int row, int column) {
        int regionsAcross = size / columns;
        int regionsDown = size / rows;

        int regionColumnIndex = column / columns;
        int regionRowIndex = row / rows;

        return regionRowIndex * regionsAcross + regionColumnIndex;

    }

    public String toString() {
        String board = "";

        // Traverse the board.
        for(int i = 0; i < size; i ++) {
            for(int j = 0; j < size; j ++)
                // add value to string
                board += charFor(i, j) + " ";
            //At the end of each row inserts a new line.
            board += "\n";
        }
        return board;
    }

    //
    private String charFor(int i, int j) {
        int v = getValue(i, j);

        if(v < 0) {
            // print ? is value is less than zero.
            return "?";
        } else if(v == 0) {
            // print blank if value is zero
            return " ";
        } else if(v < 36) {
            // print numeral of values, less than 36.
            return Character.toString(Character.forDigit(v, 36)).toUpperCase();
        } else {
            // for all other values, print "?"
            return "?";
        }
    }

    //Sets values of the sudoku board using given input stream
    protected void readFromStream(java.io.InputStream is) throws IOException {
        // Create a stream that can write binary data
        DataInputStream dataIn = new DataInputStream(is);

        // Save each value from the raw grid.
        for (int i = 0; i < (size * size); i++)
            grid[i] = dataIn.readInt();
    }

    //Writes current values of the sudoku board to the given stream
    protected void writeToStream(java.io.OutputStream os) throws IOException {
        // Create a stream that can write binary data
        DataOutputStream dataOut = new DataOutputStream(os);

        // Save each value from the raw grid.
        for (int value : grid)
            dataOut.writeInt(value);
    }
}
