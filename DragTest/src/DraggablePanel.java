import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by nathanf on 5/7/2014.
 */
class DraggablePanel extends JPanel {
    private int offsetX;
    private int offsetY;
    private Image image;
    private JLayeredPane pane;
    private boolean isFaceDown;
    private int value;

    public DraggablePanel(Image image, JLayeredPane pane, int value) {
        super();
//        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        setSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        setBackground(Color.white);
        this.image = image;
        this.pane = pane;
        this.isFaceDown = true;
        this.value = value;
        this.setLocation((int) DragTest.drawPile.getX(), (int) DragTest.drawPile.getY());

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (!isFaceDown)
                    dragCard(e);
            }


            private void dragCard(MouseEvent e) {
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

            @Override
            public void mouseClicked(MouseEvent e) {
                draw();
                moveToTop();

            }

            private void draw() {
                isFaceDown = false;
                setLocation((int) DragTest.faceUpPile.getX(), (int) DragTest.faceUpPile.getY());
            }

        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isFaceDown)
            g.drawImage(image,0,0, null);
        else {
            g.drawRoundRect(0,0, getWidth() - 1, getHeight() - 1, 5, 5);
            g.drawString(value + "", 20, 50);

        }
    }

    private void moveToTop() {
        pane.moveToFront(this);
    }

}


