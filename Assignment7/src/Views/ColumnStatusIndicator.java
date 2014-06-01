package Views;

import Model.SudokuBase;

import java.awt.*;

/**
 * Created by nate on 6/1/14.
 */
public class ColumnStatusIndicator extends StatusIndicator {

    public ColumnStatusIndicator(SudokuBase model) {
        super(model);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = model.size;
        int columns = model.columns;
        int rows = model.rows;


        for (int i = 0; i < size; i++) {
            int startX =  i  * playSpaceWidthInPixels;
            int startY = 0;
            int endX = startX + playSpaceWidthInPixels;
            int endY = size * playSpaceWidthInPixels;
            g.setColor(getColumnColor(i));
            g.fillRect(startX, startY, endX, endY);
        }
    }

    private Color getColumnColor(int columnIndex) {
        SudokuBase.State columnState = model.getColumnState(columnIndex);
        switch (columnState) {
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