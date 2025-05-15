package ru.shift.server.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class UserContext implements AutoCloseable {
    private final Socket clientSocket;

    private final OutputStream out;
    private final InputStream in;

    private String username = null;
    private String sessionToken = null;
    private boolean disconnected = false;
    private boolean authorized = false;

    public UserContext(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        out = clientSocket.getOutputStream();
        in = clientSocket.getInputStream();
    }

    public synchronized String getUsername() {
        return username;
    }

    public synchronized void setUsername(String username) {
        this.username = username;
    }

    public synchronized String getSessionToken() {
        return sessionToken;
    }

    public synchronized void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public synchronized boolean isDisconnected() {
        return disconnected;
    }

    public synchronized void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    public synchronized OutputStream getOut() {
        return out;
    }

    public synchronized InputStream getIn() {
        return in;
    }

    public synchronized boolean isAuthorized() {
        return authorized;
    }

    public synchronized void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    @Override
    public void close() throws Exception {
        if (!clientSocket.isClosed()) {
            clientSocket.close();
        }
        disconnected = true;
    }
}
