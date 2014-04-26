/**
 * Created by nate on 4/25/14.
 */
public class SudokuBoard extends SudokuBase
{
    public SudokuBoard(int layoutRows, int layoutColumns) {
        super(layoutRows, layoutColumns);
    }

    @Override
    public State getRowState(int n) {
        return null;
    }

    @Override
    public State getColumnState(int n) {
        return null;
    }

    @Override
    public State getRegionState(int n) {
        return null;
    }
}
