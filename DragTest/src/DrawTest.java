import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by nathanf on 5/6/14.
 */
public class DrawTest {
    private JFrame window;
    private DraggablePanel card;

    public DrawTest() {
        window = new JFrame();
        window.setSize(500,500);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage image = null;
        try {
            InputStream cardImageFile = new FileInputStream("C:\\git\\csc143\\DragTest\\out\\production\\DragTest\\cardback.jpg");
            image = ImageIO.read(cardImageFile);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }



        card = new DraggablePanel(image);
        window.add(card);
        card.setLocation(100,100);




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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0, null);
    }

    public DraggablePanel(Image image) {
        super();
        setSize(image.getWidth(null), image.getHeight(null));
        this.image = image;
        setBackground(Color.red);
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
            }
        });
    }
}
