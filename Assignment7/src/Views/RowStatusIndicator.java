package Views;

import Model.SudokuBase;

import java.awt.*;

/**
 * Created by nate on 6/1/14.
 */
public class RowStatusIndicator extends StatusIndicator {

    public RowStatusIndicator(SudokuBase model) {
        super(model);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = model.size;

        for (int i = 0; i < size; i++) {
            int startX =  0;
            int startY = i  * playSpaceWidthInPixels;
            int endX = size * playSpaceWidthInPixels;
            int endY = startY + playSpaceWidthInPixels ;
            g.setColor(getRowColor(i));
            g.fillRect(startX, startY, endX, endY);
        }
    }

    private Color getRowColor(int rowIndex) {
        SudokuBase.State rowState = model.getRowState(rowIndex);
        switch (rowState) {
            case COMPLETE:
                return Color.GREEN;
            case INCOMPLETE:
                return Color.YELLOW;
            case ERROR:
                return Color.RED;
        }
        return Color.WHITE;
    }
}
