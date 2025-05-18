package ru.shift.common.connection;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.common.exception.ConnectionException;
import ru.shift.common.exception.MappingException;
import ru.shift.common.message.Message;
import ru.shift.common.message.MessageMapper;
import ru.shift.common.message.Request;
import ru.shift.common.message.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ChatConnection implements AutoCloseable {
    private static final Logger log = LogManager.getLogger(ChatConnection.class);

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final MessageMapper mapper;
    private final ConcurrentHashMap<String, CompletableFuture<Response>> activeRequests;

    @Setter
    @Getter
    private boolean shouldListen = true;

    public ChatConnection(Socket socket, MessageMapper mapper) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.mapper = mapper;
        activeRequests = new ConcurrentHashMap<>();
    }

    public void sendMessage(Message message) {
        var msg = mapper.serialize(message);
        log.debug("sending message: {}", msg);
        out.println(msg);
    }

    public void readMessagesLoop(Consumer<Message> consumer) {
        var address = socket.getInetAddress();
        var port = socket.getPort();

        log.info("reading messages from {}:{} in a loop", address, port);
        while (shouldListen) {
            Message msg;
            try {
                var line = in.readLine();
                if (line == null) {
                    break;
                }
                msg = mapper.deserialize(line);
            } catch (MappingException e) {
                log.warn("received bad massage", e);
                continue;
            } catch (IOException e) {
                log.error("IO error while reading message", e);
                break;
            }
            log.debug("received new message from {}:{} of type {}", address, port, msg.getType().name());

            if (msg.isNotification() || msg.isRequest()) {
                consumer.accept(msg);
            } else if (msg.isResponse()) {
                var response = (Response) msg;
                var request = activeRequests.remove(response.getID());
                if (request == null) {
                    throw new ConnectionException("received response to non-existent request");
                }
                request.complete(response);
            } else {
                log.warn("received bad message of type {}", msg.getType().name());
            }
        }
        log.info("stopped reading from {}:{}", address, port);
    }

    public CompletableFuture<Response> sendRequestAsync(Request request) {
        var future = new CompletableFuture<Response>().orTimeout(15, TimeUnit.SECONDS);
        activeRequests.put(request.getID(), future);
        try {
            sendMessage(request);
        } catch (Exception e) {
            future.completeExceptionally(e);
        }

        return future.handle((response, e) -> {
            activeRequests.remove(request.getID());
            if (e != null) {
                throw new RuntimeException(e);
            }
            return response;
        });
    }

    @Override
    public void close() throws Exception {
        if (socket != null && !socket.isClosed()) {
            socket.shutdownInput();
        }
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
