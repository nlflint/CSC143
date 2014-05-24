package SudokuGraphics;

import java.awt.*;

/**
 * draws a star
 */
class Star implements GraphicSymbol {
    /**
     * Draws a star centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        // the star is made of 2 triangles overlaping each other
        int[] xPoints1 = new int[] {3 * widthUnit, 5 * widthUnit, widthUnit};
        int[] yPoints1 = new int[] {heightUnit, 4 * heightUnit, 4 * heightUnit};

        int[] xPoints2 = new int[] {widthUnit, 5 * widthUnit, 3 * widthUnit};
        int[] yPoints2 = new int[] {2 * heightUnit, 2 * heightUnit, 5 * heightUnit};

        g.fillPolygon(xPoints1, yPoints1, xPoints1.length);
        g.fillPolygon(xPoints2, yPoints2, xPoints2.length);
    }
}
