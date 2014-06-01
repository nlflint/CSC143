package Controllers;

import Dialogs.GameSetupDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the view and controller for setting givens, aka Setup Mode.
 * Grading level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 7: Sudoku Serialization/Integration
 */
public class SetGivensViewController extends JPanel {
    // Used to cancel setup mode
    private GameSetupDialog setupDialog;

    /**
     * Constructor.
     * @param dialog A game dialog
     */
    public SetGivensViewController(GameSetupDialog dialog) {
        super();
        // Save variables
        this.setupDialog = dialog;

        // Initializes the layout, buttons and labels.
        setLayout(new BorderLayout());

        JLabel setupModeText = new JLabel("Setup Mode");
        setupModeText.setFont(new Font("Helvetica", Font.BOLD, 24));
        setupModeText.setForeground(Color.red);
        setupModeText.setHorizontalAlignment(SwingConstants.CENTER);
        add(setupModeText, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();

        // Creates cancel button
        JButton cancelGivens = new JButton("Cancel");
        cancelGivens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupDialog.cancel();

            }
        });
        buttonsPanel.add(cancelGivens);

        // Creates set givens button
        JButton setGivens = new JButton("Set Givens");
        setGivens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupDialog.setGivens();
            }
        });
        buttonsPanel.add(setGivens);

        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
