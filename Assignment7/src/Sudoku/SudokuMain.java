package Sudoku;

import Controllers.*;
import Model.*;
import View.SudokuView;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;

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
    private SudokuBoard currentModel;
    private SudokuView currentView;
    private SudokuController currentController;

    /**
     * Constructor. Sets up and initial game window.
     */
    public SudokuMain() {
        // Creates a new 3x3 game and by-passes the Setup mode.
        SudokuBoard model = new SudokuBoard(3,3);
        setupInitialBoardValues(model);

        // Attaches the model to the window
        attachModel(model);
    }

    // Creates JFrames with everything connected in a playable game
    private void attachModel(SudokuBoard model) {
        // Create window if one doesn't exist yet
        if (applicationWindow == null)
            createApplicationWindow();

        SudokuView view = new SudokuView(model);
        view.setSelected(0,0);
        SudokuController controller = new SudokuController(model, view);

        removeOldComponents();
        addNewCompoents(view, controller);

        // Creates a key listener for the new window
        removeAllKeyListerns();
        addKeyListener(model, view , controller);

        // Finalizes the layout and shows the window.
        applicationWindow.pack();
        applicationWindow.setVisible(true);

        // Save reference
        currentModel = model;
        currentController = controller;
        currentView = view;

    }

    private void addNewCompoents(SudokuView view, SudokuController controller) {
        applicationWindow.add(controller, BorderLayout.NORTH);
        applicationWindow.add(view);
    }

    private void removeOldComponents() {
        if (currentController != null)
            applicationWindow.remove(currentController);
        if (currentView != null)
            applicationWindow.remove(currentView);
    }

    // Sets up given model with some initial values
    private void setupInitialBoardValues(SudokuBoard model) {
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

    // Adds a new key listener to the window.
    private void addKeyListener(SudokuBase model, SudokuView view, SudokuController controller) {
        applicationWindow.addKeyListener(new SudokuValueHandler(view, model, controller));
        applicationWindow.setFocusable(true);
        applicationWindow.requestFocusInWindow();
    }

    private void removeAllKeyListerns() {
        //remove all existing key listerns
        KeyListener[] listeners = applicationWindow.getKeyListeners();
        for (KeyListener listener : listeners)
            applicationWindow.removeKeyListener(listener);
    }

    // Creates a JFrame for the game and populates it with given views and controllers.
    private void createApplicationWindow() {
        // Create window
        applicationWindow = new JFrame("Sudoku");
        applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creates the file drop down menu.
        createMenu(applicationWindow);
    }

    // Creates a menu for the given window
    private void createMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        window.setJMenuBar(menubar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('f');
        menubar.add(fileMenu);

        // The new game option lets users start a new game
        JMenuItem newCmd = new JMenuItem("New Game");
        newCmd.setMnemonic('n');
        fileMenu.add(newCmd);
        newCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promptNewGame();
            }
        });

        // open menu option
        JMenuItem openCmd = new JMenuItem("Open");
        openCmd.setMnemonic('o');
        fileMenu.add(openCmd);
        openCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });

        // save AS menu option
        JMenuItem saveAsCmd = new JMenuItem("Save As...");
        saveAsCmd.setMnemonic('o');
        fileMenu.add(saveAsCmd);
        saveAsCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });

        // save menu option
        JMenuItem saveCmd = new JMenuItem("Save");
        saveAsCmd.setMnemonic('o');
        fileMenu.add(saveCmd);
        saveCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        // quit menu option
        JMenuItem quitCmd = new JMenuItem("Quit");
        quitCmd.setMnemonic('q');
        fileMenu.add(quitCmd);
        quitCmd.addActionListener(new ActionListener() {
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
            // Ok was clicked so setup new game
            setupNewGame(dialog.getRows(), dialog.getColumns());
        }
    }

    private void setupNewGame(int rows, int columns) {
        // users clicked ok so create a new game, and DONT bypass setup mode.
        GameSetupDialog setup = new GameSetupDialog(applicationWindow, rows, columns);

        if(setup.userAccepted)
            attachModel(setup.model);
    }

    private void open() {

    }

    private void saveAs() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Sudoku Save Game", "sudoku");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(applicationWindow);
        if(returnVal == JFileChooser.APPROVE_OPTION)
            saveGame(chooser.getSelectedFile().getPath());
    }

    private void saveGame(String path) {
        // Add .sudoku as file extension
        if (!path.endsWith(".sudoku"))
           path += ".sudoku";



    }

    private void save() {

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

