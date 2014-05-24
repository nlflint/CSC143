package SudokuGraphics;

import java.awt.*;

/**
 * Draws nothing. Used for Sudoku value 0.
 */
class Blank implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {}
}
