import javax.swing.*;
import java.awt.*;
import java.util.Map;

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
    Map<Integer, JPanel> shapes;

    public PlaySpace(SudokuBase base, Map<Integer,JPanel> shapes, int row, int column)
    {
        this.base = base;
        this.row = row;
        this.column = column;
        this.shapes = shapes;
        setPreferredSize(new Dimension(30, 30));
    }

    public void paintComponent(Graphics g)
    {
        String value = base.getValue(row, column) + "";

        g.drawString(value, 10, 10);
    }
}



class Alpha extends JPanel {
    public void paintComponent(Graphics g)
    {
        g.drawString("A", getWidth() / 2, getHeight() / 2);
    }
}
