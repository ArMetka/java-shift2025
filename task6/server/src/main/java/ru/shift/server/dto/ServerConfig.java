package ru.shift.server.dto;

public record ServerConfig(
        int port, // [0; 65536], 0 -> any free port
        int maxThreads // >= 0, 0 -> no limit
) {
}
