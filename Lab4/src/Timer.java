import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Stop watch UI
 *
 * @author Nathan Flint
 * @version Lab4: Timer
 */
public class Timer implements ActionListener
{
    // Start / Pause / Continue button
    private JButton startPause;

    // Reset button
    private JButton reset;

    // Text that shows time
    private JLabel timeText;

    // The underlying stop watch
    private StopWatch timer;

    // Static text of buttons
    private static final String START = "Start";
    private static final String PAUSE = "Pause";
    private static final String CONTINUE = "Continue";
    private static final String RESET = "Reset";

    /**
     * Constructor. Sets up UI and stop watch
     */
    public Timer ()
    {
        configureUI();
        configureStopWatch();
    }

    // Sets up up window and buttons
    private void configureUI()
    {
        // make window
        JFrame window = new JFrame();
        window.setSize(400, 300);
        window.setLocation(100, 100);
        window.setTitle("My Timer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Build buttons
        startPause = new JButton(START);
        startPause.addActionListener(this);
        reset = new JButton(RESET);
        reset.addActionListener(this);

        // Build label for showing elapsed time
        timeText = new JLabel();
        timeText.setFont(new Font("Ariel", Font.BOLD, 36));
        timeText.setHorizontalAlignment(JLabel.CENTER);
        timeText.setText("0.00 seconds");

        // This panel holds buttons along top of window area
        JPanel topArea = new JPanel();
        topArea.add(startPause);
        topArea.add(reset);

        // Add items to window
        window.add(topArea, BorderLayout.NORTH);
        window.add(timeText, BorderLayout.CENTER);
        window.setVisible(true);
    }

    // Sets up the the stop watch
    private void configureStopWatch()
    {
        long millisecondsPerTic = 10;
        timer = new StopWatch(millisecondsPerTic);
        // Stopwatch will trigger events on every tic.
        timer.addTickEvent(new StopWatchTickEvent() {
            @Override
            public void tick() {
                timeText.setText(String.format("%.2f seconds", timer.getElapsedTime()));
            }
        });
    }

    /**
     * Application method
     * @param args no command line args are not implemented.
     */
    public static void main(String[] args)
    {
        // Starts program
        new Timer();
    }

    /**
     * Handles logic for when any buttons are clicked
     * @param e information about the event
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Gathering basic info from the event
        JButton source = (JButton) e.getSource();
        String buttonText = ((JButton) e.getSource()).getText();

        // start or Continue button were pressed.
        if (source == startPause && (buttonText.equals(START) || buttonText.equals(CONTINUE)))
        {
            // start/continue the stop watch. behaviors are identical.
            timer.start();
            // Change button to say pause
            startPause.setText(PAUSE);
        }
        // Pause was pressed
        else if (source == startPause && buttonText.equals(PAUSE))
        {
            // Pause stop watch
            timer.pause();
            // Change button to say continue
            startPause.setText(CONTINUE);
        }
        // Reset was pressed
        else if (source == reset)
        {
            // reset stopwatch
            timer.reset();
            // Change button to say Start
            startPause.setText(START);
            // Set focus to start button
            startPause.grabFocus();
        }
    }
}

/**
 * Implements a stop watch
 *
 * @author Nathan Flint
 * @version Lab4: Timer
 */
class StopWatch
{
    // time reading from the stop watch
    private double elapsedSeconds;

    // Flag tracks if stop watch is running
    private boolean timerIsRunning;

    // Tread the stop watch uses while running
    private Thread timerThread;

    // Collection of events that are triggered
    private ArrayList<StopWatchTickEvent> tickEvents;

    // Time between stop watch tics
    private long ticMilliseconds;

    /**
     * Constructor. Sets up initial settings, and resets stop watch.
     * @param milliseconds time between clock ticks
     */
    public StopWatch(long milliseconds)
    {
        tickEvents = new ArrayList<StopWatchTickEvent>();
        ticMilliseconds = milliseconds;
        timerIsRunning = false;
        elapsedSeconds = 0.0;
    }

    /**
     * Stops watch and sets elapsed time to 0.
     */
    public void reset()
    {
        timerIsRunning = false;
        elapsedSeconds = 0.0;
        triggerTickEvents();
    }

    // Tiggers every event in the collection
    private void triggerTickEvents() {
        for (StopWatchTickEvent event : tickEvents)
            event.tick();
    }

    /**
     * Starts the stop watch.
     * Continues if watch is paused.
     * Does nothing if watch is already running.
     */
    public void start()
    {
        if (timerIsRunning)
            return;

        timerThread = new Thread(
                new Runnable() {
                    public void run() {
                        while (timerIsRunning)
                        {
                            incrementTime();
                            sleep();
                        }
                    }
                });

        timerThread.start();
        timerIsRunning = true;
    }

    // Increments elapsed time by tic amount
    private void incrementTime() {
        elapsedSeconds += ticMilliseconds / 1000.0;
        triggerTickEvents();
    }

    // Sleeps for the tic amount
    private void sleep() {
        // how long we want to sleep for.
        long millisecondsRemaining = ticMilliseconds;
        // When the wait time is over
        long wakeUpTime = System.currentTimeMillis() + ticMilliseconds;

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
                //error happened, stop timer
                timerIsRunning = false;
            }
            // update time left until wakeup time.
            millisecondsRemaining = wakeUpTime - System.currentTimeMillis();
        }
    }

    /**
     * Pauses stop watch if it's running.
     * Does nothing if it's already paused.
     */
    public void pause() { timerIsRunning = false; }

    /**
     * Gets time elapsed in seconds
     * @return elapsed time in seconds
     */
    public double getElapsedTime() { return elapsedSeconds; }

    /**
     * Adds an event to the event list.
     * @param event Event to be triggered at every tic
     */
    public void addTickEvent(StopWatchTickEvent event) { tickEvents.add(event); }
}

/**
 * Interface for event triggered on every tic of the stop watch
 */
interface StopWatchTickEvent {
    /**
     * Method is executed every time event is fired
     */
    public void tick();
}
