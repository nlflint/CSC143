import javax.swing.*;
import java.awt.*;

/**
 * Created by nathanf on 4/28/14.
 */
public class Timer
{
    JButton startPause;
    JButton reset;
    JLabel time;

    public Timer ()
    {
        JFrame window = new JFrame();
        window.setSize(400, 300);
        window.setLocation(100, 100);
        window.setLayout(new BorderLayout());
        window.setTitle("My Timer");

        startPause = new JButton("Start");
        reset = new JButton("Reset");

        time = new JLabel("0.00 seconds");
        time.setFont(new Font("Ariel", Font.BOLD, 36));
        time.setHorizontalAlignment(JLabel.CENTER);

        JPanel topArea = new JPanel();
        topArea.add(startPause);
        topArea.add(reset);

        window.add(topArea, BorderLayout.NORTH);
        window.add(time, BorderLayout.CENTER);

        window.setVisible(true);
    }

    public static void main(String[] args)
    {
        new Timer();
    }
}
