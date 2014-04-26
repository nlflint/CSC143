import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests the sudoku board.
 *
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 3: Sudoku Core
 */
public class SudukoBoardStateTest
{
    @Test
    public void emptyBoard()
    {
        // empty board
        SudokuBoard board = new SudokuBoard(3,3);

        // Get states of rows, columns, and regions
        SudokuBoard.State rowState = board.getRowState(3);
        SudokuBoard.State columnState = board.getColumnState(1);
        SudokuBoard.State regionState = board.getRegionState(0);

        // All states should be incomplete
        assertEquals("Unexpected row state", SudokuBoard.State.INCOMPLETE, rowState);
        assertEquals("Unexpected column state", SudokuBoard.State.INCOMPLETE, columnState);
        assertEquals("Unexpected region state", SudokuBoard.State.INCOMPLETE, regionState);
    }

    @Test
    public void incompleteParts()
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

        // Get states of rows, columns, and regions
        SudokuBoard.State rowState = board.getRowState(1);
        SudokuBoard.State columnState = board.getColumnState(0);
        SudokuBoard.State regionState = board.getRegionState(1);

        // All states should be incomplete
        assertEquals("Unexpected row state", SudokuBoard.State.INCOMPLETE, rowState);
        assertEquals("Unexpected column state", SudokuBoard.State.INCOMPLETE, columnState);
        assertEquals("Unexpected region state", SudokuBoard.State.INCOMPLETE, regionState);

    }

    @Test
    public void errorHasPreferenceOverIncomplete()
    {
        // Board with regions, rows and columns that are in two states, incomplete and duplicate.
        SudokuBoard board = CreateTestBoard(2, 2,
                new int[][]
                {
                        {0,0,4,0},
                        {0,0,0,0},
                        {4,0,0,4},
                        {0,0,4,0}
                });

        // Get state of interesting rows, columns, and regions
        SudokuBoard.State rowState = board.getRowState(2);
        SudokuBoard.State columnState = board.getColumnState(2);
        SudokuBoard.State regionState = board.getRegionState(3);

        // All states should be ERROR. Error has preference over incomplete.
        assertEquals("Unexpected row state", SudokuBoard.State.ERROR, rowState);
        assertEquals("Unexpected column state", SudokuBoard.State.ERROR, columnState);
        assertEquals("Unexpected region state", SudokuBoard.State.ERROR, regionState);

    }

    @Test
    public void errorParts()
    {
        // Puts duplicate 4s in rows, columns and regions
        SudokuBoard board = CreateTestBoard(2, 2,
                new int[][]
                        {
                                {0,0,4,0},
                                {0,0,1,0},
                                {4,3,2,4},
                                {0,0,1,2}
                        });

        // Get state of interesting rows, columns, and regions
        SudokuBoard.State rowState = board.getRowState(2);
        SudokuBoard.State columnState = board.getColumnState(2);
        SudokuBoard.State regionState = board.getRegionState(3);

        // All states should be ERROR
        assertEquals("Unexpected row state", SudokuBoard.State.ERROR, rowState);
        assertEquals("Unexpected column state", SudokuBoard.State.ERROR, columnState);
        assertEquals("Unexpected region state", SudokuBoard.State.ERROR, regionState);

    }

    @Test
    public void completeParts()
    {
        // A board with a complete row, column and region
        SudokuBoard board = CreateTestBoard(2, 2,
                new int[][]
                {
                        {1,2,3,4},
                        {3,0,1,2},
                        {2,0,0,0},
                        {4,0,0,0}
                });

        // Act
        SudokuBoard.State rowState = board.getRowState(0);
        SudokuBoard.State columnState = board.getColumnState(0);
        SudokuBoard.State regionState = board.getRegionState(1);

        // Assert
        assertEquals("Unexpected row state", SudokuBoard.State.COMPLETE, rowState);
        assertEquals("Unexpected column state", SudokuBoard.State.COMPLETE, columnState);
        assertEquals("Unexpected region state", SudokuBoard.State.COMPLETE, regionState);

    }

    @Test
    public void completedBoard()
    {
        // A completed error-free board with 3x3 regions
        SudokuBoard board = CreateTestBoard(3, 3,
                new int[][]
                        {
                                {4,5,2,3,9,1,8,7,6},
                                {3,1,8,6,7,5,2,9,4},
                                {6,7,9,4,2,8,3,1,5},
                                {8,3,1,5,6,4,7,2,9},
                                {2,4,5,9,8,7,1,6,3},
                                {9,6,7,2,1,3,5,4,8},
                                {7,9,6,8,5,2,4,3,1},
                                {1,8,3,7,4,9,6,5,2},
                                {5,2,4,1,3,6,9,8,7}
                        });

        // verify all columns, rows, and regions are complete
        int[] values = new int[] {0,1,2,3,4,5,6,7,8};

        for (int index : values)
        {
            SudokuBoard.State rowState = board.getRowState(index);
            assertEquals("Unexpected row state", SudokuBoard.State.COMPLETE, rowState);

            SudokuBoard.State columnState = board.getColumnState(index);
            assertEquals("Unexpected column state", SudokuBoard.State.COMPLETE, columnState);

            SudokuBoard.State regionState = board.getRegionState(index);
            assertEquals("Unexpected region state", SudokuBoard.State.COMPLETE, regionState);
        }
    }

    @Test
    public void completed3x2Board()
    {
        // A completed error-free board with 3x2 regions
        SudokuBoard board = CreateTestBoard(2, 3,
                new int[][]
                        {
                                {6,5,1,3,4,2},
                                {4,3,2,1,6,5},
                                {2,4,6,5,1,3},
                                {5,1,3,4,2,6},
                                {1,2,5,6,3,4},
                                {3,6,4,2,5,1}


                        });

        // verify all columns, rows, and regions are complete
        int[] values = new int[] {0,1,2,3,4,5};

        for (int index : values)
        {
            SudokuBoard.State rowState = board.getRowState(index);
            assertEquals("Unexpected row state", SudokuBoard.State.COMPLETE, rowState);

            SudokuBoard.State columnState = board.getColumnState(index);
            assertEquals("Unexpected column state", SudokuBoard.State.COMPLETE, columnState);

            SudokuBoard.State regionState = board.getRegionState(index);
            assertEquals("Unexpected region state", SudokuBoard.State.COMPLETE, regionState);
        }
    }

    @Test
    public void completed2x3Board()
    {
        // A completed error-free board with 2x3 regions
        SudokuBoard board = CreateTestBoard(3, 2,
                new int[][]
                        {
                                {1,5,6,2,4,3},
                                {3,2,1,4,6,5},
                                {4,6,3,5,1,2},
                                {5,4,2,1,3,6},
                                {6,1,5,3,2,4},
                                {2,3,4,6,5,1}


                        });

        // verify all columns, rows, and regions.
        int[] values = new int[] {0,1,2,3,4,5};

        for (int index : values)
        {
            SudokuBoard.State rowState = board.getRowState(index);
            assertEquals("Unexpected row state", SudokuBoard.State.COMPLETE, rowState);

            SudokuBoard.State columnState = board.getColumnState(index);
            assertEquals("Unexpected column state", SudokuBoard.State.COMPLETE, columnState);

            SudokuBoard.State regionState = board.getRegionState(index);
            assertEquals("Unexpected region state", SudokuBoard.State.COMPLETE, regionState);
        }
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
