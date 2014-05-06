import javax.swing.*;
import java.awt.*;

/**
 * Created by nathanf on 5/1/14.
 */
public class Circle extends JComponent {
    double angleFromCenter;
    int distanceFromCenter;
    double velocity;
    int diameter;
    Color color;
    Direction direction;

    enum Direction {
        Clockwise,
        CounterClockwise
    }

    Point rotationPoint;

    public Circle(Point rotationPoint)
    {
        angleFromCenter = Math.random() * 360;
        diameter = 10;
        distanceFromCenter =(int)(130 * Math.random()) + 20;
        this.rotationPoint = rotationPoint;
        direction = (Math.random() - 0.5) > 0 ? Direction.Clockwise: Direction.CounterClockwise;
        setSize(diameter,diameter);
        velocity = Math.random() * 4;

        color = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));


    }
    @Override
    public void paintComponent(Graphics g)
    {
        double radians = (2 * Math.PI) * (angleFromCenter / 360.0);
        double yDelta = Math.sin(radians) * distanceFromCenter;
        double xDelta = Math.cos(radians) * distanceFromCenter;

        double newX = rotationPoint.getX() + xDelta - (0.5 * diameter);
        double newY = rotationPoint.getY() + yDelta - (0.5 * diameter);

        g.setColor(color);
        g.fillOval(0, 0, 10, 10);
        setBounds((int)newX, (int)newY, diameter, diameter);


    }

    public void step()
    {
        int directionFactor = direction == Direction.CounterClockwise ? -1 : 1;
        angleFromCenter += directionFactor * velocity;
        angleFromCenter %= 360;
    }
}
