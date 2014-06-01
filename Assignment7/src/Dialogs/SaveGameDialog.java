package Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nate on 5/31/14.
 */
public class SaveGameDialog extends JDialog implements ActionListener {
    private JButton save;
    private JButton doNotSave;

    public boolean userRequestsSaveChanges;

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
