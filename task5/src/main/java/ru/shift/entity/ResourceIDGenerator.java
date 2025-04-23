package ru.shift.entity;

public class ResourceIDGenerator {
    private final Object lock = new Object();
    private volatile int id = 0;

    public int getNewID() {
        synchronized (lock) {
            return id++;
        }
    }
}
