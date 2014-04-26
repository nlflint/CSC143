import org.junit.*;
import static org.junit.Assert.*;
/**
 * Created by nate on 4/25/14.
 */
public class SudukoBoardTest
{
    @Test
    public void getStateOfEmptyBoard()
    {
        // empty board
        SudokuBoard board = new SudokuBoard(9,9);

        // Act
        SudokuBoard.State rowState = board.getRowState(3);
        SudokuBoard.State columnState = board.getColumnState(1);
        SudokuBoard.State regionState = board.getRegionState(9);

        // Assert
        assertEquals("Unexpected row state", SudokuBoard.State.INCOMPLETE, rowState);
        assertEquals("Unexpected column state", SudokuBoard.State.INCOMPLETE, columnState);
        assertEquals("Unexpected region state", SudokuBoard.State.INCOMPLETE, regionState);
    }

    @Test
    public void getStateOfIncompleteBoard()
    {
        // Creates a board with complete row, column, and region
        SudokuBoard board = CreateTestBoard(2, 2,
                new int[][]
                {
                        {3,0,0,0},
                        {1,2,4,0},
                        {4,0,0,0},
                        {0,0,0,0}
                });

        // Act
        SudokuBoard.State rowState = board.getRowState(1);
        SudokuBoard.State columnState = board.getColumnState(0);
        SudokuBoard.State regionState = board.getRegionState(1);

        // Assert
        assertEquals("Unexpected row state", SudokuBoard.State.INCOMPLETE, rowState);
        assertEquals("Unexpected column state", SudokuBoard.State.INCOMPLETE, columnState);
        assertEquals("Unexpected region state", SudokuBoard.State.INCOMPLETE, regionState);

    }

    @Test
    public void getStateOfBoardWithDupes()
    {
        // Puts duplicate 4s in rows, columns and regions
        SudokuBoard board = CreateTestBoard(2, 2,
                new int[][]
                {
                        {0,0,4,0},
                        {0,0,0,0},
                        {4,0,0,4},
                        {0,0,4,0}
                });

        // Act
        SudokuBoard.State rowState = board.getRowState(2);
        SudokuBoard.State columnState = board.getColumnState(2);
        SudokuBoard.State regionState = board.getRegionState(3);

        // Assert
        assertEquals("Unexpected row state", SudokuBoard.State.ERROR, rowState);
        assertEquals("Unexpected column state", SudokuBoard.State.ERROR, columnState);
        assertEquals("Unexpected region state", SudokuBoard.State.ERROR, regionState);

    }

    @Test
    public void getStateOfCompleteBoard()
    {
        // A board with a complete row, column and region
        SudokuBoard board = CreateTestBoard(2, 2,
                new int[][]
                {
                        {1,2,3,4},
                        {3,4,0,0},
                        {2,0,0,0},
                        {4,0,0,0}
                });

        // Act
        SudokuBoard.State rowState = board.getRowState(3);
        SudokuBoard.State columnState = board.getColumnState(3);
        SudokuBoard.State regionState = board.getRegionState(4);

        // Assert
        assertEquals("Unexpected row state", SudokuBoard.State.COMPLETE, rowState);
        assertEquals("Unexpected column state", SudokuBoard.State.COMPLETE, columnState);
        assertEquals("Unexpected region state", SudokuBoard.State.COMPLETE, regionState);

    }

    private SudokuBoard CreateTestBoard(int rows, int columns, int[][] ints)
    {
        SudokuBoard board = new SudokuBoard(rows, columns);

        for (int i = 0; i < ints.length; i++)
            for(int j = 0; j < ints[i].length; j++)
                board.setValue(i, j, ints[i][j]);
        return board;
    }
}
