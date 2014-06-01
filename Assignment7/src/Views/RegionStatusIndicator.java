package Views;

import Model.SudokuBase;

import java.awt.*;

/**
 * Created by nate on 6/1/14.
 */
public class RegionStatusIndicator extends StatusIndicator {

    public RegionStatusIndicator(SudokuBase model) {
        super(model);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = model.size;
        int columns = model.columns;
        int rows = model.rows;
        int regionWidth = model.columns;
        int regionHeight = model.rows;

        int regionCount = (size / columns) * (size / rows);
        int regionsAcross = size / columns;

        for (int i = 0; i < regionCount; i++) {
            int startX =  (i % regionsAcross) * regionWidth * playSpaceWidthInPixels;
            int startY = (i / regionsAcross) * regionHeight * playSpaceWidthInPixels;
            int endX = startX + (regionWidth * playSpaceWidthInPixels);
            int endY = startY + (regionHeight * playSpaceWidthInPixels);
            g.setColor(getRegionColor(i));
            g.fillRect(startX, startY, endX, endY);
        }
    }

    private Color getRegionColor(int regionIndex) {
        SudokuBase.State regionState = model.getRegionState(regionIndex);
        switch (regionState) {
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
