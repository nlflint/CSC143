package Views;

import Model.SudokuBase;
import SudokuGraphics.GraphicSymbol;
import SudokuGraphics.GraphicsLibrary;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

/**
 * Represents a play space on the sudoku board.
 */
class PlaySpace extends JPanel {
    // holds the state of the sudoku board
    SudokuBase base;

    // The row an column that this play space represents
    private int row, column;

    // Contains methods to translate sudoku values into graphics
    GraphicsLibrary library;

    /**
     * Constructs a play space.
     * @param base a Sudoku board
     * @param library a graphics library
     * @param row the row that this play space represents on the sudoku board
     * @param column the row that this play space represents on the sudoku board
     */
    public PlaySpace(SudokuBase base, GraphicsLibrary library, int row, int column) {
        // Save member data
        this.base = base;
        this.row = row;
        this.column = column;
        this.library = library;

        // Set size and a border
        setPreferredSize(new Dimension(50, 50));
        setBorder(new BorderUIResource.LineBorderUIResource(Color.black));
    }

    /**
     * Draws this play space
     * @param g graphics object that will be used for drawing
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // gets a value for the sudoku board
        int value = base.getValue(row, column);

        // Set the color of the graphics based on if the value was given.
        Color givenColor = base.isGiven(row, column) ? new Color(114, 221, 114): Color.black;
        g.setColor(givenColor);

        // use the value to get a class that draws the right symbol
        GraphicSymbol graphic = library.getGraphic(value);

        // sets constraints that the graphic class will draw within.
        Dimension drawingArea = new Dimension(getWidth(), getHeight());

        // call the class to draw the graphic.
        graphic.draw(g, drawingArea);
    }

    /**
     * Gets the row on the board where this playspace resides
     * @return row
     */
    public int getRow() { return row;}

    /**
     * Get teh column on the board where this playspace resides
     * @return the column
     */
    public int getColumn() { return column;}
}
