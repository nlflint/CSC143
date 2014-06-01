package Views;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by nate on 6/1/14.
 */
class MouseHandler extends MouseAdapter {

    private SelectedCell view;

    /**
     * Constructor. Saves a reference to the view so cells can be selected.
     * @param view
     */
    public MouseHandler(SelectedCell view) {
        this.view = view;
    }

     /**
     * Selects a cell on the view when mouse is clicked
     * @param e event info
     */
    @Override
    public void mousePressed(MouseEvent e) {
        super.mouseClicked(e);

        // Change selected row to the space that was clicked
        PlaySpace playSpace = ((PlaySpace)e.getSource());
        view.setSelected(playSpace.getRow(), playSpace.getColumn());
    }
}
