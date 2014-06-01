package Dialogs;

import Controllers.*;
import Model.SudokuBoard;
import Views.SudokuView;
import javax.swing.*;
import java.awt.*;

/**
 * Shows a dialog for setting up givens of a new game.
 * Grading level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 7: Sudoku Serialization/Integration
 */
public class GameSetupDialog extends JDialog {
    /**
     * Indicates if the user accepts the new game
     */
    public boolean userAccepted;

    /**
     * Model that is prepared by the user.
     */
    public SudokuBoard model;

    /**
     * Constructor. Use row and columns to indicate the size of the new board.
     * @param owner JFrame that owns this dialog.
     * @param rows Number of rows new board shall have
     * @param columns Number of columns the new board shall have
     */
    public GameSetupDialog(JFrame owner, int rows, int columns) {
        super(owner, "New Game", true);

        // Create a new game
        model = new SudokuBoard(rows, columns);
        SudokuView view = new SudokuView(model);
        view.setSelected(0,0);
        view.hideIndicators();
        SudokuController controller = new SudokuController(model, view);
        SetGivensViewController setGivensViewController = new SetGivensViewController(this);

        // Attach components to the dialog
        add(controller, BorderLayout.NORTH);
        add(view);
        add(setGivensViewController, BorderLayout.SOUTH);
        addKeyListener(model, view, controller);

        // Show the dialog
        pack();
        setVisible(true);
    }

    // Adds a new key listener to the window.
    private void addKeyListener(SudokuBoard model, SudokuView view, SudokuController controller) {
        this.addKeyListener(new SudokuValueHandler(view, model, controller));
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    // User presses cancel
    public void cancel() {
        userAccepted = false;
        dispose();
    }

    //User presses setGivens
    public void setGivens() {
        model.fixGivens();
        setVisible(false);
        userAccepted = true;
        dispose();
    }


}
