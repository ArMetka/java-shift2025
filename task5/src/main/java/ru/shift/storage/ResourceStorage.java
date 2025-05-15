package ru.shift.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.entity.Resource;

import java.util.ArrayDeque;
import java.util.Queue;

public class ResourceStorage implements IResourceStorage {
    private static final Logger log = LogManager.getLogger(ResourceStorage.class);

    private final Queue<Resource> storage;
    private final int capacity;

    public ResourceStorage(int capacity) {
        this.capacity = capacity;
        storage = new ArrayDeque<>(capacity);
    }

    @Override
    public synchronized void putResource(Resource resource) {
        while (storage.size() >= capacity) {
            try {
                log.debug("waiting for free space");
                wait();
                logNotify();
            } catch (InterruptedException ignore) {
            }
        }

        storage.add(resource);
        log.debug("put resource id={}, storage size={}", resource.getId(), storage.size());

        notifyAll();
    }

    @Override
    public synchronized Resource getResource() {
        while (storage.isEmpty()) {
            try {
                log.debug("waiting for new resources");
                wait();
                logNotify();
            } catch (InterruptedException ignore) {
            }
        }

        var resource = storage.remove();
        log.debug("remove resource id={}, storage size={}", resource.getId(), storage.size());

        notifyAll();

        return resource;
    }

    private void logNotify() {
        log.debug("notified");
    }
}
