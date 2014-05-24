package SudokuGraphics;

import java.awt.*;

/**
 * Draws a triangle pointing down
 */
class DownTriangle implements GraphicSymbol {
    /**
     * Draws a triangle pointing down centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        // Three points of the triangle
        int[] xPoints = new int[] {widthUnit, 5 * widthUnit, 3 * widthUnit};
        int[] yPoints = new int[] { heightUnit, heightUnit, 5 * heightUnit};

        g.fillPolygon(xPoints, yPoints, xPoints.length);
    }
}
