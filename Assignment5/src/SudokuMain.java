import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by nate on 5/10/14.
 */
public class SudokuMain extends JFrame {

    public SudokuMain() {
        SudokuBoard model = new SudokuBoard(4,3);
        model.setValue(5,5,5);
        model.setValue(6,6,6);
        model.fixGivens();
        model.setValue(7,7,7);

        SudokuView view = new SudokuView(model);
        view.setSelected(0,0);

        SukoduController controller = new SukoduController(model, view);

        add(controller, BorderLayout.NORTH);
        add(view);

        pack();
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args)
    {
        new SudokuMain();
    }

}

/**
 * Created by nate on 5/10/14.
 */
class SukoduController extends JPanel {
    public SukoduController(SudokuBase model, SelectedCell view) {
        int size = model.size;

        for (int sudokuValue = 0; sudokuValue <= size; sudokuValue++)
            add(new ControlButton(sudokuValue, model, view));

        GridLayout grid = new GridLayout(1, size + 1);
        grid.setHgap(2);
        grid.setHgap(2);
        setLayout(grid);


    }

}

class ControlButton extends JPanel implements MouseListener {
    // Background colors
    private final Color standardBackgroundColor;
    private final Color highlightedBackgroundColor;
    private final Color activeBackgroundColor;

    private int sudouValue;
    private SudokuBase model;
    private GraphicsLibrary graphicsLibrary;
    private SelectedCell view;

    public ControlButton(int sudokuValue, SudokuBase model, SelectedCell view) {
        //Set background colors
        standardBackgroundColor = Color.white;
        highlightedBackgroundColor = Color.yellow;
        activeBackgroundColor = Color.red;

        this.sudouValue = sudokuValue;
        this.model = model;
        this.view = view;
        this.graphicsLibrary = GraphicsLibrary.getInstance();
        setPreferredSize(new Dimension(50,50));
        setBackground(Color.white);
        setBorder(new BorderUIResource.LineBorderUIResource(Color.black));
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        GraphicSymbol graphics = graphicsLibrary.getGraphic(sudouValue);
        graphics.draw(g, getSize());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = view.getSelectedRow();
        int selectedColumn = view.getSelectedColumn();

        if (!model.isGiven(selectedRow, selectedColumn))
            model.setValue(selectedRow, selectedColumn, sudouValue);

        setBackground(highlightedBackgroundColor);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setBackground(activeBackgroundColor);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(highlightedBackgroundColor);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(standardBackgroundColor);
    }
}