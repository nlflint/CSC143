import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nate on 5/1/14.
 */
public class SudokuView extends JPanel
        implements SelectedCell, NumericSupport {

    private SudokuBase base;
    private boolean usesNumericGraphics;
    private GraphicsLibrary graphicsLibrary;
    private PlaySpace[][] playSpaces;
    private PlaySpace selectedPlaySpace;

    public SudokuView(SudokuBase base)
    {
        this.base = base;
        setBackground(Color.white);
        playSpaces = new PlaySpace[base.size][base.size];

        // Setup graphics
        usesNumericGraphics = true;
        graphicsLibrary = new GraphicsLibrary();

        // Set spacing around entire board.
        Border line = new BorderUIResource.LineBorderUIResource(Color.black, 1);
        Border empty = new BorderUIResource.EmptyBorderUIResource(2,2,2,2);
        setBorder(new BorderUIResource.CompoundBorderUIResource(line, empty));

        // Create playing squares
        createPlaySpaces(base);

        // show everything
        setVisible(true);

    }

    private void createPlaySpaces(SudokuBase base) {
        int size = base.rows * base.columns;
        GridLayout layout = new GridLayout(size,size);
        layout.setVgap(2);
        layout.setHgap(2);
        setLayout(layout);

        for (int row = 0; row < size; row++)
        {
            for (int column = 0; column < size; column++)
            {
                PlaySpace playSpace = new PlaySpace(base, graphicsLibrary, row, column);
                playSpace.setBackground(getRegionBackgroundColor(row, column));
                playSpaces[row][column] = playSpace;
                add(playSpace);
            }
        }
    }

    private Color getRegionBackgroundColor(int row, int column)
    {
        int numberRegionsHorizontally = base.size / base.columns;
        int numberRegionsVertically = base.size / base.rows;

        int regionColumn = column / numberRegionsHorizontally;
        int regionRow = row / numberRegionsVertically;

        boolean regionColumnEven = regionColumn % 2 == 0;
        boolean regionRowEven = regionRow % 2 == 0;

        if (regionColumnEven && regionRowEven
                || !regionColumnEven && !regionRowEven)
            return Color.white;

        return Color.lightGray;



    }

    @Override
    public void setSelected(int row, int col)
    {
        if (selectedPlaySpace != null)
            selectedPlaySpace.setBackground(getRegionBackgroundColor(getSelectedRow(), getSelectedColumn()));

        selectedPlaySpace = playSpaces[row][col];
        selectedPlaySpace.setBackground(Color.yellow);
    }

    @Override
    public int getSelectedRow()
    {
        if (selectedPlaySpace == null)
            return 0;
        return selectedPlaySpace.row;
    }

    @Override
    public int getSelectedColumn()
    {
        if (selectedPlaySpace == null)
            return 0;
        return selectedPlaySpace.column;
    }

    @Override
    public void setNumeric(boolean flag)
    {
        if (flag)
            graphicsLibrary.setGraphicType(GraphicsLibrary.GraphicType.Numeric);
        else
            graphicsLibrary.setGraphicType(GraphicsLibrary.GraphicType.Symbolic);

    }

    @Override
    public boolean showsNumeric() {
        return graphicsLibrary.getGraphicType() == GraphicsLibrary.GraphicType.Numeric;
    }
}

class PlaySpace extends JPanel {
    SudokuBase base;
    int row, column;
    GraphicsLibrary library;

    public PlaySpace(SudokuBase base, GraphicsLibrary library, int row, int column)
    {
        this.base = base;
        this.row = row;
        this.column = column;
        this.library = library;
        setPreferredSize(new Dimension(30, 30));
        setBorder(new BorderUIResource.LineBorderUIResource(Color.black));
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int value = base.getValue(row, column);

        Color givenColor = base.isGiven(row, column) ? Color.black : new Color(114, 221, 114);
        g.setColor(givenColor);

        GraphicSymbol graphic = library.getGraphic(value);
        Dimension drawingArea = new Dimension(getWidth(), getHeight());
        graphic.draw(g, drawingArea);

    }
}

class GraphicsLibrary {
    private Map<Integer, GraphicSymbol> symbolDefinitions;

    GraphicType graphicType;

    public GraphicsLibrary()
    {
        setGraphicType(GraphicType.Symbolic);
    }

    public void setGraphicType(GraphicType type)
    {
        graphicType = type;

        if (graphicType.equals(GraphicType.Numeric))
            buildGraphicsLibraryWithNumbers();
        else
            buildGraphicsLibraryWithSymbols();
    }

    public GraphicType getGraphicType() { return graphicType; }

    public GraphicSymbol getGraphic(int number)
    {
        return symbolDefinitions.get(number);
    }

    private void buildGraphicsLibraryWithNumbers()
    {
        initializeSymbolDefinitions();

        for (int i = 1; i < 13; i++)
            symbolDefinitions.put(i, new Numeric(i));
    }

    private void initializeSymbolDefinitions()
    {
        symbolDefinitions = new HashMap<Integer, GraphicSymbol>();
        symbolDefinitions.put(0, new Blank());
    }

    private void buildGraphicsLibraryWithSymbols()
    {
        initializeSymbolDefinitions();

        symbolDefinitions.put(1, new OneDot());
        symbolDefinitions.put(2, new TwoDots());
        symbolDefinitions.put(3, new ThreeDots());
        symbolDefinitions.put(4, new FourDots());
        symbolDefinitions.put(5, new UpTriangle());
        symbolDefinitions.put(6, new DownTriangle());
        symbolDefinitions.put(7, new Star());
        symbolDefinitions.put(8, new UpArrow());
        symbolDefinitions.put(9, new DownArrow());
        symbolDefinitions.put(10, new Cross());
        symbolDefinitions.put(11, new Ring());
        symbolDefinitions.put(12, new CircleX());
    }

    enum GraphicType {
        Symbolic,
        Numeric
    }


}

interface GraphicSymbol {
    public void draw(Graphics g,  Dimension drawArea);
}

class Numeric implements GraphicSymbol {

    private int numeral;

    public Numeric(int number) { numeral = number; }

    @Override
    public void draw(Graphics g, Dimension drawArea) {
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(numeral + "", (int) drawArea.getWidth() / 3, (int) (drawArea.getHeight() / 1.5));
    }
}

class Blank implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {}
}

class OneDot implements GraphicSymbol {
    @Override
    public void draw(Graphics g,Dimension drawArea) {
        int width = drawArea.width / 4;
        int height = drawArea .height / 4;
        int x = drawArea.width / 2 - (width / 2);
        int y = drawArea.height / 2 - (height / 2);

        g.fillOval(x, y, width, height);
    }
}

class TwoDots implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int width = drawArea.width / 4;
        int height = drawArea .height / 4;

        int x1 = drawArea.width / 6 * 2 - (width / 2);
        int x2 = drawArea.width / 6 * 4 - (width / 2);
        int y = drawArea.height / 2 - (height / 2);

        g.fillOval(x1, y, width, height);
        g.fillOval(x2, y, width, height);
    }
}

class ThreeDots implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int width = drawArea.width / 4;
        int height = drawArea .height / 4;
        int x1 = drawArea.width / 6 * 2 - (width / 2);
        int x2 = drawArea.width / 6 * 4 - (width / 2);
        int y1 = drawArea.height / 6 * 2 - (height / 2);
        int y2 = drawArea.height / 6 * 4 - (height / 2);

        g.fillOval(x1, y1, width, height);
        g.fillOval(x2, y1, width, height);
        g.fillOval(x2, y2, width, height);
    }
}

class FourDots implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int width = drawArea.width / 4;
        int height = drawArea .height / 4;
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

class UpTriangle implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {

        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        int[] xPoints = new int[] {3 * widthUnit, 5 * widthUnit, widthUnit};
        int[] yPoints = new int[] {heightUnit, 5 * heightUnit, 5 * heightUnit};

        g.fillPolygon(xPoints, yPoints, xPoints.length);
    }
}

class DownTriangle implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        int[] xPoints = new int[] {widthUnit, 5 * widthUnit, 3 * widthUnit};
        int[] yPoints = new int[] { heightUnit, heightUnit, 5 * heightUnit};

        g.fillPolygon(xPoints, yPoints, xPoints.length);
    }
}




class Star implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        int[] xPoints1 = new int[] {3 * widthUnit, 5 * widthUnit, widthUnit};
        int[] yPoints1 = new int[] {heightUnit, 4 * heightUnit, 4 * heightUnit};

        int[] xPoints2 = new int[] {widthUnit, 5 * widthUnit, 3 * widthUnit};
        int[] yPoints2 = new int[] {2 * heightUnit, 2 * heightUnit, 5 * heightUnit};

        g.fillPolygon(xPoints1, yPoints1, xPoints1.length);
        g.fillPolygon(xPoints2, yPoints2, xPoints2.length);
    }
}

class UpArrow implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));

        g2d.drawLine(3 * widthUnit, heightUnit, 3 * widthUnit, 5 * heightUnit );
        g2d.drawLine(3 * widthUnit, heightUnit, 4 * widthUnit, 3 * heightUnit );
        g2d.drawLine(3 * widthUnit, heightUnit, 2 * widthUnit, 3 * heightUnit );
    }
}


class DownArrow implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));

        g2d.drawLine(3 * widthUnit, 5 * heightUnit, 3 * widthUnit, heightUnit);
        g2d.drawLine(3 * widthUnit, 5 * heightUnit, 4 * widthUnit, 3 * heightUnit);
        g2d.drawLine(3 * widthUnit, 5 * heightUnit, 2 * widthUnit, 3 * heightUnit);
    }
}


class Cross implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(4));

        g2d.drawLine(widthUnit * 3, heightUnit, widthUnit * 3, heightUnit * 5);
        g2d.drawLine(widthUnit * 2, heightUnit * 2, widthUnit * 4, heightUnit * 2);
    }
}

class Ring implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        Graphics2D g2d = (Graphics2D)g;

        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval(widthUnit, heightUnit, widthUnit * 4, heightUnit * 4);
    }
}

class CircleX implements GraphicSymbol {
    @Override
    public void draw(Graphics g, Dimension drawArea) {
        int widthUnit = drawArea.width / 6;
        int heightUnit = drawArea.height / 6;

        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));

        g2d.drawOval(widthUnit, heightUnit, widthUnit * 4, heightUnit * 4);
        g2d.drawLine(widthUnit, heightUnit, 5 * widthUnit, 5 * heightUnit);
        g2d.drawLine(5 * widthUnit, heightUnit, widthUnit, 5 * heightUnit);
    }
}

