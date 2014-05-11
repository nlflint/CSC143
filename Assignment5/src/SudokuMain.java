import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by nate on 5/10/14.
 */
public class SudokuMain {
    private JFrame applicationWindow;
    private JFrame previousApplicationWindow;
    private SudokuBase model;
    private JPanel setupModePanel;

    public SudokuMain() {

        createNewGame(3, 3);
        setupInitialBoardValues();
        changeToPlayMode();
}

    private void createNewGame(int rows, int columns) {
        applicationWindow = createApplicationWindow();
        initializeBoardComponents(applicationWindow, rows, columns);
        createSetupModeComponents(applicationWindow);
        applicationWindow.pack();
        applicationWindow.setVisible(true);
    }

    private void setupInitialBoardValues() {
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

    private void createSetupModeComponents(JFrame window) {
        setupModePanel = new JPanel(new BorderLayout());

        JLabel setupModeText = new JLabel("Setup Mode");
        setupModeText.setFont(new Font("Helvitica", Font.BOLD, 24));
        setupModeText.setForeground(Color.red);
        setupModeText.setHorizontalAlignment(SwingConstants.CENTER);
        setupModePanel.add(setupModeText, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();

        JButton cancelGivens = new JButton("Cancel");
        cancelGivens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applicationWindow.setVisible(false);
                previousApplicationWindow.setVisible(true);
                applicationWindow = previousApplicationWindow;
                previousApplicationWindow = null;
            }
        });
        buttonsPanel.add(cancelGivens);

        JButton setGivens = new JButton("Set Givens");
        setGivens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeToPlayMode();
            }
        });
        buttonsPanel.add(setGivens);

        setupModePanel.add(buttonsPanel, BorderLayout.SOUTH);
        window.add(setupModePanel, BorderLayout.SOUTH);
    }

    private void changeToPlayMode() {
        model.fixGivens();
        setupModePanel.setVisible(false);
        applicationWindow.pack();
    }

    private void initializeBoardComponents(JFrame window, int rows, int columns) {
        model = createSudokuModel(rows, columns);
        SudokuView view = createSudokuView(model);
        SudokuController controller = createSudokuController(model, view);

        window.add(controller, BorderLayout.NORTH);
        window.add(view);
    }

    private JFrame createApplicationWindow() {
        JFrame applicationWindow = new JFrame();
        applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenu(applicationWindow);
        return applicationWindow;

}

    private void createMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        window.setJMenuBar(menubar);

        JMenu filemenu = new JMenu("File");
        menubar.add(filemenu);

        JMenuItem newCmd = new JMenuItem("New Game");
        filemenu.add(newCmd);
        newCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promptNewGame();
            }
        });

        JMenuItem quitcmd = new JMenuItem("Quit");
        filemenu.add(quitcmd);
        quitcmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });

    }

    private void quit()
    {
        final int SUCCESS_EXIT_CODE = 0;
        System.exit(SUCCESS_EXIT_CODE);
    }

    private void promptNewGame() {
        NewGameDialog dialog = new NewGameDialog(applicationWindow);
        if (dialog.okWasClicked()) {
            applicationWindow.setVisible(false);
            previousApplicationWindow = applicationWindow;
            createNewGame(dialog.getRows(), dialog.getColumns());
        }


    }

    private SudokuBase createSudokuModel(int rows, int columns) {
        return new SudokuBoard(rows, columns);
    }

    private SudokuView createSudokuView(SudokuBase base)
    {
        SudokuView sudokuView = new SudokuView(base);
        sudokuView.setSelected(0,0);
        return sudokuView;
    }

    private SudokuController createSudokuController(SudokuBase base, SudokuView sudokuView)
    {
        return new SudokuController(base, sudokuView);
    }

    public static void main(String[] args)
    {
        new SudokuMain();
    }

}

/**
 * Created by nate on 5/10/14.
 */
class SudokuController extends JPanel {
    public SudokuController(SudokuBase model, SelectedCell view) {
        int size = model.size;

        for (int sudokuValue = 0; sudokuValue <= size; sudokuValue++)
            add(new ControlButton(sudokuValue, model, view));

        GridLayout grid = new GridLayout(1, size + 1);
        grid.setHgap(2);
        grid.setHgap(2);
        setLayout(grid);

        addKeyListener(new SudokuValueHandler(view, model));
        setFocusable(true);
        requestFocusInWindow();
    }
}

class ControlButton extends JPanel implements MouseListener {
    // Background colors
    private final Color standardBackgroundColor;
    private final Color highlightedBackgroundColor;
    private final Color activeBackgroundColor;

    private static boolean mouseCanBeReleased;

    private int sudouValue;
    private SudokuBase model;
    private GraphicsLibrary graphicsLibrary;
    private SelectedCell view;

    public ControlButton(int sudokuValue, SudokuBase model, SelectedCell view) {
        //Set background colors
        standardBackgroundColor = new Color(238, 238, 238);
        highlightedBackgroundColor = Color.yellow;
        activeBackgroundColor = Color.red;

        this.sudouValue = sudokuValue;
        this.model = model;
        this.view = view;
        this.graphicsLibrary = GraphicsLibrary.getInstance();
        setPreferredSize(new Dimension(40,40));
        setBackground(standardBackgroundColor);
        setBorder(new BorderUIResource.LineBorderUIResource(Color.black));
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        GraphicSymbol graphics = graphicsLibrary.getGraphic(sudouValue);
        graphics.draw(g, getSize());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        setBackground(activeBackgroundColor);
        mouseCanBeReleased = true;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Do nothing if mouse was dragged after button was pressed down.
        if (!mouseCanBeReleased)
            return;

        int selectedRow = view.getSelectedRow();
        int selectedColumn = view.getSelectedColumn();

        if (!model.isGiven(selectedRow, selectedColumn))
            model.setValue(selectedRow, selectedColumn, sudouValue);
        else
            java.awt.Toolkit.getDefaultToolkit().beep();

        setBackground(highlightedBackgroundColor);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
            setBackground(highlightedBackgroundColor);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(standardBackgroundColor);
        mouseCanBeReleased = false;
    }
}

class NewGameDialog extends JDialog implements ActionListener {

    private int rows;
    private int columns;

    private boolean okClicked;

    private JTextField rowsInput;
    private JTextField columnsInput;

    private JButton ok;
    private JButton cancel;

    public NewGameDialog(JFrame owner) {
        super(owner, "New Game", true);

        rowsInput = new JTextField("3", 3);

        JPanel rowsPanel = new JPanel();
        rowsPanel.add(new JLabel("Rows: "));
        rowsPanel.add(rowsInput);


        columnsInput = new JTextField("3", 3);

        JPanel columnsPanel = new JPanel();
        columnsPanel.add(new JLabel("Columns: "));
        columnsPanel.add(columnsInput);


        JPanel inputsPanel = new JPanel();
        inputsPanel.add(rowsPanel);
        inputsPanel.add(columnsPanel);
        add(inputsPanel);

        JPanel buttonsPanel = new JPanel();
        ok = new JButton("OK");
        ok.addActionListener(this);
        buttonsPanel.add(ok);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttonsPanel.add(cancel);


        add(buttonsPanel, BorderLayout.SOUTH);

        setSize(300, 100);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if(src == ok) {
            okPressed();
        } else if(src == cancel) {
            cancelPressed();
        }
    }

    private void okPressed() {
        try {
            //try to parse, if failed, there will be an illegal value.
            rows = Integer.parseInt(rowsInput.getText());
            columns = Integer.parseInt(columnsInput.getText());


            if (inputIsValid(rows, columns)) {
                okClicked = true;
                dispose();
            } else {
                showWrongInputMessage();
            }
        } catch (Exception ex) {
            showWrongInputMessage();
        }
    }


    private void cancelPressed() {
        okClicked = false;
        dispose();
    }

    private boolean inputIsValid(int rows, int columns) {
        return rows > 0 && columns > 0 && (rows * columns) < 13;
}

    private void showWrongInputMessage() {
        String errorLineOne = "Rows and Columns must be positive whole numbers that\n";
        String errorLineTwo = "when multiplied together are greater than 0 and less than 13.";
        JOptionPane.showMessageDialog(null, errorLineOne + errorLineTwo);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean okWasClicked() {
        return okClicked;
    }

}

class SudokuValueHandler extends KeyAdapter {
    // The selectedCell class will be used to change the selected cell
    SelectedCell view;
    SudokuBase model;

    /**
     * Constructor holds a reference to the selected cell object
     * @param view the selected cell object
     */
    public SudokuValueHandler(SelectedCell view, SudokuBase model) {
        this.model = model;
        this.view = view;
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

        int invalidValue = -1;
        int value = invalidValue;

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

        if (value > invalidValue && value <= model.size)
            if (!model.isGiven(selectedRow, selectedColumn))
                model.setValue(selectedRow, selectedColumn, value);
            else
                java.awt.Toolkit.getDefaultToolkit().beep();
    }
}