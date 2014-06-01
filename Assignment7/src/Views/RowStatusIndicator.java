package Views;

import Model.SudokuBase;

import java.awt.*;

/**
 * Indicates status of rows
 * Grading level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 7: Sudoku Serialization/Integration
 */
class RowStatusIndicator extends StatusIndicator {

    /**
     * Constructor just uses parent constructor.
     * @param model
     */
    public RowStatusIndicator(SudokuBase model) {
        super(model);
    }

    /**
     * Overrides paint component to draw indicator
     * @param g graphics object
     */

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int numberOfRows = model.size;

        // loop through rows
        for (int i = 0; i < numberOfRows; i++) {
            // Defines rectangle points for drawing the row
            int startX =  0;
            int startY = i  * playSpaceWidthInPixels;
            int endX = numberOfRows * playSpaceWidthInPixels;
            int endY = startY + playSpaceWidthInPixels ;

            // Sets color status
            g.setColor(getRowColor(i));
            // draw the row
            g.fillRect(startX, startY, endX, endY);
        }
    }

    // Gets color based on row status
    private Color getRowColor(int rowIndex) {
        SudokuBase.State rowState = model.getRowState(rowIndex);
        switch (rowState) {
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
