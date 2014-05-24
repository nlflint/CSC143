package SudokuGraphics;

import java.awt.*;

/**
 * Draws two dots
 */
class TwoDots implements GraphicSymbol {
    /**
     * Draws two dots centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int width = drawArea.width / 4;
        int height = drawArea .height / 4;

        // dots share the same y coordinate, but have different x coordinate
        int x1 = drawArea.width / 6 * 2 - (width / 2);
        int x2 = drawArea.width / 6 * 4 - (width / 2);
        int y = drawArea.height / 2 - (height / 2);

        g.fillOval(x1, y, width, height);
        g.fillOval(x2, y, width, height);
    }
}
