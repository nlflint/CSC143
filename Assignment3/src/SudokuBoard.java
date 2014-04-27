/**
 * Implements the core logic of a Sudoku board.
 *
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 3: Sudoku Core
 */
public class SudokuBoard extends SudokuBase
{
    /**
     * Constructor. Takes the dimensions for a region, and constructor a entire blank board.
     * @param layoutRows rows in a region
     * @param layoutColumns columns in a region
     */
    public SudokuBoard(int layoutRows, int layoutColumns)
    {
        super(layoutRows, layoutColumns);
    }

    /**
     * Gets state of the given row.
     * @param row zero based index of row to get state
     * @return state of the row
     */
    public State getRowState(int row)
    {
        // get row of values as an array
        int[] rowValues = getLineOfValues(row, IterateOn.Columns);

        // Get and return state
        return verifySudokuValues(rowValues);
    }

    /**
     * Gets the state of the given column.
     * @param column Index of column to check. Is zero based.
     * @return State of the column
     */
    public State getColumnState(int column)
    {
        // Creates an array of values from the column
        int[] columnValues = getLineOfValues(column, IterateOn.Rows);

        // Get and return state
        return verifySudokuValues(columnValues);
    }

    /**
     * Get status of the given region.
     * @param region Index of the region. Zero based.
     * @return State of the region
     */
    public State getRegionState(int region)
    {
        // gets the x/y coordinates of region (in region coordinates)
        int regionX = region % rows;
        int regionY = region / rows;

        // identifies where on the board the region starts (in board coordinates).
        int startingRow = regionY * rows;
        int startingColumn = regionX * columns;

        // will hold values from the given region
        int[] regionValues = new int[size];
        int regionValueIndex = 0;

        // populates array with values from the given region
        for (int row = startingRow; row < startingRow + rows; row++ )
            for (int column = startingColumn; column < startingColumn + columns; column++)
                regionValues[regionValueIndex++] = getValue(row, column);

        // Get and return state
        return verifySudokuValues(regionValues);
    }

    // Gets a line of values from the board, either a row or column based on iteration type.
    private int[] getLineOfValues(int staticIndex, IterateOn iteratesOn)
    {
        int[] values = new int[size];

        // build an array of values from the given row
        for (int i = 0; i < size; i++)
        {
            // Get value based on iteration type
            values[i] = iteratesOn == IterateOn.Columns ? getValue(staticIndex, i) : getValue(i, staticIndex);
        }
        return values;
    }

    // gets the state of a set of values. used by row, column and region.
    private State verifySudokuValues(int[] sudokuValues)
    {
        // Create an array of the frequency of values, not counting zeros.
        int[] valueFrequency = createFrequencyOfValues(sudokuValues);

        // Errors have precedence over completeness, so check errors first
        if (errorsExist(valueFrequency))
            return State.ERROR;

        // Check for completeness
        if (dataSetIsComplete(valueFrequency))
            return State.COMPLETE;

        // values are missing
        return State.INCOMPLETE;
    }

    // takes given values, and returns an array of the frequency of those values, not counting zero
    private int[] createFrequencyOfValues(int[] sudokuValues) {
        // Holds occurrences of each value.
        // Indexes are offset by -1. So index 0 is value 1, index 1 is value 2, etc..
        int[] valueFrequency = new int[sudokuValues.length];

        // Count occurrences of each value
        for (int i = 0; i < sudokuValues.length; i ++)
        {
            // don't count zeros
            if (sudokuValues[i] == 0)
                continue;

            // increment count
            valueFrequency[sudokuValues[i] - 1] += 1;
        }
        return valueFrequency;
    }

    // search for occurrences of duplicate values
    private boolean errorsExist(int[] valueFrequency) {
        for (int value : valueFrequency)
        {
            // Values with a count of > 1 means there are duplicates, which is an error.
            if (value > 1)
                return true;
        }
        return false;
    }

    // searches for missing values,
    private boolean dataSetIsComplete(int[] valueFrequency) {
        for (int value : valueFrequency)
        {
            // A value with zero occurrences means it's missing, and the data set is incomplete.
            if (value == 0)
                return false;
        }
        // All values are accounted for
        return true;
    }

    // Identifies how getLineOfValues iterates through the board.
    private enum IterateOn
    {
        Rows,
        Columns
    }
}
