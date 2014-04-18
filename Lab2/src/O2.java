import javax.swing.*;
import java.awt.*;

/**
 * Draws an O shape
 *
 * @author Nathan Flint
 * @version Lab2: Graphics
 */
public class O2 extends JComponent {

    // Colors of the O
    private Color _lineColor;
    private Color _fillColor;

    /**
     * Sets default size and color
     */
    public O2 () {
        setPreferredSize(new Dimension(25, 25));
        _lineColor = Color.black;
        _fillColor = Color.white;
    }

    /**
     * Sets the outline color of the O
     * @param color Color of the outline
     */
    public void setLineColor(Color color) {
        _lineColor = color;
    }

    /**
     * Gets teh outline color of the O
     * @return the outline color
     */
    public Color getLineColor() {
        return _lineColor;
    }

    /**
     * Sets the fill color of the O
     * @param color fill color
     */
    public void setFillColor(Color color) {
        _fillColor = color;
    }

    /**
     * Gets the fill color
     * @return the fill color
     */
    public Color setFillColor() {
        return _fillColor;
    }

    /**
     * Method that paints the O
     * @param g graphics component where the O will be painted
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(_fillColor);
        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(_lineColor);
        g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
