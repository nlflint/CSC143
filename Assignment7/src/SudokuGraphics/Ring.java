package SudokuGraphics;

import java.awt.*;

/**
 * draws a ring
 */
class Ring implements GraphicSymbol {
    /**
     * Draws a triangle pointing up centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        Graphics2D g2d = (Graphics2D)g;

        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;
         // draws a circle with a thick line
        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval(widthUnit, heightUnit, widthUnit * 4, heightUnit * 4);
    }
}
