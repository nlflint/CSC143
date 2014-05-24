package SudokuGraphics;

import java.awt.*;

/**
 * All graphics implement this interface for drawing withing the given draw area.
 */
public interface GraphicSymbol {
    public void draw(Graphics g, Dimension drawArea);
}
