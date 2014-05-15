import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Stack;

/**
 * Main class for the sudoku program. Ties together the view and model with a controller.
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 5: Sudoku Input Handling
 */
public class SudokuMain {
    // Contains the active game window, and previous active games windows.
    private JFrame applicationWindow;
    private Stack<JFrame> previousApplicationWindows;

    /**
     * Constructor. Sets up and initial game window.
     */
    public SudokuMain() {
        // Initializes the stack of previous games windows.
        previousApplicationWindows = new Stack<JFrame>();
        // Creates a new 3x3 game and by-passes the Setup mode.
        createNewGame(3, 3, true);
    }

    // Creates JFrames with everything connected in a playable game
    private void createNewGame(int rows, int columns, boolean bypassSetupMode) {
        // Creates model, view and controllers and ties them together.
        SudokuBase model = new SudokuBoard(rows, columns);
        SudokuView view = new SudokuView(model);
        view.setSelected(0,0);
        SudokuController controller = new SudokuController(model, view);
        SetGivensViewController setGivensViewController = new SetGivensViewController(model, this);

        // Creates the JFrame with view and controllers
        JFrame window = createApplicationWindow(view, controller, setGivensViewController);
        // promotes this new window to the active window.
        promoteGameWindow(window);

        // This is where setup mode can be bypassed and default values are used instead.
        if (bypassSetupMode){
            setupInitialBoardValues(model);
            setGivensViewController.setVisible(false);
        }

        // Creates a key listener for the new window
        addKeyListener(window, view, model, controller);

        // Finalizes the layout and shows the window.
        window.pack();
        window.setVisible(true);

    }

    // Adds a new key listener to the window.
    private void addKeyListener(JFrame window, SudokuView view, SudokuBase model, SudokuController controller) {
        window.addKeyListener(new SudokuValueHandler(view, model, controller));
        window.setFocusable(true);
        window.requestFocusInWindow();
    }

    private void promoteGameWindow(JFrame window) {
        // If there is a currently active window the save it's reference in case users cancel Setup mode
        if (applicationWindow != null) {
            applicationWindow.setVisible(false);
            previousApplicationWindows.add(applicationWindow);
        }
        // promotes to currently active window
        applicationWindow = window;
    }

    // Sets up given model with some initial values
    private void setupInitialBoardValues(SudokuBase model) {
        model.setValue(0, 3, 6);
        model.setValue(0, 5, 1);
        model.setValue(1, 2, 4);
        model.setValue(1, 4, 5);
        model.setValue(1, 5, 3);
        model.setValue(2, 3, 3);
        model.setValue(3, 2, 6);
        model.setValue(4, 0, 2);
        model.setValue(4, 1, 3);
        model.setValue(4, 3, 1);
        model.setValue(5, 0, 6);
        model.setValue(5, 2, 1);
        model.fixGivens();
    }

    // Creates a JFrame for the game and populates it with given views and controllers.
    private JFrame createApplicationWindow(SudokuView view,
                                           SudokuController controller,
                                           SetGivensViewController setGivensViewController) {
        JFrame applicationWindow = new JFrame();
        applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationWindow.add(controller, BorderLayout.NORTH);
        applicationWindow.add(view);
        applicationWindow.add(setGivensViewController, BorderLayout.SOUTH);

        // Creates the file drop down menu.
        createMenu(applicationWindow);

        return applicationWindow;
    }

    // Creates a menu for the given window
    private void createMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        window.setJMenuBar(menubar);

        JMenu filemenu = new JMenu("File");
        filemenu.setMnemonic('f');
        menubar.add(filemenu);

        // The new game option lets users start a new game
        JMenuItem newCmd = new JMenuItem("New Game");
        newCmd.setMnemonic('n');
        filemenu.add(newCmd);
        newCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promptNewGame();
            }
        });

        // Creates the quiz menu option
        JMenuItem quitcmd = new JMenuItem("Quit");
        quitcmd.setMnemonic('q');
        filemenu.add(quitcmd);
        quitcmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
    }

    // Quites the game
    private void quit() {
        final int SUCCESS_EXIT_CODE = 0;
        System.exit(SUCCESS_EXIT_CODE);
    }

    // Creates a new game
    private void promptNewGame() {
        // prompt user for dimensions of new game board.
        NewGameDialog dialog = new NewGameDialog(applicationWindow);
        if (dialog.okWasClicked()) {
            // users clicked ok so create a new game, and DONT bypass setup mode.
            createNewGame(dialog.getRows(), dialog.getColumns(), false);
        }
    }

    /**
     * Hides setup mode, and reloads last running game.
     */
    void cancelSetupMode() {
        applicationWindow.setVisible(false);
        applicationWindow = previousApplicationWindows.pop();
        applicationWindow.setVisible(true);
    }

    /**
     * Packs the current active window, use after givens controller disappears.
     */
    void packActiveWindow() {
        applicationWindow.pack();
    }

    /**
     * Application method. Starts the sudoku program.
     * @param args Command line arguments
     */
    public static void main(String[] args)
    {
        new SudokuMain();
    }

}

/**
 * This is the view and controller for setting givens, aka Setup Mode.
 */
class SetGivensViewController extends JPanel {
    // Used to cancel setup mode
    private SudokuMain applicationMain;
    // Model is used to set givens.
    private SudokuBase model;

    /**
     * Constructor.
     * @param sudokuModel Givens will be fixed on this model
     * @param main This sudoku main class used for canceling setup mode
     */
    public SetGivensViewController(SudokuBase sudokuModel, SudokuMain main) {
        super();
        // Save variables
        this.applicationMain = main;
        this.model = sudokuModel;

        // Initializes the layout, buttons and labels.
        setLayout(new BorderLayout());

        JLabel setupModeText = new JLabel("Setup Mode");
        setupModeText.setFont(new Font("Helvitica", Font.BOLD, 24));
        setupModeText.setForeground(Color.red);
        setupModeText.setHorizontalAlignment(SwingConstants.CENTER);
        add(setupModeText, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();

        // Creates cancel button
        JButton cancelGivens = new JButton("Cancel");
        cancelGivens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applicationMain.cancelSetupMode();

            }
        });
        buttonsPanel.add(cancelGivens);

        // Creates set givens button
        JButton setGivens = new JButton("Set Givens");
        setGivens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.fixGivens();
                setVisible(false);
                applicationMain.packActiveWindow();
            }
        });
        buttonsPanel.add(setGivens);

        add(buttonsPanel, BorderLayout.SOUTH);
    }
}

/**
 * Controller for adding values to the sudoku board.
 */
class SudokuController extends JPanel {
    // Holds a reference to each conrol button. Used to flash the background color when a key is pressed.
    private HashMap<Integer, ControlButton> controlButtons;
    // Keyboard key that actives each  button
    private HashMap<Integer, String> keyboardKeys;

    /**
     * Constructor.
     * @param model Model that will be changed by this controller
     * @param view Where the active cell will be read from.
     */
    public SudokuController(SudokuBase model, SelectedCell view) {
        super();
        keyboardKeys = createKeyboardMappings();
        controlButtons = new HashMap<Integer, ControlButton>();

        int size = model.size;

        // Creates control buttons for inputing sudoku values
        for (int sudokuValue = 0; sudokuValue <= size; sudokuValue++)
        {
            ControlButton button = new ControlButton(sudokuValue, model, view, keyboardKeys.get(sudokuValue));
            add(button);
            controlButtons.put(sudokuValue,button);
        }

        // Layout buttons in a nice row.
        GridLayout grid = new GridLayout(1, size + 1);
        grid.setHgap(2);
        grid.setHgap(2);
        setLayout(grid);
    }

    private HashMap<Integer, String> createKeyboardMappings() {
        // All the keyboard mappings.
        HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
        hashmap.put(0,"0");
        hashmap.put(1,"1");
        hashmap.put(2,"2");
        hashmap.put(3,"3");
        hashmap.put(4,"4");
        hashmap.put(5,"5");
        hashmap.put(6,"6");
        hashmap.put(7,"7");
        hashmap.put(8,"8");
        hashmap.put(9,"9");
        hashmap.put(10,"A");
        hashmap.put(11,"B");
        hashmap.put(12,"C");
        return hashmap;
    }

    /**
     * Flashes the background color of the given value on the controller.
     * @param value value to flash the active color
     */
    public void flashActive(int value) {
        controlButtons.get(value).flashActive();
    }
}

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
            java.awt.Toolkit.getDefaultToolkit().beep();

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
                // save original background color
                Color originalBackgroundColor = getBackground();

                setBackground(activeBackgroundColor);
                repaint();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) { }

                // Restore the original background color.
                setBackground(originalBackgroundColor);
            }
        });
        thread.start();

    }
}

/**
 * A dialog for for getting dimensions when creating a new game.
 */
class NewGameDialog extends JDialog implements ActionListener {

    // Values gathered by user are stored here.
    private int rows;
    private int columns;

    // true if ok clicked, false if cancel was clicked.
    private boolean okClicked;

    // Fields in the dialog.
    private JTextField rowsInput;
    private JTextField columnsInput;

    private JButton ok;
    private JButton cancel;

    /**
     * Contructor.
     * @param owner The owner from which the dialog is displayed
     */
    public NewGameDialog(JFrame owner) {
        super(owner, "New Game", true);

        // row input
        rowsInput = new JTextField("3", 3);
        JPanel rowsPanel = new JPanel();
        rowsPanel.add(new JLabel("Rows: "));
        rowsPanel.add(rowsInput);

        // column input
        columnsInput = new JTextField("3", 3);
        JPanel columnsPanel = new JPanel();
        columnsPanel.add(new JLabel("Columns: "));
        columnsPanel.add(columnsInput);

        // sub-panel for the input windows and labels.
        JPanel inputsPanel = new JPanel();
        inputsPanel.add(rowsPanel);
        inputsPanel.add(columnsPanel);
        add(inputsPanel);

        // sub-panel for buttons
        JPanel buttonsPanel = new JPanel();

        // ok button
        ok = new JButton("OK");
        ok.addActionListener(this);
        buttonsPanel.add(ok);

        // Cancel button
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttonsPanel.add(cancel);


        add(buttonsPanel, BorderLayout.SOUTH);

        setSize(300, 100);
        setVisible(true);
        pack();
    }

    /**
     * Handles button clicks for ok and cancel buttons
     * @param e event information
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // determines is ok or cancel was clicked
        if(src == ok) {
            okPressed();
        } else if(src == cancel) {
            cancelPressed();
        }
    }

    // Ok was pressed. validate inputs.
    private void okPressed() {
        try {
            //try to parse
            rows = Integer.parseInt(rowsInput.getText());
            columns = Integer.parseInt(columnsInput.getText());

            // validate values
            if (!rowIsValid(rows))
                showInvalidRowMessage();
            else if (!columnisValid(columns))
                showInvalidColumnMessage();
            else if (!inputSizeValid(rows * columns))
                // Values are unacceptable, asks for good values
                showWrongSizeMessage();
            else {
                okClicked = true;
                dispose();
            }
        } catch (Exception ex) {
            // Parsing failed, values are unacceptable. asks for good values
            showWrongInputMessage();
        }
    }

    private void showWrongSizeMessage() {
        String errorLineOne = "Row times columns must be greater than 0 and less than 13.";
        JOptionPane.showMessageDialog(null, errorLineOne);
    }

    private void showInvalidColumnMessage() {
        String errorLineOne = "Column must be greater than 0 and less than 5.";
        JOptionPane.showMessageDialog(null, errorLineOne);
    }

    private boolean columnisValid(int columns) {
        return columns > 0 && columns < 5;
    }

    private void showInvalidRowMessage() {
        String errorLineOne = "Row must be greater than 0 and less than 5.";
        JOptionPane.showMessageDialog(null, errorLineOne);
    }

    // Validates row
    private boolean rowIsValid(int rows) {
        return rows > 0 && rows < 5;
    }

    // Cancel was pressed. Close the window and indicate that OK was NOT clicked.
    private void cancelPressed() {
        okClicked = false;
        dispose();
    }

    // Validates given row and column
    private boolean inputSizeValid(int size) {
        return size > 0 && size < 13;
    }

    // Shows a dialog that asks for good input
    private void showWrongInputMessage() {
        String errorLineOne = "Rows and Columns must be positive whole numbers that\n";
        String errorLineTwo = "when multiplied together are greater than 1 and less than 13.";
        JOptionPane.showMessageDialog(null, errorLineOne + errorLineTwo);
    }

    /**
     * Get rows input by user
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get Columns input by the user
     * @return
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Determine if OK was clicked.
     * @return true is OK was clicked. False if ok was NOT clicked.
     */
    public boolean okWasClicked() {
        return okClicked;
    }

}

/**
 * The keyboard listener for a sudoku game.
 */
class SudokuValueHandler extends KeyAdapter {
    // The selectedCell class will be used to change the selected cell
    private SelectedCell view;
    private SudokuBase model;
    private SudokuController controller;

    /**
     * Constructor holds a reference to the selected cell object
     * @param view the selected cell object
     */
    public SudokuValueHandler(SelectedCell view, SudokuBase model, SudokuController controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    /**
     * This method is executed everytime a key is pressed.
     * @param e event information
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Get the currently selected column and row
        int selectedRow = view.getSelectedRow();
        int selectedColumn = view.getSelectedColumn();

        // Start with an invalid value. Later this value is checked to see if it changed.
        int invalidValue = -1;
        int value = invalidValue;

        // Idenfiy the key that was pressed and take action or set the value.
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
            case KeyEvent.VK_0:
                value = 0;
                break;
            case KeyEvent.VK_1:
                value = 1;
                break;
            case KeyEvent.VK_2:
                value = 2;
                break;
            case KeyEvent.VK_3:
                value = 3;
                break;
            case KeyEvent.VK_4:
                value = 4;
                break;
            case KeyEvent.VK_5:
                value = 5;
                break;
            case KeyEvent.VK_6:
                value = 6;
                break;
            case KeyEvent.VK_7:
                value = 7;
                break;
            case KeyEvent.VK_8:
                value = 8;
                break;
            case KeyEvent.VK_9:
                value = 9;
                break;
            case KeyEvent.VK_A:
                value = 10;
                break;
            case KeyEvent.VK_B:
                value = 11;
                break;
            case KeyEvent.VK_C:
                value = 12;
                break;
        }

        // If value is valid, update the model.
        if (value > invalidValue && value <= model.size)
            // only update values that are not givens.
            if (!model.isGiven(selectedRow, selectedColumn)) {
                model.setValue(selectedRow, selectedColumn, value);
                controller.flashActive(value);
            }
            else
                // Value was a given, so beep.
                java.awt.Toolkit.getDefaultToolkit().beep();
    }
}