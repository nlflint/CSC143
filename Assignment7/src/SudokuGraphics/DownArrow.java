package SudokuGraphics;

import java.awt.*;

/**
 * draws a down arrow
 */
class DownArrow implements GraphicSymbol {
    /**
     * Draws an arrow pointing down centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        // Makes the line thicker
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));

        // Arrow is made of three lines
        g2d.drawLine(3 * widthUnit, 5 * heightUnit, 3 * widthUnit, heightUnit);
        g2d.drawLine(3 * widthUnit, 5 * heightUnit, 4 * widthUnit, 3 * heightUnit);
        g2d.drawLine(3 * widthUnit, 5 * heightUnit, 2 * widthUnit, 3 * heightUnit);
    }
}
