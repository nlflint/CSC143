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

        for (int column = 0; column < size; column++)
        {
            rowValues[column] = getValue(row, column);
        }
        return sudokuVerify(rowValues);
    }

    private State sudokuVerify(int[] values)
    {
        // Holds occurrences of each value
        int[] valueFrequency = new int[values.length + 1];
        Arrays.fill(valueFrequency, 0);

        // Count occurrences of each value
        for (int i = 0; i < values.length - 1; i ++)
        {
            // dont count zeros
            if (values[i] == 0)
                continue;

            valueFrequency[values[i]] += 1;
        }

        for (int value : valueFrequency)
        {
            // duplicate values mean error error
            if (value > 1)
                return State.ERROR;

            // missing values mean data is incomplete
            if (value == 0)
                return State.INCOMPLETE;
        }

        // everything must be find
        return State.COMPLETE;


    }

    @Override
    public State getColumnState(int n) {
        return State.INCOMPLETE;
    }

    @Override
    public State getRegionState(int n) {
        return State.INCOMPLETE;
    }
}
