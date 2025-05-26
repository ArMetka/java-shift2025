package ru.shift.client.view.window;

import ru.shift.client.ChatClient;
import ru.shift.client.view.event.ConnectEvent;
import ru.shift.client.view.listener.ConnectEventListener;

import javax.swing.*;
import java.awt.*;

public class ConnectWindow extends JDialog {
    private final GridBagConstraints gbc;

    private JTextField usernameField;
    private JTextField addressField;
    private JTextField portField;
    private JButton okButton;

    public ConnectWindow(JFrame owner) {
        super(owner, "Connect to Server", true);
        setLayout(new GridBagLayout());
        setSize(350, 200);
        setLocationRelativeTo(owner);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        createFields();
        createButton();

        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        getRootPane().setDefaultButton(okButton);
    }

    private void createFields() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Server Address:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField(ChatClient.DEFAULT_SERVER_ADDRESS, 15);
        add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Port:"), gbc);
        gbc.gridx = 1;
        portField = new JTextField(ChatClient.DEFAULT_SERVER_PORT, 15);
        add(portField, gbc);
    }

    private void createButton() {
        okButton = new JButton("Connect");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(okButton, gbc);
    }

    public void setConnectEventListener(ConnectEventListener listener) {
        okButton.addActionListener(e -> {
            if (validateFields()) {
                listener.onConnect(new ConnectEvent(
                        usernameField.getText().trim(),
                        addressField.getText().trim(),
                        Integer.parseInt(portField.getText().trim())
                ));
            }
        });
    }

    private boolean validateFields() {
        if (usernameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username cannot be empty");
            return false;
        }
        try {
            Integer.parseInt(portField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Port must be a number");
            return false;
        }
        return true;
    }
}
