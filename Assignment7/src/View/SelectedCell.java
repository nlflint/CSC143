package View;

/**
 * Interface to support the programmatic control of the
 * SelectedCell in SudokuView.
 */
public interface SelectedCell {

    /**
     * Set the selected cell to the given row and column.
     * @param row The indicated row
     * @param col The indicated column
     */
    public void setSelected(int row, int col);

    /**
     * Retrive the row of the currently selected cell.
     * @return The row in which the selected cell is located.
     */
    public int getSelectedRow();

    /**
     * Retrive the column of the currently selected cell.
     * @return The column in which the selected cell is located.
     */
    public int getSelectedColumn();

}