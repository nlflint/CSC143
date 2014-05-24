package SudokuGraphics;

import java.util.HashMap;
import java.util.Map;

/**
 * A lookup table for getting classes to draw the symbols.
 */
public class GraphicsLibrary {
    //This is a singleton
    private static GraphicsLibrary instance;

    /**
     * Gets an instance of the graphics library (singleton)
     * @return the instance of the graphics library
     */
    public static GraphicsLibrary getInstance() {
        if (instance == null)
            instance = new GraphicsLibrary();
        return instance;
    }

    // Which set of symbols to use. numbers or pictures.
    public enum GraphicsType {
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
    private GraphicsLibrary() {
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
