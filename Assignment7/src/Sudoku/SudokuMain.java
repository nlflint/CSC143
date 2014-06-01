package Sudoku;

import Controllers.*;
import Dialogs.*;
import Model.*;
import Views.SudokuView;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

/**
 * Main class for the sudoku program. Ties together the view and model with a controller.
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 5: Sudoku Input Handling
 */
public class SudokuMain implements Observer{
    // Contains the active game window, and previous active games windows.
    private JFrame applicationWindow;
    private SudokuBoard currentModel;
    private SudokuView currentView;
    private SudokuController currentController;
    private String lastFilePath;
    private boolean gameHasUnsavedChanges;
    private List<String> mostRecentFiles;
    private int limitOfMostRecentFiles = 4;
    private String configFileFullPath;

    /**
     * Constructor. Sets up and initial game window.
     */
    public SudokuMain() {
        configFileFullPath = getFullConfigFilePath();

        // Load config file
        loadListOfRecentFiles();

        SudokuBoard model;
        if (mostRecentFiles.size() > 0 && fileExists(mostRecentFiles.get(0)))
        {
            openGame(mostRecentFiles.get(0));
        }
        else
        {
            // Creates a new 3x3 game and by-passes the Setup mode.
            model = new SudokuBoard(3,3);
            setupInitialBoardValues(model);

            attachModel(model);
        }
    }

    private String getFullConfigFilePath() {
        final String configFileName = ".sudokurc";
        File configFile = new File(configFileName);
        return configFile.getAbsolutePath();
    }

    private void loadListOfRecentFiles() {
        mostRecentFiles = new ArrayList<String>();

        // stop if config file doesn't exist
        if (!fileExists(configFileFullPath))
            return;


        FileInputStream fileInputStream;
        DataInputStream dataInputStream;
        try {
            fileInputStream = new FileInputStream(configFileFullPath);
            dataInputStream = new DataInputStream(fileInputStream);
            for (int i = 0; i < limitOfMostRecentFiles; i++) {
                String fullFilename  = dataInputStream.readUTF();
                if (fileExists(fullFilename))
                    mostRecentFiles.add(fullFilename);
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    private boolean fileExists(String fileName) {
        File f = new File(fileName);
        return f.exists() && !f.isDirectory();
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

        // observe the model for changes
        model.addObserver(this);

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
        applicationWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (gameHasUnsavedChanges)
                    promptSaveGame();
            }
        });

        // Creates the file drop down menu.
        createMenu(applicationWindow);
    }

    private void promptSaveGame() {
        SaveGameDialog saveDialog = new SaveGameDialog(applicationWindow);
        if (saveDialog.userRequestsSaveChanges)
            save();

    }

    // Creates a menu for the given window
    private void createMenu(JFrame window) {
        JMenuBar menubar = window.getJMenuBar();
        if (menubar == null)
            menubar = new JMenuBar();

        menubar.removeAll();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('f');
        menubar.add(fileMenu);

        // The new game option lets users start a new game
        JMenuItem newCmd = new JMenuItem("New Game");
        newCmd.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
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
        openCmd.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openCmd.setMnemonic('o');
        fileMenu.add(openCmd);
        openCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });

        fileMenu.addSeparator();

        // save AS menu option
        JMenuItem saveAsCmd = new JMenuItem("Save As...");
        saveAsCmd.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        saveAsCmd.setMnemonic('a');
        fileMenu.add(saveAsCmd);
        saveAsCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });

        // save menu option
        JMenuItem saveCmd = new JMenuItem("Save");
        saveCmd.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveAsCmd.setMnemonic('s');
        fileMenu.add(saveCmd);
        saveCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        fileMenu.addSeparator();
        for (int i = 0; i < mostRecentFiles.size(); i ++) {
            JMenuItem mruItem = new JMenuItem(mostRecentFiles.get(i));
            mruItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JMenuItem selectedMenuItem = (JMenuItem) e.getSource();
                    String fullFilename = selectedMenuItem.getText();
                    if (fileExists(fullFilename))
                        openGame(fullFilename);
                    else {
                        //Error opening file. remove it from the list.
                        JOptionPane.showMessageDialog(applicationWindow, "Cannot open file: " + fullFilename);
                        applicationWindow.getJMenuBar().getMenu(0).remove(selectedMenuItem);
                    }
                }
            });
            fileMenu.add(mruItem);
        }

        fileMenu.addSeparator();

        // save menu option
        JMenuItem resetCmd = new JMenuItem("Reset Game");
        resetCmd.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        resetCmd.setMnemonic('r');
        fileMenu.add(resetCmd);
        resetCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        // quit menu option
        JMenuItem quitCmd = new JMenuItem("Quit");
        quitCmd.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        quitCmd.setMnemonic('q');
        fileMenu.add(quitCmd);
        quitCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });

        window.setJMenuBar(menubar);
        menubar.validate();
    }

    private void reset() {
        currentModel.resetGame();
    }

    // Quites the game
    private void quit() {
        WindowEvent wev = new WindowEvent(applicationWindow, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
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
        {
            attachModel(setup.model);
            lastFilePath = null;
            gameHasUnsavedChanges = true;
        }

    }

    private void open() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Sudoku Game", "sudoku");
        chooser.setFileFilter(filter);

        // show dialog
        int returnVal = chooser.showOpenDialog(applicationWindow);

        // save if user clicked save.
        if(returnVal == JFileChooser.APPROVE_OPTION)
            openGame(chooser.getSelectedFile().getPath());
    }

    private void openGame(String path) {
        // Append .sudoku
        if (!path.endsWith(".sudoku"))
            path += ".sudoku";

        try {
            FileInputStream openFile = new FileInputStream(path);
            SudokuBoard newBoard = initializeSudokuBoard(openFile);
            newBoard.deserialize(openFile);
            attachModel(newBoard);
            openFile.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to open: \n" + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to open: \n" + e.getMessage());
        }

        lastFilePath = path;
        gameHasUnsavedChanges = false;
        setMostRecentGame(path);
    }

    private SudokuBoard initializeSudokuBoard(FileInputStream openFile) throws IOException {
        DataInputStream dataStream = new DataInputStream(openFile);
        int rows = dataStream.readInt();
        int columns = dataStream.readInt();
        return new SudokuBoard(rows,columns);
    }

    private void saveAs() {
        // Create dialog
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Sudoku Game", "sudoku");
        chooser.setFileFilter(filter);

        // show dialog
        int returnVal = chooser.showSaveDialog(applicationWindow);

        // save if user clicked save.
        if(returnVal == JFileChooser.APPROVE_OPTION)
            saveGame(chooser.getSelectedFile().getPath());
    }

    private void saveGame(String path) {
        // Append .sudoku
        if (!path.endsWith(".sudoku"))
           path += ".sudoku";

        try {
            FileOutputStream saveFile = new FileOutputStream(path);
            writeFileHeader(saveFile);
            currentModel.serialize(saveFile);
            saveFile.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to save: \n" + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to save: \n" + e.getMessage());
        }

        lastFilePath = path;
        gameHasUnsavedChanges = false;
        setMostRecentGame(path);
    }

    private void setMostRecentGame(String path) {
        // Remove duplicate file names
        Iterator<String> iterator = mostRecentFiles.iterator();
        while (iterator.hasNext())
            if (iterator.next().equals(path))
                iterator.remove();

        // Add new file to top of list
        mostRecentFiles.add(0,path);
        int newMruLength = Math.min(mostRecentFiles.size(), limitOfMostRecentFiles);
        mostRecentFiles = mostRecentFiles.subList(0, newMruLength);

        // Write the current list to the config file.
        try {
            FileOutputStream fileOutput = new FileOutputStream(configFileFullPath);
            DataOutputStream dataOutput = new DataOutputStream(fileOutput);
            for (String filename : mostRecentFiles)
                dataOutput.writeUTF(filename);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(applicationWindow,"Error saving: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(applicationWindow, "Error saving: " + e.getMessage());
        }

        // Recreate menu bar to update file list.
        createMenu(applicationWindow);

    }

    private void writeFileHeader(FileOutputStream saveFile) throws IOException {
        DataOutputStream dataStream = new DataOutputStream(saveFile);
        dataStream.writeInt(currentModel.rows);
        dataStream.writeInt(currentModel.columns);
    }

    private void save() {
        if (lastFilePath == null)
            saveAs();
        else
            saveGame(lastFilePath);
    }

    @Override
    public void update(Observable o, Object arg) {
        gameHasUnsavedChanges = true;
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

