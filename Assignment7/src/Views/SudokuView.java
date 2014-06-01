package Views;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.util.*;

import Model.SudokuBase;
import SudokuGraphics.*;

/**
 * Renders a sudoku board.
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

    //Panels for grouping componments
    private JPanel playSpacePanel;
    private JPanel statusPanel;

    /**
     * Constructor sets up graphics and binds it to the sudoku board.
     * @param base The sudoku board
     */
    public SudokuView(SudokuBase base) {

        // Save the sudoku board information
        numberRowsInRegion = base.rows;
        numberColumnsInRegion = base.columns;
        boardWidth = base.size;

        configureEdgeBorder(this);

        // Set white background
        setBackground(Color.white);

        // Setup graphics
        graphicsLibrary = GraphicsLibrary.getInstance();

        //configure layout
        setLayout(new BorderLayout());

        // Create playing squares
        createPlaySpaces(base);

        // Create status indicators
        createStatusIndicators(base);

        // Add keyboard listener
        initializeKeyboardListener();

        // Add this view as an observer of the model
        base.addObserver(this);

        // show everything
        setVisible(true);

    }

    // Hides status indicators when setting up a new game
    public void hideIndicators() {
        statusPanel.setVisible(false);
        repaint();
    }

    // Creates row, column and region status indicators along bottom of window
    private void createStatusIndicators(SudokuBase base) {
        statusPanel = new JPanel();
        FlowLayout flowLeftAligned = new FlowLayout(FlowLayout.LEFT);
        statusPanel.setLayout(flowLeftAligned);

        add(statusPanel,BorderLayout.SOUTH);
        // Set spacing around status panel.
        //configureEdgeBorder(playSpacePanel);

        RegionStatusIndicator regionIndicator = new RegionStatusIndicator(base);
        statusPanel.add(regionIndicator);

        ColumnStatusIndicator columnIndicator = new ColumnStatusIndicator(base);
        statusPanel.add(columnIndicator);

        RowStatusIndicator rowIndicator = new RowStatusIndicator(base);
        statusPanel.add(rowIndicator);

    }

    // Creates a boarder around edge of the board
    private void configureEdgeBorder(JPanel panel) {
        Border line = new BorderUIResource.LineBorderUIResource(Color.black, 1);
        Border empty = new BorderUIResource.EmptyBorderUIResource(2,2,2,2);
        panel.setBorder(new BorderUIResource.CompoundBorderUIResource(line, empty));
    }

    // Creates all the play spaces on the board.
    private void createPlaySpaces(SudokuBase base) {
        playSpacePanel = new JPanel();
        add(playSpacePanel);

        // Set spacing around entire board.
        //configureEdgeBorder(playSpacePanel);

        // Initialize an array to save references to the places spaces.
        // Board is always square
        playSpaces = new PlaySpace[boardWidth][boardWidth];

        //setup grid layout with spacing.
        GridLayout layout = new GridLayout(boardWidth, boardWidth);
        layout.setVgap(2);
        layout.setHgap(2);
        playSpacePanel.setLayout(layout);

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
                playSpacePanel.add(playSpace);
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
    /**
     * Used by observable model whenever something changes. Used to redraw everything if model changes.
     */
    public void update(Observable o, Object arg) {
        repaint();
    }
}


