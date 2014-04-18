import javax.swing.*;
import java.awt.*;

/**
 * Draws a tic tac toe board
 *
 * @author Nathan Flint
 * @version Lab2: Graphics
 */
public class TTT {
    private JPanel ticTacToePanel;
    private JComponent parentComponent;

    public TTT()
    {
        // Create a 3x3 layout
        GridLayout grid = new GridLayout(3,3);

        // Create the tic tac toe board and apply the grid layout
        ticTacToePanel = new JPanel(grid);

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
        ticTacToePanel.add(CreateXCell(Draw.rightAndBottomLines));
        ticTacToePanel.add(CreateOCell(Draw.rightAndBottomLines));
        ticTacToePanel.add(CreateXCell(Draw.bottomLineOnly));
        ticTacToePanel.add(CreateOCell(Draw.rightAndBottomLines));
        ticTacToePanel.add(CreateXCell(Draw.rightAndBottomLines));
        ticTacToePanel.add(CreateTicTacToeCell(null, Draw.bottomLineOnly));
        ticTacToePanel.add(CreateOCell(Draw.rightLineOnly));
        ticTacToePanel.add(CreateTicTacToeCell(null, Draw.rightLineOnly));
        ticTacToePanel.add(CreateXCell(Draw.noLines));

        ticTacToePanel.validate();
    }

    // Creates an "X" with the given tic tac toe lines
    private JComponent CreateXCell(Draw lines)
    {
        X2 xComponent = new X2();
        xComponent.setLineColor(Color.blue);

        return CreateTicTacToeCell(xComponent, lines);
    }

    private TicTacToeCell CreateTicTacToeCell(Component shape, Draw lines)
    {
        // Create the cell, which is a JPanel, and give it some spacing around it.
        TicTacToeCell cell = new TicTacToeCell(lines);
        cell.setLayout(new BorderLayout());
        cell.setBorder(BorderFactory.createEmptyBorder(13,13,13,13));

        // If a shape was given, then add it. Else it's an un-played tile
        if (shape != null)
            cell.add(shape);

        return cell;
    }

    // Creates an "O" with the given tic tac toe lines
    private JComponent CreateOCell(Draw lines)
    {
        O2 oComponent = new O2();
        oComponent.setLineColor(Color.red);
        oComponent.setFillColor(Color.yellow);

        return CreateTicTacToeCell(oComponent, lines);
    }

    /**
     * Each tic tac toe cell draws components contained inside which are set elsewhere,
     * and then optionally draws a right and bottom line for tic tac toe lines.
     */
    private class TicTacToeCell extends JPanel {
        // Contains the setting for which tic tac toe lines to draw
        private Draw draw;

        /**
         * Constructor saves the line draw setting.
         * @param lines Which lines will be drawn
         */
        public TicTacToeCell(Draw lines) {
            super();

            // Save the line draw settings
            draw = lines;
        }

        /**
         * Draws the tic tac toe lines.
         * @param g graphics for drawing
         */
        public void paintComponent(Graphics g)
        {
            // Optionally draw the right line
            if (draw == Draw.rightAndBottomLines || draw == Draw.rightLineOnly)
                g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1);

            // Optionally draw the bottom line
            if (draw == Draw.rightAndBottomLines || draw == Draw.bottomLineOnly)
                g.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1);
        }
    }

    /**
     * Each tic tac toe cell will optionally draw a bottom line and right lin
     * for the tic tac toe lines.
     *
     * This enum identifies the possibilities.
     */
    private enum Draw
    {
        rightLineOnly,
        bottomLineOnly,
        rightAndBottomLines,
        noLines

    }

}
