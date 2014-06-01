package Views;

import Model.SudokuBase;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

/**
 * Abstract class for row, column, region indicators
 * Grading level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 7: Sudoku Serialization/Integration
 */
abstract class StatusIndicator extends JPanel {
    // Model that is monitored
    protected SudokuBase model;
    // pixel width of a playcell when drawn in the indicator cell
    protected final int playSpaceWidthInPixels = 5;

    /**
     * Constructor. Sets up initial values.
     * @param model Model to monitor for status updates.
     */
    public StatusIndicator(SudokuBase model) {
        this.model = model;
        setBorder(new BorderUIResource.LineBorderUIResource(Color.black));
        int totalWidth = model.size * playSpaceWidthInPixels;
        setPreferredSize(new Dimension(totalWidth,totalWidth));

    }
}
