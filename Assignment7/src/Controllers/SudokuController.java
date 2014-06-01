package Controllers;

import Model.SudokuBase;
import View.SelectedCell;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Controller for adding values to the sudoku board.
 */
public class SudokuController extends JPanel {
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
