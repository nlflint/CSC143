package Views;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Handles keyboard input for moving the selected cell with arrow keys
 */
class KeyHandler extends KeyAdapter {
    // The selectedCell class will be used to change the selected cell
    SelectedCell view;

    /**
     * Constructor holds a reference to the selected cell object
     * @param view the selected cell object
     */
    public KeyHandler(SelectedCell view) { this.view = view; }

    /**
     * This method is executed everytime a key is pressed.
     * @param e event information
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Get the currently selected column and row
        int selectedRow = view.getSelectedRow();
        int selectedColumn = view.getSelectedColumn();

        // Based on the key, alter the currently selected row.
        // Not worrying of the selected cell goes off the board because
        // the selected cell object will handle that.
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
        }
    }
}
