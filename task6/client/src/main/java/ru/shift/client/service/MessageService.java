package ru.shift.client.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.client.controller.IChatController;
import ru.shift.client.model.IModelEventPublisher;
import ru.shift.client.model.enums.ModelEventType;
import ru.shift.client.model.event.ConnectionInitEvent;
import ru.shift.client.model.event.DisconnectEvent;
import ru.shift.client.model.event.SendMessageEvent;
import ru.shift.client.model.listener.ConnectionFailEventListener;
import ru.shift.client.model.listener.ConnectionInitEventListener;
import ru.shift.client.model.listener.DisconnectEventListener;
import ru.shift.client.model.listener.SendMessageEventListener;
import ru.shift.common.ServerMessage;
import ru.shift.common.dto.TokenData;
import ru.shift.common.dto.UserData;
import ru.shift.common.messages.AuthRequest;
import ru.shift.common.messages.ChatMessage;
import ru.shift.common.messages.Disconnect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MessageService {
    private static final Logger log = LogManager.getLogger(MessageService.class);
    private final IModelEventPublisher model;
    private final IChatController controller;
    private final ObjectMapper mapper = new ObjectMapper();

    private volatile Socket socket;
    private volatile boolean isReading = false;
    private OutputStream out;

    public MessageService(IModelEventPublisher model, IChatController controller) {
        this.model = model;
        this.controller = controller;
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        subscribeToModel();
    }

    private void subscribeToModel() {
        model.addModelEventListener(ModelEventType.CONNECTION_INIT, (ConnectionInitEventListener) this::initConnection);
        model.addModelEventListener(ModelEventType.SEND_MESSAGE, (SendMessageEventListener) this::sendMessage);
        model.addModelEventListener(ModelEventType.CONNECTION_FAIL, (ConnectionFailEventListener) e -> closeSocket());
        model.addModelEventListener(ModelEventType.DISCONNECT, (DisconnectEventListener) this::disconnect);
    }

    private void disconnect(DisconnectEvent event) {
        try {
            mapper.writeValue(out, new Disconnect(null));
        } catch (Exception e) {
            log.error("failed to send disconnect message: {}", e.getMessage());
        } finally {
            closeSocket();
        }
    }

    private void sendMessage(SendMessageEvent event) {
        try {
            mapper.writeValue(out, new ChatMessage(
                    new TokenData(event.sessionToken()),
                    event.chatMessageData()
            ));
        } catch (Exception e) {
            log.error("failed to send message: {}", e.getMessage());
            closeSocket();
        }
    }

    private void initConnection(ConnectionInitEvent event) {
        try {
            socket = new Socket(event.address(), event.port());
            out = socket.getOutputStream();
            var in = socket.getInputStream();
            isReading = true;
            new Thread(() -> readMessages(in)).start();
            mapper.writeValue(out, new AuthRequest(new UserData(event.username())));
        } catch (Exception e) {
            log.error("failed to init connection: {}", e.getMessage());
            controller.handleConnectionFail("failed to init connection: " + e.getMessage());
            closeSocket();
        }
    }

    private void readMessages(InputStream in) {
        try {
            MappingIterator<ServerMessage> it = mapper.readerFor(ServerMessage.class).readValues(in);
            while (isReading) {
                var msg = it.next();
                log.debug("received new {} message", msg.getType().name());
                controller.handleServerMessage(msg);
            }
        } catch (Exception e) {
            log.error("failed to read message: {}", e.getMessage());
        } finally {
            isReading = false;
            log.debug("stopped reading");
            closeSocket();
        }
    }

    private void closeSocket() {
        isReading = false;
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                log.error("failed to close socket: {}", e.getMessage());
            }
        }
    }
}
