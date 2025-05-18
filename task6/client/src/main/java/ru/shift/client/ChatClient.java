package ru.shift.client;

import ru.shift.client.controller.ChatController;
import ru.shift.client.model.ChatModel;
import ru.shift.client.view.ChatView;

public class ChatClient {
    public ChatClient() {
        var model = new ChatModel();
        var view = new ChatView(model);
        new ChatController(model, view);
        view.setVisible(true);
    }
}
