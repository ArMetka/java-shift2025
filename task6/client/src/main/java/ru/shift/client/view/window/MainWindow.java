package ru.shift.client.view.window;

import ru.shift.client.view.event.SendMessageEvent;
import ru.shift.client.view.listener.SendMessageEventListener;
import ru.shift.common.dto.ChatMessageData;
import ru.shift.common.dto.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainWindow extends JFrame {
    private final Container container;
    private final BorderLayout layout;

    private JMenuItem connectMenu;

    private JTextArea input;
    private JPanel messages;
    private DefaultListModel<String> users;

    private JScrollPane messageScrollPane;
    private JButton sendMessageBtn;

    public MainWindow() {
        super("Chat App");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);
        setLocationRelativeTo(null);

        createMenu();

        container = getContentPane();
        layout = new BorderLayout();
        container.setLayout(layout);

        createChatGUI();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Connection");

        gameMenu.add(connectMenu = new JMenuItem("Connect"));

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void createChatGUI() {
        messages = new JPanel();
        messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
        messageScrollPane = new JScrollPane(messages);

        users = new DefaultListModel<>();
        var userList = new JList<>(users);
        userList.setFocusable(false);
        var usersScrollPane = new JScrollPane(userList);
        usersScrollPane.setPreferredSize(new Dimension(120, 0));

        input = new JTextArea(3, 30);
        var inputScrollPane = new JScrollPane(input);
        sendMessageBtn = new JButton("Send");
        sendMessageBtn.setEnabled(false);

        var inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(inputScrollPane, BorderLayout.CENTER);
        inputPanel.add(sendMessageBtn, BorderLayout.EAST);

        var mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, usersScrollPane, messageScrollPane);
        mainSplitPane.setResizeWeight(0.15);

        container.add(mainSplitPane, BorderLayout.CENTER);
        container.add(inputPanel, BorderLayout.SOUTH);
    }

    public void newMessage(ChatMessageData message) {
        addMessage(message, false);
    }

    public void userJoin(String username, Date date) {
        addMessage(new ChatMessageData(
                new UserData("Server"),
                "User \"" + username + "\" joined the chat",
                date
        ), true);
        users.addElement(username);
    }

    public void userLeave(String username, Date date) {
        addMessage(new ChatMessageData(
                new UserData("Server"),
                "User \"" + username + "\" left the chat",
                date
        ), true);
        users.removeElement(username);
    }

    private void addMessage(ChatMessageData message, boolean isServerMessage) {
        var messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout(5, 5));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        fmt.setTimeZone(TimeZone.getDefault());

        var userLabel = new JLabel(message.userData().name() + " [" + fmt.format(message.date()) + "]");
        userLabel.setFont(userLabel.getFont().deriveFont(Font.BOLD));
        if (isServerMessage) {
            userLabel.setForeground(new Color(201, 23, 23));
        }

        var contentArea = new JTextArea(message.messageContent());
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);

        messagePanel.add(userLabel, BorderLayout.NORTH);
        messagePanel.add(contentArea, BorderLayout.CENTER);

        messages.add(messagePanel);
        messages.revalidate();
        messages.repaint();
        SwingUtilities.invokeLater(() -> messageScrollPane.getVerticalScrollBar().setValue(messageScrollPane.getVerticalScrollBar().getMaximum()));
    }

    public void setUsers(List<UserData> userDataList) {
        users.removeAllElements();
        userDataList.forEach(e -> users.addElement(e.name()));
    }

    public void setSendMessageBtnEnabled(boolean enabled) {
        sendMessageBtn.setEnabled(enabled);
    }

    public void setSendMessageEventListener(SendMessageEventListener listener) {
        sendMessageBtn.addActionListener(e -> {
            var message = input.getText().trim();
            if (!message.isEmpty()) {
                listener.onSendMessage(new SendMessageEvent(message));
            }
            input.setText("");
        });
    }

    public void setConnectBtnAction(ActionListener listener) {
        connectMenu.addActionListener(listener);
    }

    public void setConnectEnabled(boolean enabled) {
        connectMenu.setEnabled(enabled);
    }
}
