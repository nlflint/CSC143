import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nathanf on 4/28/14.
 */
public class Timer implements ActionListener
{
    JButton startPause;
    JButton reset;
    JLabel timeText;
    TimeController timer;

    public Timer ()
    {
        JFrame window = new JFrame();
        window.setSize(400, 300);
        window.setLocation(100, 100);
        window.setLayout(new BorderLayout());
        window.setTitle("My Timer");

        startPause = new JButton("Start");
        startPause.addActionListener(this);

        reset = new JButton("Reset");
        reset.addActionListener(this);

        timeText = new JLabel();
        timeText.setFont(new Font("Ariel", Font.BOLD, 36));
        timeText.setHorizontalAlignment(JLabel.CENTER);

        JPanel topArea = new JPanel();
        topArea.add(startPause);
        topArea.add(reset);

        window.add(topArea, BorderLayout.NORTH);
        window.add(timeText, BorderLayout.CENTER);

        window.setVisible(true);

        timer = new TimeController(timeText);
    }

    public static void main(String[] args)
    {
        new Timer();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton source = (JButton) e.getSource();

        if (source == startPause && source.getText().equals("Start"))
        {
            timer.start();
            startPause.setText("Pause");
        }
        else if (source == startPause && source.getText().equals("Pause"))
        {
            timer.pause();
            startPause.setText("Start");
        }
        else if (source == reset)
        {
            timer.reset();
            startPause.setText("Start");
        }
    }

    private class TimeController implements Runnable
    {
        double currentTime;
        boolean isTimerRunning;
        Thread timerThread;
        JLabel timeLabel;

        public TimeController(JLabel timeLabel)
        {
            this.timeLabel = timeLabel;
            reset();
        }
        public void start()
        {
            timerThread = new Thread(this);
            timerThread.start();
            isTimerRunning = true;
        }

        public void pause()
        {
            isTimerRunning = false;
        }

        public void reset()
        {
            isTimerRunning = false;
            currentTime = 0.0;
            refreshTimeLabel();
        }

        private void refreshTimeLabel()
        {
            timeLabel.setText(String.format("%.2f seconds", currentTime));
        }

        @Override
        public void run() {
            while (isTimerRunning)
            {
                incrementTimeLabel();
                sleep();
            }
        }

        private void incrementTimeLabel() {
            currentTime += 0.01;
            refreshTimeLabel();
        }

        private void sleep() {
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                //error happened, stop timer
                isTimerRunning = false;
            }
        }


    }
}
