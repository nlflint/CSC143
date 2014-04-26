import java.util.Arrays;

/**
 * Created by nate on 4/25/14.
 */
public class SudokuBoard extends SudokuBase
{
    public SudokuBoard(int layoutRows, int layoutColumns)
    {
        super(layoutRows, layoutColumns);
    }

    @Override
    public State getRowState(int row)
    {
        int[] rowValues = new int[size];

        // build an array of values from the given row
        for (int column = 0; column < size; column++)
        {
            rowValues[column] = getValue(row, column);
        }
        return verifySudokuValues(rowValues);
    }

    private State verifySudokuValues(int[] values)
    {
        // Holds occurrences of each value
        int[] valueFrequency = new int[values.length];
        Arrays.fill(valueFrequency, 0);

        // Count occurrences of each value
        for (int i = 0; i < values.length; i ++)
        {
            // don't count zeros
            if (values[i] == 0)
                continue;

            // found a value
            valueFrequency[values[i] - 1] += 1;
        }

        // track if the board has errors and/or is incomplete
        boolean boardIsIncomplete = false;
        boolean boardHasDuplicate = false;

        // search for occurrences of duplicates or missing values
        for (int value : valueFrequency)
        {
            // duplicate values are an error
            if (value > 1)
                boardHasDuplicate = true;

            // missing values make the set incomplete
            if (value == 0)
                boardIsIncomplete = true;
        }

        // Errors have precedence over incomplete, so check errors first
        if (boardHasDuplicate)
            return State.ERROR;

        // Check for incomplete
        if (boardIsIncomplete)
            return State.INCOMPLETE;

        // everything must be fine
        return State.COMPLETE;


    }

    @Override
    public State getColumnState(int column)
    {
        int[] columnValues = new int[size];

        // builds an array of values from the given colummn
        for (int row = 0; row < size; row++)
            columnValues[row] = getValue(row, column);

        return verifySudokuValues(columnValues);
    }

    @Override
    public State getRegionState(int region)
    {
        // gets the x/y coordinates of region (in region coordinates)
        int regionX = region % rows;
        int regionY = region / rows;

        // identifies where on the board the region starts (in board coordinates).
        int startingRow = regionY * rows;
        int startingColumn = regionX * columns;

        // holds values from the given region
        int[] regionValues = new int[size];
        int valueIndex = 0;

        // populates array with values from the given region
        for (int row = startingRow; row < startingRow + rows; row++ )
            for (int column = startingColumn; column < startingColumn + columns; column++)
                regionValues[valueIndex++] = getValue(row, column);

        return verifySudokuValues(regionValues);
    }
}
