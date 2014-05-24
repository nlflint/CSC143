package SudokuGraphics;

import java.awt.*;

/**
 * draws a circle with an X over it
 */
class CircleX implements GraphicSymbol {
    /**
     * Draws a triangle pointing up centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        // Makes the lines thicker
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));

        // draws a circle and two lines
        g2d.drawOval(widthUnit, heightUnit, widthUnit * 4, heightUnit * 4);
        g2d.drawLine(widthUnit, heightUnit, 5 * widthUnit, 5 * heightUnit);
        g2d.drawLine(5 * widthUnit, heightUnit, widthUnit, 5 * heightUnit);
    }
}
