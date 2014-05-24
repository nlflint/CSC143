package SudokuGraphics;

import java.awt.*;

/**
 * Code for drawing a number. Use constructor to configure which number.
 */
class Numeric implements GraphicSymbol {

    // Which number should be drawn.
    private int numeral;

    // constructor saves which number should be drawn.
    public Numeric(int number) { numeral = number; }

    /**
     * Draws the number given to constructor to the given graphics object, and within the given drawing area.
     * @param g graphics graphics object that will be used for drawing
     * @param drawArea digit will be centered in the area between 0,0 and this given dimension.
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        // Draw the numeral to the center of the area.
        g.setFont(new Font("Times New Roman", Font.BOLD, 24));
        g.drawString(numeral + "", (int) (drawArea.getWidth() / 2.8), (int) (drawArea.getHeight() / 1.5));
    }
}
