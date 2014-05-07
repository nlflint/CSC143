import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by nathanf on 5/6/14.
 */
public class DragTest {
    public static Point drawPile = new Point(20,20);
    public static Point faceUpPile;

    private JFrame window;
    private JLayeredPane pane;
    private DraggablePanel card;

    public DragTest() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        pane = new JLayeredPane();
        pane.setPreferredSize(new Dimension(500, 500));
        pane.setLayout(null);
        pane.setBackground(new Color(0, 158, 29));

        Toolkit tk = Toolkit.getDefaultToolkit();
        URL url = getClass().getResource("cardback.jpg");

        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }

        for (int i = 0; i < 10; i++)
        {
            pane.add(new DraggablePanel(image, pane, i), JLayeredPane.DEFAULT_LAYER);
        }

        faceUpPile = new Point((int)drawPile.getX() + (image.getWidth() + 10), (int)drawPile.getY());

        window.add(pane);
        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new DragTest();
    }
}


