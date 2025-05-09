package ru.shift.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.storage.IResourceStorage;

public class ResourceConsumer implements Runnable {
    private static final Logger log = LogManager.getLogger(ResourceConsumer.class);

    private final IResourceStorage storage;
    private final int interval;

    public ResourceConsumer(IResourceStorage storage, int interval) {
        this.storage = storage;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            consumeResource();
        }
    }

    private void consumeResource() {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ignore) {
        }

        var resource = storage.getResource();
        log.info("consumed resource id={}", resource.getId());
    }
}
