import javax.swing.*;
import java.awt.*;

/**
 * Draws an O
 *
 * @author Nathan Flint
 * @version Lab2: Graphics
 */
public class O2 extends JComponent {

    private Color _lineColor;
    private Color _fillColor;

    public O2 ()
    {
        setPreferredSize(new Dimension(25, 25));
        _lineColor = Color.black;
        _fillColor = Color.white;
    }

    /**
     * Sets the outline color of the O
     * @param color Color of the outline
     */
    public void setLineColor(Color color) {_lineColor = color;}

    /**
     * Sets the fill color of the O
     * @param color fill color
     */
    public void setFillColor(Color color) {_fillColor = color;}

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(_fillColor);
        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(_lineColor);
        g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
