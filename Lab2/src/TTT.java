import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Draws a tic tac toe board
 *
 * @author Nathan Flint
 * @version Lab2: Graphics
 */
public class TTT {
    private TicTacToePanel ticTacToePanel;
    private JComponent parentComponent;

    public TTT()
    {
        // Create a 3x3 layout
        GridLayout grid = new GridLayout(3,3);
        grid.setHgap(20);
        grid.setVgap(20);

        // Create the tic tac toe board and apply the grid layout
        ticTacToePanel = new TicTacToePanel();
        ticTacToePanel.setLayout(grid);
        ticTacToePanel.setBorder(new EmptyBorder(10,10,10,10));

        // Create a window
        JFrame win = new JFrame("Tic-Tac-Toe");
        win.setSize(400, 300);
        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win.setVisible(true);

        // Add tic tac toe board to window.
        win.add(ticTacToePanel, BorderLayout.CENTER);
        win.validate();

    }

    public static void main(String[] args) {

        TTT ticTacToeBoard = new TTT();
        ticTacToeBoard.build();
    }

    public void build()
    {
        // Add X's and O's to the tic tac toe board. The Draw enum also determines which lines
        // of the tic tac toe board will be drawn.
        ticTacToePanel.add(CreateXCell());
        ticTacToePanel.add(CreateOCell());
        ticTacToePanel.add(CreateXCell());
        ticTacToePanel.add(CreateOCell());
        ticTacToePanel.add(CreateXCell());
        ticTacToePanel.add(CreateTicTacToeCell(null));
        ticTacToePanel.add(CreateOCell());
        ticTacToePanel.add(CreateTicTacToeCell(null));
        ticTacToePanel.add(CreateXCell());

        ticTacToePanel.validate();
    }

    // Creates an "X"
    private JComponent CreateXCell()
    {
        X2 xComponent = new X2();
        xComponent.setLineColor(Color.blue);

        return CreateTicTacToeCell(xComponent);
    }

    // Creates a JPanel with some default spacing and attaches an optional component
    private JPanel CreateTicTacToeCell(Component shape)
    {
        // Create the cell, which is a JPanel, and give it some spacing around it.
        JPanel cell = new JPanel();
        cell.setLayout(new BorderLayout());
        cell.setBorder(BorderFactory.createEmptyBorder(13,13,13,13));
        cell.setOpaque(false);

        // If a shape was given, then add it. Else it's a blank tile
        if (shape != null)
            cell.add(shape);

        return cell;
    }

    // Creates an "O"
    private JComponent CreateOCell()
    {
        O2 oComponent = new O2();
        oComponent.setLineColor(Color.red);
        oComponent.setFillColor(Color.yellow);

        return CreateTicTacToeCell(oComponent);
    }

    /**
     * Creates a panel with tic tac toe lines drawn in the background
     */
    private class TicTacToePanel extends JPanel {
        /**
         * Draws the tic tac toe lines.
         * @param g graphics for drawing
         */
        public void paintComponent(Graphics g)
        {
            // Draws vertical tic tac toe lines
            g.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight());
            g.drawLine(getWidth() * 2 / 3, 0, getWidth() * 2 / 3, getHeight());

            // Draws horizontal tic tac toe lines
            g.drawLine(0, getHeight() / 3, getWidth(), getHeight() / 3);
            g.drawLine(0, getHeight() * 2 / 3, getWidth(), getHeight() * 2 / 3);
        }
    }
}
