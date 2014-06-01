package Controllers;

import Model.SudokuBase;
import SudokuGraphics.GraphicSymbol;
import SudokuGraphics.GraphicsLibrary;
import View.SelectedCell;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Represents a control button for inputting a single sudoku value.
 */
class ControlButton extends JPanel implements MouseListener {
    // Background color constants
    private final Color standardBackgroundColor;
    private final Color highlightedBackgroundColor;
    private final Color activeBackgroundColor;

    // flag is used to fix a mouse draggin while clicking bug.
    private static boolean mousePointerExitedAfterPress;

    // The value that this sudoku button inputs
    private int sudouValue;
    // The model to update when this button is clicked
    private SudokuBase model;
    // The library of graphics that this button uses to draw itself.
    private GraphicsLibrary graphicsLibrary;
    // Active playspace is read from this veiw
    private SelectedCell view;
    // Keyboardkey that actives this button
    private String keyboardKey;

    /**
     * Constructor. Sets up the button.
     * @param sudokuValue value of this sudoku button
     * @param model model that will be updated when this button is clicked
     * @param view the view where the active cell is identified.
     */
    public ControlButton(int sudokuValue, SudokuBase model, SelectedCell view, String keyboardKey) {
        //Set background colors
        standardBackgroundColor = new Color(238, 238, 238);
        highlightedBackgroundColor = Color.yellow;
        activeBackgroundColor = Color.red;

        // Saves input parameters
        this.sudouValue = sudokuValue;
        this.model = model;
        this.view = view;
        this.keyboardKey = keyboardKey;

        // Get an instance of the singleton graphics library
        this.graphicsLibrary = GraphicsLibrary.getInstance();

        setPreferredSize(new Dimension(40, 40));
        setBackground(standardBackgroundColor);
        setBorder(new BorderUIResource.LineBorderUIResource(Color.black));

        addMouseListener(this);
    }

    /**
     * Overrides default paint method. Draws the value.
     * @param g the object will be used for drawing
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Lookup the graphic using the value
        GraphicSymbol graphics = graphicsLibrary.getGraphic(sudouValue);
        // Draw the graphic
        graphics.draw(g, getSize());
        g.setFont(new Font("Helvitica", Font.PLAIN, 9));
        g.drawString(keyboardKey,(getWidth() - 10), 10);
    }

    /**
     * Not used.
     * @param e event information
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * When mouse is pressed, then background changes to active color and a flag is set.
     * @param e event information
     */
    @Override
    public void mousePressed(MouseEvent e) {
        setBackground(activeBackgroundColor);
        mousePointerExitedAfterPress = false;

    }

    /**
     * Updates selected cell with value that was clicked.
     * @param e event information
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Do nothing if mouse was dragged outside this JPanel after button was pressed down.
        if (mousePointerExitedAfterPress)
            return;

        // Get selected row and columns
        int selectedRow = view.getSelectedRow();
        int selectedColumn = view.getSelectedColumn();

        // Only set values that are not givens
        if (!model.isGiven(selectedRow, selectedColumn))
            model.setValue(selectedRow, selectedColumn, sudouValue);
        else
            Toolkit.getDefaultToolkit().beep();

        setBackground(highlightedBackgroundColor);
    }

    /**
     * Highlights cell when mouse pointer enters the jpanel
     * @param e event information
     */
    @Override
    public void mouseEntered(MouseEvent e) {
            setBackground(highlightedBackgroundColor);
    }

    /**
     * Un-highlights jpanel when mouse exits and sets a flag that indicates the mouse has exited.
     * @param e event information
     */
    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(standardBackgroundColor);
        mousePointerExitedAfterPress = true;
    }

    /**
     * Flashes the active background color for 200 ms
     */
    public void flashActive() {
        // Need to make a new thread so the background update is visible.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                setBackground(activeBackgroundColor);
                repaint();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) { }

                // Restore the original background color.
                setBackground(standardBackgroundColor);
            }
        });
        thread.start();

    }
}
