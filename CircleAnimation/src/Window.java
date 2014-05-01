import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by nathanf on 5/1/14.
 */
public class Window extends JFrame {
    int framesPerSecond = 60;

    public Window()
    {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);

        Point center = new Point(200,200);

        ArrayList<Circle> circles = new ArrayList<Circle>();
        for (int i = 0; i < 200; i++)
            circles.add(new Circle(center));


        for (Circle c : circles)
            add(c);

        setVisible(true);

        while(true)
        {
            repaint();
            sleep();

            for (Circle c : circles)
            {
                c.step();
            }

        }
    }

    // Sleeps for the tic amount
    private void sleep() {
        // how long we want to sleep for.
        long millisecondsRemaining = 1000 / framesPerSecond;
        // When the wait time is over
        long wakeUpTime = System.currentTimeMillis() + millisecondsRemaining;

        // Loop until we are past the wake up time. In case thread is awaken earlier than instructed.
        while (millisecondsRemaining > 0)
        {
            try
            {
                // Sleep for the time remaining
                Thread.sleep(millisecondsRemaining);
            }
            catch (InterruptedException e)
            {
            }
            // update time left until wakeup time.
            millisecondsRemaining = wakeUpTime - System.currentTimeMillis();
        }
    }

    public static void main(String[] args)
    {
        new Window();
    }

}
