import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Renders a sudoku board.
 *
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment4: Sudoku Graphics
 */
public class SudokuView extends JPanel
        implements SelectedCell, NumericSupport {

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
        graphicsLibrary = new GraphicsLibrary();

        // Set spacing around entire board.
        configureEdgeBorder();

        // Create playing squares
        createPlaySpaces(base);

        // Add keyboard listener
        initializeKeyboardListener();

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
        boolean regionRowIsEven = isRegionAxisIndexEven(column, numberColumnsInRegion);
        boolean regionColumnIsEven = isRegionAxisIndexEven(row, numberRowsInRegion);

        // XOR logic, if row and column have the same even-ness then color is light gray, else it's white.
        if (regionRowIsEven ^ regionColumnIsEven)
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
        // Save data
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
        Color givenColor = base.isGiven(row, column) ? Color.black : new Color(114, 221, 114);
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

/**
 * A lookup table for getting classes to draw the symbols.
 */
class GraphicsLibrary {
    // Which set of symbols to use. numbers or pictures.
    enum GraphicsType {
        Symbolic,
        Numeric
    }

    // The lookup tables for graphics
    private Map<Integer, GraphicSymbol> symbolicDefinitions;
    private Map<Integer, GraphicSymbol> numericDefinitions;

    // Identifies which set of graphics are currently being used
    GraphicsType graphicsType;

    /**
     * Constructor. Defaults to Symbolic graphics.
     */
    public GraphicsLibrary() {
        buildNumericLibrary();
        buildSymbolicLibrary();
        setGraphicsType(GraphicsType.Symbolic);
    }

    /**
     * Sets which graphics set the library should present, numbers or symbols.
     * @param type Numbers or symbols
     */
    public void setGraphicsType(GraphicsType type) { graphicsType = type; }

    /**
     * Gets teh currently configured graphics type.
     * @return numeric or symbolic
     */
    public GraphicsType getGraphicsType() { return graphicsType; }

    /**
     * Gets a graphics object for drawing the given sudoku value.
     * @param number sudoku value
     * @return graphics object
     */
    public GraphicSymbol getGraphic(int number) {
        // look up the graphic in the correct library
        if (graphicsType == GraphicsType.Numeric)
            return numericDefinitions.get(number);
        else
            return symbolicDefinitions.get(number);
    }

    // Builds a hash map with numeric graphics
    private void buildNumericLibrary() {
        // resets the hashmap and sets a blank graphic for value 0
        numericDefinitions = new HashMap<Integer, GraphicSymbol>();
        numericDefinitions.put(0, new Blank());

        // Set values 1 - 12 with numerical graphics
        for (int i = 1; i < 13; i++)
            numericDefinitions.put(i, new Numeric(i));
    }

    // Builds a hash map with symbolic graphics
    private void buildSymbolicLibrary() {
        // resets the hashmap and sets a blank graphic for value 0
        symbolicDefinitions = new HashMap<Integer, GraphicSymbol>();
        symbolicDefinitions.put(0, new Blank());

        // configure a different graphic for each sudoku value
        symbolicDefinitions.put(1, new OneDot());
        symbolicDefinitions.put(2, new TwoDots());
        symbolicDefinitions.put(3, new ThreeDots());
        symbolicDefinitions.put(4, new FourDots());
        symbolicDefinitions.put(5, new UpTriangle());
        symbolicDefinitions.put(6, new DownTriangle());
        symbolicDefinitions.put(7, new Star());
        symbolicDefinitions.put(8, new UpArrow());
        symbolicDefinitions.put(9, new DownArrow());
        symbolicDefinitions.put(10, new Cross());
        symbolicDefinitions.put(11, new Ring());
        symbolicDefinitions.put(12, new CircleX());
    }
}

/**
 * All graphics implement this interface for drawing withing the given draw area.
 */
interface GraphicSymbol {
    public void draw(Graphics g,  Dimension drawArea);
}

/**
 * Draws nothing. Used for Sudoku value 0.
 */
class Blank implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {}
}

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

/**
 * draws four dots
 */
class FourDots implements GraphicSymbol {
    /**
     * Draws four dots centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int width = drawArea.width / 4;
        int height = drawArea .height / 4;

        // all four dots use a combination of these 4 axis.
        int x1 = drawArea.width / 6 * 2 - (width / 2);
        int x2 = drawArea.width / 6 * 4 - (width / 2);
        int y1 = drawArea.height / 6 * 2 - (height / 2);
        int y2 = drawArea.height / 6 * 4 - (height / 2);

        g.fillOval(x1, y1, width, height);
        g.fillOval(x2, y1, width, height);
        g.fillOval(x1, y2, width, height);
        g.fillOval(x2, y2, width, height);
    }
}

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

/**
 * Draws a triangle pointing down
 */
class DownTriangle implements GraphicSymbol {
    /**
     * Draws a triangle pointing down centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        // Three points of the triangle
        int[] xPoints = new int[] {widthUnit, 5 * widthUnit, 3 * widthUnit};
        int[] yPoints = new int[] { heightUnit, heightUnit, 5 * heightUnit};

        g.fillPolygon(xPoints, yPoints, xPoints.length);
    }
}


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

/**
 * draws an up arrow
 */
class UpArrow implements GraphicSymbol {
    /**
     * Draws an arrow pointing up centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        // Set the line to be wider
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));

        // Arrow is made of these three lines
        g2d.drawLine(3 * widthUnit, heightUnit, 3 * widthUnit, 5 * heightUnit );
        g2d.drawLine(3 * widthUnit, heightUnit, 4 * widthUnit, 3 * heightUnit);
        g2d.drawLine(3 * widthUnit, heightUnit, 2 * widthUnit, 3 * heightUnit);
    }
}

/**
 * draws a down arrow
 */
class DownArrow implements GraphicSymbol {
    /**
     * Draws an arrow pointing down centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        // Makes the line thicker
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));

        // Arrow is made of three lines
        g2d.drawLine(3 * widthUnit, 5 * heightUnit, 3 * widthUnit, heightUnit);
        g2d.drawLine(3 * widthUnit, 5 * heightUnit, 4 * widthUnit, 3 * heightUnit);
        g2d.drawLine(3 * widthUnit, 5 * heightUnit, 2 * widthUnit, 3 * heightUnit);
    }
}

/**
 * draws a cross
 */
class Cross implements GraphicSymbol {
    /**
     * Draws a cross centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        // Make the line thicker
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(4));

        // Made of 2 lines
        g2d.drawLine(widthUnit * 3, heightUnit, widthUnit * 3, heightUnit * 5);
        g2d.drawLine(widthUnit * 2, heightUnit * 2, widthUnit * 4, heightUnit * 2);
    }
}

/**
 * draws a ring
 */
class Ring implements GraphicSymbol {
    /**
     * Draws a triangle pointing up centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        Graphics2D g2d = (Graphics2D)g;

        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;
         // draws a circle with a thick line
        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval(widthUnit, heightUnit, widthUnit * 4, heightUnit * 4);
    }
}

/**
 * draws a circle with an X over it
 */
class CircleX implements GraphicSymbol {
    /**
     * Draws a triangle pointing up centered and scaled within the given area.
     * @param g graphics to use for drawing.
     * @param drawArea drawing area is between (0,0) and this dimension
     */
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        // Makes the lines thicker
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));

        // draws a circle and two lines
        g2d.drawOval(widthUnit, heightUnit, widthUnit * 4, heightUnit * 4);
        g2d.drawLine(widthUnit, heightUnit, 5 * widthUnit, 5 * heightUnit);
        g2d.drawLine(5 * widthUnit, heightUnit, widthUnit, 5 * heightUnit);
    }
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


