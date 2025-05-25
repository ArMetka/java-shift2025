package ru.shift.client;

import ru.shift.client.controller.ChatController;
import ru.shift.client.model.ChatModel;
import ru.shift.client.view.ChatView;

public class ChatClient {
    public static final String DEFAULT_SERVER_ADDRESS = "localhost";
    public static final String DEFAULT_SERVER_PORT = "3242";

    public ChatClient() {
        var model = new ChatModel();
        var view = new ChatView(model);
        new ChatController(model, view);
        view.setVisible(true);
    }
}
