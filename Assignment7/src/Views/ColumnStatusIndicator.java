package Views;

import Model.SudokuBase;

import java.awt.*;

/**
 * Monitor status of columns
 * Grading level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 7: Sudoku Serialization/Integration
 */
public class ColumnStatusIndicator extends StatusIndicator {

    /**
     * Constructor. Uses parent constructor
     * @param model Sudoku board to monitor
     */
    public ColumnStatusIndicator(SudokuBase model) {
        super(model);
    }

    /**
     * Overrides paint component to draw status indicator
     * @param g graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int numberOfColumns = model.size;

        // loop through each column
        for (int i = 0; i < numberOfColumns; i++) {
            // Define rectangle points for the column
            int startX =  i  * playSpaceWidthInPixels;
            int startY = 0;
            int endX = startX + playSpaceWidthInPixels;
            int endY = numberOfColumns * playSpaceWidthInPixels;
            // set status color of the column
            g.setColor(getColumnColor(i));
            // draw the column
            g.fillRect(startX, startY, endX, endY);
        }
    }

    // Gets color based on column status
    private Color getColumnColor(int columnIndex) {
        SudokuBase.State columnState = model.getColumnState(columnIndex);
        switch (columnState) {
            case COMPLETE:
                return Color.GREEN;
            case INCOMPLETE:
                return Color.YELLOW;
            case ERROR:
                return Color.RED;
        }
        return Color.WHITE;
    }
}