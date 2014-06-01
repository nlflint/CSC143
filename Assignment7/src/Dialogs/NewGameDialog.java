package Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A dialog for for getting dimensions when creating a new game.
 */
public class NewGameDialog extends JDialog implements ActionListener {

    // Values gathered by user are stored here.
    private int rows;
    private int columns;

    // true if ok clicked, false if cancel was clicked.
    private boolean okClicked;

    // Fields in the dialog.
    private JTextField rowsInput;
    private JTextField columnsInput;

    private JButton ok;
    private JButton cancel;

    /**
     * Contructor.
     * @param owner The owner from which the dialog is displayed
     */
    public NewGameDialog(JFrame owner) {
        super(owner, "New Game", true);

        // row input
        rowsInput = new JTextField("3", 3);
        JPanel rowsPanel = new JPanel();
        rowsPanel.add(new JLabel("Rows: "));
        rowsPanel.add(rowsInput);

        // column input
        columnsInput = new JTextField("3", 3);
        JPanel columnsPanel = new JPanel();
        columnsPanel.add(new JLabel("Columns: "));
        columnsPanel.add(columnsInput);

        // sub-panel for the input windows and labels.
        JPanel inputsPanel = new JPanel();
        inputsPanel.add(rowsPanel);
        inputsPanel.add(columnsPanel);
        add(inputsPanel);

        // sub-panel for buttons
        JPanel buttonsPanel = new JPanel();

        // ok button
        ok = new JButton("OK");
        ok.addActionListener(this);
        buttonsPanel.add(ok);

        // Cancel button
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttonsPanel.add(cancel);


        add(buttonsPanel, BorderLayout.SOUTH);

        setSize(300, 100);
        setVisible(true);
        pack();
    }

    /**
     * Handles button clicks for ok and cancel buttons
     * @param e event information
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // determines is ok or cancel was clicked
        if(src == ok) {
            okPressed();
        } else if(src == cancel) {
            cancelPressed();
        }
    }

    // Ok was pressed. validate inputs.
    private void okPressed() {
        try {
            //try to parse
            rows = Integer.parseInt(rowsInput.getText());
            columns = Integer.parseInt(columnsInput.getText());

            // validate values
            if (!rowIsValid(rows))
                showInvalidRowMessage();
            else if (!columnisValid(columns))
                showInvalidColumnMessage();
            else if (!inputSizeValid(rows * columns))
                // Values are unacceptable, asks for good values
                showWrongSizeMessage();
            else {
                okClicked = true;
                dispose();
            }
        } catch (Exception ex) {
            // Parsing failed, values are unacceptable. asks for good values
            showWrongInputMessage();
        }
    }

    private void showWrongSizeMessage() {
        String errorLineOne = "Row times columns must be greater than 0 and less than 13.";
        JOptionPane.showMessageDialog(null, errorLineOne);
    }

    private void showInvalidColumnMessage() {
        String errorLineOne = "Column must be greater than 0 and less than 5.";
        JOptionPane.showMessageDialog(null, errorLineOne);
    }

    private boolean columnisValid(int columns) {
        return columns > 0 && columns < 5;
    }

    private void showInvalidRowMessage() {
        String errorLineOne = "Row must be greater than 0 and less than 5.";
        JOptionPane.showMessageDialog(null, errorLineOne);
    }

    // Validates row
    private boolean rowIsValid(int rows) {
        return rows > 0 && rows < 5;
    }

    // Cancel was pressed. Close the window and indicate that OK was NOT clicked.
    private void cancelPressed() {
        okClicked = false;
        dispose();
    }

    // Validates given row and column
    private boolean inputSizeValid(int size) {
        return size > 0 && size < 13;
    }

    // Shows a dialog that asks for good input
    private void showWrongInputMessage() {
        String errorLineOne = "Rows and Columns must be positive whole numbers that\n";
        String errorLineTwo = "when multiplied together are greater than 1 and less than 13.";
        JOptionPane.showMessageDialog(null, errorLineOne + errorLineTwo);
    }

    /**
     * Get rows input by user
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get Columns input by the user
     * @return
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Determine if OK was clicked.
     * @return true is OK was clicked. False if ok was NOT clicked.
     */
    public boolean okWasClicked() {
        return okClicked;
    }

}
