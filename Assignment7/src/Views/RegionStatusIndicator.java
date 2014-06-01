package Views;

import Model.SudokuBase;

import java.awt.*;

/**
 * Indicates the status of regions
 * Grading level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 7: Sudoku Serialization/Integration
 */
class RegionStatusIndicator extends StatusIndicator {

    /**
     * Constructor uses parent constructor.
     * @param model Model to monitor
     */
    public RegionStatusIndicator(SudokuBase model) {
        super(model);

    }

    /**
     * Overrides paint component to draw region status.
     * @param g graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the shape and number of regions
        int boardWidth = model.size;
        int columns = model.columns;
        int rows = model.rows;
        int regionWidth = model.columns;
        int regionHeight = model.rows;
        int regionCount = (boardWidth / columns) * (boardWidth / rows);
        int regionsAcross = boardWidth / columns;

        // Loop throgh regions
        for (int i = 0; i < regionCount; i++) {
            // defines rectangle points for drawing the region
            int startX =  (i % regionsAcross) * regionWidth * playSpaceWidthInPixels;
            int startY = (i / regionsAcross) * regionHeight * playSpaceWidthInPixels;
            int endX = startX + (regionWidth * playSpaceWidthInPixels);
            int endY = startY + (regionHeight * playSpaceWidthInPixels);
            // gets color status for the region
            g.setColor(getRegionColor(i));
            // draw the region
            g.fillRect(startX, startY, endX, endY);
        }
    }

    // Gets color based on region status
    private Color getRegionColor(int regionIndex) {
        SudokuBase.State regionState = model.getRegionState(regionIndex);
        switch (regionState) {
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
