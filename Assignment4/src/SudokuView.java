import javax.swing.*;
import java.awt.*;

/**
 * Created by nate on 5/1/14.
 */
public class SudokuView extends JPanel
        implements SelectedCell {

    public SudokuView(SudokuBase base)
    {


        int size = base.rows * base.columns;
        setLayout(new GridLayout(size,size));

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                PlaySpace playSpace = new PlaySpace(base, j, i);
                add(playSpace);
            }
        }

        setPreferredSize(new Dimension(800,800));
        setVisible(true);
    }

    @Override
    public void setSelected(int row, int col) {

    }

    @Override
    public int getSelectedRow() {
        return 0;
    }

    @Override
    public int getSelectedColumn() {
        return 0;
    }
}

class PlaySpace extends JPanel {
    SudokuBase base;
    int row, column;

    public PlaySpace(SudokuBase base, int row, int column)
    {
        this.base = base;
        this.row = row;
        this.column = column;
        setPreferredSize(new Dimension(30, 30));
    }

    public void paintComponent(Graphics g)
    {
        String value = base.getValue(row, column) + "";
        g.drawString(value, 10, 10);
    }
}
