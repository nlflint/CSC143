package Views;

import Model.SudokuBase;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

/**
 * Created by nate on 6/1/14.
 */
public abstract class StatusIndicator extends JPanel {
    protected SudokuBase model;
    protected final int playSpaceWidthInPixels = 5;


    public StatusIndicator(SudokuBase model) {
        this.model = model;
        setBorder(new BorderUIResource.LineBorderUIResource(Color.black));
        int totalWidth = model.size * playSpaceWidthInPixels;
        setPreferredSize(new Dimension(totalWidth,totalWidth));

    }
}
