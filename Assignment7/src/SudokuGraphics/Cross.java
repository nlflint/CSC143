package SudokuGraphics;

import java.awt.*;

/**
 * draws a cross
 */
class Cross implements GraphicSymbol {
    /**
     * Draws a cross centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        // Make the line thicker
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(4));

        // Made of 2 lines
        g2d.drawLine(widthUnit * 3, heightUnit, widthUnit * 3, heightUnit * 5);
        g2d.drawLine(widthUnit * 2, heightUnit * 2, widthUnit * 4, heightUnit * 2);
    }
}
