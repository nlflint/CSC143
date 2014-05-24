package SudokuGraphics;

import java.awt.*;

/**
 * draws a triangle pointing up
 */
class UpTriangle implements GraphicSymbol {
    /**
     * Draws a triangle pointing up centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {

        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        // 3 scaled points of the triangle
        int[] xPoints = new int[] {3 * widthUnit, 5 * widthUnit, widthUnit};
        int[] yPoints = new int[] {heightUnit, 5 * heightUnit, 5 * heightUnit};

        g.fillPolygon(xPoints, yPoints, xPoints.length);
    }
}
