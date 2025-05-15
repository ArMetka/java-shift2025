package ru.shift.client;

import ru.shift.client.controller.ChatController;
import ru.shift.client.controller.IChatController;
import ru.shift.client.model.ChatModel;
import ru.shift.client.model.IChatModel;
import ru.shift.client.service.MessageService;
import ru.shift.client.view.ChatView;
import ru.shift.client.view.IChatView;

public class ChatClient {
    public ChatClient() {
        IChatModel model = new ChatModel();

        IChatView view = new ChatView(model);

        IChatController controller = new ChatController(model, view);

        new MessageService(model, controller);

        view.setVisible(true);
    }
}
