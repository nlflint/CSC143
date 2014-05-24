package SudokuGraphics;

import java.awt.*;

/**
 * draws three dots
 */
class ThreeDots implements GraphicSymbol {
    /**
     * Draws three dots in the center of the area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int width = drawArea.width / 4;
        int height = drawArea .height / 4;

        // all three dots centered and scaled within the given area.
        int x1 = drawArea.width / 6 * 2 - (width / 2);
        int x2 = drawArea.width / 6 * 4 - (width / 2);
        int y1 = drawArea.height / 6 * 2 - (height / 2);
        int y2 = drawArea.height / 6 * 4 - (height / 2);

        g.fillOval(x1, y1, width, height);
        g.fillOval(x2, y1, width, height);
        g.fillOval(x2, y2, width, height);
    }
}
