import java.awt.*;

/**
 * A sample component class.
 *
 * @author Nathan Flint
 * @version Lab2: Graphics
 */
public class X2 extends javax.swing.JComponent {

    /**
     * Constructor, sets the preferred size and color.
     */
    public X2() {
        setPreferredSize(new java.awt.Dimension(25, 25));
        lc = Color.BLACK;
    }

    // Color will be used to draw the X
    private java.awt.Color lc;

    /**
     * Gets the color that will be used to draw the X
     * @return Color that will be used
     */
    public java.awt.Color getLineColor() {
        return lc;
    }

    /**
     * Sets the color that will be used to draw the X
     * @param rgb Color to use
     */
    public void setLineColor(java.awt.Color rgb) {
        lc = rgb;
        repaint();
    }

    /**
     * The necessary method. This method
     * renders the component.
     * @param g The Graphics object use to render
     */
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.setColor(lc);
        g.drawLine(0, 0, getWidth() - 1, getHeight() - 1);
        g.drawLine(0, getHeight() - 1, getWidth() - 1, 0);
    }

    /**
     * Application method.
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        javax.swing.JFrame win;
        win = new javax.swing.JFrame("Test Component");
        win.add(new X2(), java.awt.BorderLayout.NORTH);
        win.add(new X2(), java.awt.BorderLayout.SOUTH);
        win.add(new X2(), java.awt.BorderLayout.EAST);
        win.add(new X2(), java.awt.BorderLayout.WEST);
        win.setSize(400, 300);
        win.setVisible(true);
    }
}
