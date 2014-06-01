package Controllers;

import Model.SudokuBase;
import Views.SelectedCell;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The keyboard listener for a sudoku game.
 */
public class SudokuValueHandler extends KeyAdapter {
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
