import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by nathanf on 5/6/14.
 */
public class DrawTest {
    private JFrame window;
    private JLayeredPane pane;
    private DraggablePanel card;

    public DrawTest() {
        window = new JFrame();
        window.setSize(500,500);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pane = new JLayeredPane();
        pane.setPreferredSize(new Dimension(500,500));

        BufferedImage image = null;
        try {
            InputStream cardImageFile = new FileInputStream("cardback.jpg");
            image = ImageIO.read(cardImageFile);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < 5; i++)
        {
            pane.add(new DraggablePanel(image, pane), JLayeredPane.DEFAULT_LAYER);
        }

        window.add(pane);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new DrawTest();
    }
}


class DraggablePanel extends JPanel {
    private int offsetX;
    private int offsetY;
    private Image image;
    private JLayeredPane pane;

    public DraggablePanel(Image image, JLayeredPane pane) {
        super();
        setSize(image.getWidth(null), image.getHeight(null));
        this.image = image;
        this.pane = pane;

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (offsetX > 0 && offsetY > 0) {
                    int newX = getX() + e.getX() - offsetX;
                    int newY = getY() + e.getY() - offsetY;
                    setLocation(newX, newY);
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                offsetX = e.getX();
                offsetY = e.getY();
                moveToLayer(JLayeredPane.DRAG_LAYER);

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                moveToLayer(JLayeredPane.DEFAULT_LAYER);
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0, null);
    }

    private void moveToLayer(int JLayerPaneIndex) {
        pane.setLayer(this, JLayerPaneIndex);
    }
}
