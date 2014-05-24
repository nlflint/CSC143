package SudokuGraphics;

import java.awt.*;

/**
 * Draws a Dot in the center of the given area.
 */

class OneDot implements GraphicSymbol {
    /**
     * Draws a dot centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g,Dimension drawArea) {
        int width = drawArea.width / 4;
        int height = drawArea .height / 4;
        int x = drawArea.width / 2 - (width / 2);
        int y = drawArea.height / 2 - (height / 2);

        g.fillOval(x, y, width, height);
    }
}
