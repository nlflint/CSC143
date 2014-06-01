package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import Model.SudokuBase;
import SudokuGraphics.*;

/**
 * Renders a sudoku board.
 *
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment4: Sudoku Graphics
 */
public class SudokuView extends JPanel
        implements SelectedCell, NumericSupport, Observer {

    // Hold the Sudoku board information
    private int numberRowsInRegion, numberColumnsInRegion, boardWidth;

    // Contains graphics library for drawing symbals and numbers
    private GraphicsLibrary graphicsLibrary;

    // Contains references to the play spaces
    private PlaySpace[][] playSpaces;

    // the currently selected space
    private PlaySpace selectedPlaySpace;

    /**
     * Constructor sets up graphics and binds it to the sudoku board.
     * @param base The sudoku board
     */
    public SudokuView(SudokuBase base) {

        // Save the sudoku board information
        numberRowsInRegion = base.rows;
        numberColumnsInRegion = base.columns;
        boardWidth = base.size;

        // Set white background
        setBackground(Color.white);

        // Setup graphics
        graphicsLibrary = GraphicsLibrary.getInstance();

        // Set spacing around entire board.
        configureEdgeBorder();

        // Create playing squares
        createPlaySpaces(base);

        // Add keyboard listener
        initializeKeyboardListener();

        // Add this view as an observer of the model
        base.addObserver(this);

        // show everything
        setVisible(true);

    }

    // Creates a boarder around edge of the board
    private void configureEdgeBorder() {
        Border line = new BorderUIResource.LineBorderUIResource(Color.black, 1);
        Border empty = new BorderUIResource.EmptyBorderUIResource(2,2,2,2);
        setBorder(new BorderUIResource.CompoundBorderUIResource(line, empty));
    }

    // Creates all the play spaces on the board.
    private void createPlaySpaces(SudokuBase base) {
        // Initialize an array to save references to the places spaces.
        // Board is always square
        playSpaces = new PlaySpace[boardWidth][boardWidth];

        //setup grid layout with spacing.
        GridLayout layout = new GridLayout(boardWidth, boardWidth);
        layout.setVgap(2);
        layout.setHgap(2);
        setLayout(layout);

        // create play spaces by row and column
        for (int row = 0; row < boardWidth; row++) {
            for (int column = 0; column < boardWidth; column++) {
                // Create the play space
                PlaySpace playSpace = new PlaySpace(base, graphicsLibrary, row, column);

                // Set the background color based on the region of the space
                playSpace.setBackground(getRegionBackgroundColor(row, column));

                // Enable space to respond to mouse clicks
                playSpace.addMouseListener(new MouseHandler(this));

                // Add space to an array so that it can be referenced for set-selected-cell methods.
                playSpaces[row][column] = playSpace;

                // add the space to the UI.
                add(playSpace);
            }
        }
    }

    // this formula gives the regions an alternating background color.
    private Color getRegionBackgroundColor(int row, int column) {
        // Get even-ness of the region axis where the cell resides
        boolean regionRowIsEven = isRegionAxisIndexEven(column, numberRowsInRegion);
        boolean regionColumnIsEven = isRegionAxisIndexEven(row, numberColumnsInRegion);

        // if row and column have the same even-ness then color is light gray, else it's white.
        if (regionRowIsEven == regionColumnIsEven)
            return Color.lightGray;
        else
            return Color.white;
    }

    // Calculates even-ness of the region where the space resides, along one axis
    private boolean isRegionAxisIndexEven(int spaceIndex, int numberSpacesAlongRegion) {
        // Calculate which region the cell is in, along one axis.
        int numberRegionsOnAxis = boardWidth / numberSpacesAlongRegion;
        int regionAxisIndex = spaceIndex / numberRegionsOnAxis;

        // Determine if the region's index on this axis is even
        return regionAxisIndex % 2 == 0;
    }

    // Allows the view the sense keyboard input.
    private void initializeKeyboardListener() {
        // Adds a my keylistener and focuses the window.
        addKeyListener(new KeyHandler(this));
        setFocusable(true);
        requestFocusInWindow();
    }

    /**
     * Selects the play space at the given row and column
     * @param row The indicated row
     * @param col The indicated column
     */
    @Override
    public void setSelected(int row, int col) {
        // Make sure the row and columns are within the game board
        if (row < 0 || row >= boardWidth || col < 0 || col >= boardWidth)
            // out of range, do nothing.
            return;

        // Un-colors the existing selected play space. Make sure one is selected before trying to rest its color.
        if (selectedPlaySpace != null)
            selectedPlaySpace.setBackground(
                    getRegionBackgroundColor(selectedPlaySpace.getRow(), selectedPlaySpace.getColumn()));

        // Save the new selected play space and change its background color to yellow
        selectedPlaySpace = playSpaces[row][col];
        selectedPlaySpace.setBackground(Color.yellow);
    }

    /**
     * Gets the row index of the currently selected play space
     * @return row
     */
    @Override
    public int getSelectedRow() {
        // If no playspace is selected return 0.
        if (selectedPlaySpace == null)
            return 0;
        return selectedPlaySpace.getRow();
    }

    /**
     * Gets the column index of the currently selected play space
     * @return column
     */
    @Override
    public int getSelectedColumn() {
        // If no playspace is selected return 0.
        if (selectedPlaySpace == null)
            return 0;
        return selectedPlaySpace.getColumn();
    }

    /**
     * Sets if the board symbols should be drawn as numbers
     * @param flag true if the values of the board should show as numbers.
     */
    @Override
    public void setNumeric(boolean flag) {
        // Update the graphics library based on the given flag
        if (flag)
            graphicsLibrary.setGraphicsType(GraphicsLibrary.GraphicsType.Numeric);
        else
            graphicsLibrary.setGraphicsType(GraphicsLibrary.GraphicsType.Symbolic);

    }

    /**
     * Indicates if the sudoku values are displayed as numbers.
     * @return type of graphics being used
     */
    @Override
    public boolean showsNumeric() {
        // Look into the graphics library to see if its set to numeric
        return graphicsLibrary.getGraphicsType() == GraphicsLibrary.GraphicsType.Numeric;
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}

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


class MouseHandler extends MouseAdapter {

    private SelectedCell view;

    /**
     * Constructor. Saves a reference to the view so cells can be selected.
     * @param view
     */
    public MouseHandler(SelectedCell view) {
        this.view = view;
    }

     /**
     * Selects a cell on the view when mouse is clicked
     * @param e event info
     */
    @Override
    public void mousePressed(MouseEvent e) {
        super.mouseClicked(e);

        // Change selected row to the space that was clicked
        PlaySpace playSpace = ((PlaySpace)e.getSource());
        view.setSelected(playSpace.getRow(), playSpace.getColumn());
    }
}

/**
 * Handles keyboard input for moving the selected cell with arrow keys
 */
class KeyHandler extends KeyAdapter {
    // The selectedCell class will be used to change the selected cell
    SelectedCell view;

    /**
     * Constructor holds a reference to the selected cell object
     * @param view the selected cell object
     */
    public KeyHandler(SelectedCell view) { this.view = view; }

    /**
     * This method is executed everytime a key is pressed.
     * @param e event information
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Get the currently selected column and row
        int selectedRow = view.getSelectedRow();
        int selectedColumn = view.getSelectedColumn();

        // Based on the key, alter the currently selected row.
        // Not worrying of the selected cell goes off the board because
        // the selected cell object will handle that.
        switch (e.getKeyCode()) {
            // If up was pressed
            case KeyEvent.VK_UP:
                view.setSelected(selectedRow - 1, selectedColumn);
                break;
            // If down was pressed
            case KeyEvent.VK_DOWN:
                view.setSelected(selectedRow + 1, selectedColumn);
                break;
            // If left was pressed
            case KeyEvent.VK_LEFT:
                view.setSelected(selectedRow, selectedColumn - 1);
                break;
            // If right was pressed
            case KeyEvent.VK_RIGHT:
                view.setSelected(selectedRow, selectedColumn + 1);
                break;
        }
    }
}


