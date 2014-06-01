package Controllers;

import Model.SudokuBase;
import Model.SudokuBoard;
import View.SudokuView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nate on 5/31/14.
 */
public class GameSetupDialog extends JDialog {
    public boolean userAccepted;
    public SudokuBoard model;

    public GameSetupDialog(JFrame owner, int rows, int columns) {
        super(owner, "New Game", true);
        model = new SudokuBoard(rows, columns);
        SudokuView view = new SudokuView(model);
        view.setSelected(0,0);
        SudokuController controller = new SudokuController(model, view);
        SetGivensViewController setGivensViewController = new SetGivensViewController(this);

        add(controller, BorderLayout.NORTH);
        add(view);
        add(setGivensViewController, BorderLayout.SOUTH);

        addKeyListener(model, view, controller);

        pack();
        setVisible(true);

    }

    // Adds a new key listener to the window.
    private void addKeyListener(SudokuBase model, SudokuView view, SudokuController controller) {
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
