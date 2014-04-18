import javax.swing.*;
import java.awt.*;

/**
 * Draws a tic tac toe board
 *
 * @author Nathan Flint
 * @version Lab2: Graphics
 */
public class TTT extends JComponent {
    public static void main(String[] args) {

        // Create a 3x3 layout
        GridLayout grid = new GridLayout(3,3);
        grid.setHgap(2);
        grid.setVgap(2);

        // Create the tic tac toe board and apply the grid layout
        JPanel ticTacToeBoard = new JPanel(grid);

        // Add X's and O's to the tic tac toe board.
        ticTacToeBoard.add(CreateX(), BorderLayout.CENTER);
        ticTacToeBoard.add(CreateO(), BorderLayout.CENTER);
        ticTacToeBoard.add(CreateX(), BorderLayout.CENTER);
        ticTacToeBoard.add(CreateO(), BorderLayout.CENTER);
        ticTacToeBoard.add(CreateX(), BorderLayout.CENTER);
        ticTacToeBoard.add(new JPanel(), BorderLayout.CENTER);
        ticTacToeBoard.add(CreateO(), BorderLayout.CENTER);
        ticTacToeBoard.add(new JPanel(), BorderLayout.CENTER);
        ticTacToeBoard.add(CreateX(), BorderLayout.CENTER);

        // Create a window
        JFrame win = new JFrame("Tic-Tac-Toe");
        win.setSize(400, 300);
        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win.setVisible(true);

        // Set a black background
        win.add(new TTT(), BorderLayout.CENTER);
        win.add(ticTacToeBoard, BorderLayout.CENTER);

        // Add tic tac toe board to window.
//        win.add(ticTacToeBoard, BorderLayout.CENTER);
//
//        // Add blank panels to create a border around the tic tac toe board.
//        win.add(new JPanel(), BorderLayout.NORTH);
//        win.add(new JPanel(), BorderLayout.EAST);
//        win.add(new JPanel(), BorderLayout.SOUTH);
//        win.add(new JPanel(), BorderLayout.WEST);
    }

    private static X2 CreateX()
    {
        X2 xComponent = new X2();
        xComponent.setLineColor(Color.blue);
        return xComponent;
    }

    private static O2 CreateO()
    {
        O2 oComponent = new O2();
        oComponent.setLineColor(Color.red);
        oComponent.setFillColor(Color.yellow);
        return oComponent;
    }

    public void paintComponent(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());
    }
}
