package ru.shift.producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.entity.Resource;
import ru.shift.entity.ResourceIDGenerator;
import ru.shift.storage.IResourceStorage;

public class ResourceProducer implements Runnable {
    private static final Logger log = LogManager.getLogger(ResourceProducer.class);

    private final IResourceStorage storage;
    private final ResourceIDGenerator idGenerator;
    private final int interval;

    public ResourceProducer(IResourceStorage storage, ResourceIDGenerator idGenerator, int interval) {
        this.storage = storage;
        this.idGenerator = idGenerator;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            produceResource();
        }
    }

    private void produceResource() {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ignore) {
        }

        var resource = new Resource(idGenerator.getNewID());
        log.info("produced resource id={}", resource.getId());
        storage.putResource(resource);
    }
}
