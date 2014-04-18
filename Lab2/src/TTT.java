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
    /**
     * Application method.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new TTT();
    }

    /**
     * Constructor that does it all.
     */
    public TTT()
    {
        // Create a 3x3 layout
        GridLayout grid = new GridLayout(3,3);
        grid.setHgap(30);
        grid.setVgap(30);

        // Create the tic tac toe board, apply the grid layout, and add blank spacing around the panel
        TicTacToePanel ticTacToePanel = new TicTacToePanel();
        ticTacToePanel.setLayout(grid);
        ticTacToePanel.setBorder(new EmptyBorder(15,15,15,15));

        // Create a window
        JFrame win = new JFrame("Tic-Tac-Toe");
        win.setSize(400, 300);
        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win.setVisible(true);

        // Add tic tac toe board to window.
        win.add(ticTacToePanel);

        // Add X's and O's and blanks to the tic tac toe board.
        ticTacToePanel.add(CreateX());
        ticTacToePanel.add(CreateO());
        ticTacToePanel.add(CreateX());
        ticTacToePanel.add(CreateO());
        ticTacToePanel.add(CreateX());
        ticTacToePanel.add(new JPanel());
        ticTacToePanel.add(CreateO());
        ticTacToePanel.add(new JPanel());
        ticTacToePanel.add(CreateX());

        // Draw everything
        win.validate();

    }

    // Creates an "X"
    private JComponent CreateX()
    {
        X2 xComponent = new X2();
        xComponent.setLineColor(Color.blue);
        return xComponent;
    }

    // Creates an "O"
    private JComponent CreateO()
    {
        O2 oComponent = new O2();
        oComponent.setLineColor(Color.red);
        oComponent.setFillColor(Color.yellow);
        return oComponent;
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
