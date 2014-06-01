package Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This model dialog asks users if they want to save or discard changes. Its used when closing the game
 * with pending changes.
 * Grading level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 7: Sudoku Serialization/Integration
 */
public class SaveGameDialog extends JDialog implements ActionListener {
    // References to buttons
    private JButton save;
    private JButton doNotSave;

    /**
     * Indicates of the user wants to save changes or not
     */
    public boolean userRequestsSaveChanges;

    /**
     * Constructor. Sets up dialog.
     * @param owner JFrame that owns this dialog.
     */
    public SaveGameDialog(JFrame owner) {
        super(owner, "Save Changes?", true);

        add(new JLabel("You have unsaved changed. Would you like to save changes?"));

        // sub-panel for buttons
        JPanel buttonsPanel = new JPanel();

        // save button
        save = new JButton("Save");
        save.addActionListener(this);
        buttonsPanel.add(save);

        // Cancel button
        doNotSave = new JButton("Don't Save");
        doNotSave.addActionListener(this);
        buttonsPanel.add(doNotSave);


        add(buttonsPanel, BorderLayout.SOUTH);

        setSize(300, 100);
        pack();
        setVisible(true);
    }

    /**
     * Mouse handler for both buttons
     * @param e event arguments
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton)e.getSource();

        if (clickedButton == save)
            userRequestsSaveChanges = true;
        else
            userRequestsSaveChanges = false;

        dispose();
    }
}
