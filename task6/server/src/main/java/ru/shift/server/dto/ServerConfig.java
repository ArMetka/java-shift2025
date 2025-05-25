package ru.shift.server.dto;

public record ServerConfig(
        int port, // [0; 65536], 0 -> any free port
        int maxThreads // >= 0, 0 -> no limit
) {
    public static final int PORT_MIN_VALUE = 0;
    public static final int PORT_MAX_VALUE = 65536;
    public static final int THREADS_MIN_VALUE = 0;
}
