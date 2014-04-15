import java.awt.*;

/**
 * A sample component class.
 */
public class X extends javax.swing.JComponent {

    /**
     * Constructor, sets the preferred size.
     */
    public X() {
        setPreferredSize(new java.awt.Dimension(25, 25));
        lc = Color.BLACK;
    }

    private java.awt.Color lc;
    public java.awt.Color getLineColor() {
        return lc;
    }
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

    public static void main(String[] args)
    {
        javax.swing.JFrame win;
        win = new javax.swing.JFrame("Test Component");
        win.add(new X(), java.awt.BorderLayout.NORTH);
        win.add(new X(), java.awt.BorderLayout.SOUTH);
        win.add(new X(), java.awt.BorderLayout.EAST);
        win.add(new X(), java.awt.BorderLayout.WEST);
        win.setSize(400, 300);
        win.setVisible(true);
    }
}
