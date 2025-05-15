package ru.shift;

import ru.shift.consumer.ResourceConsumer;
import ru.shift.dto.ProductionData;
import ru.shift.entity.ResourceIDGenerator;
import ru.shift.producer.ResourceProducer;
import ru.shift.storage.IResourceStorage;
import ru.shift.storage.ResourceStorage;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadProduction {
    private static final String PRODUCER_PREFIX = "producer-";
    private static final String CONSUMER_PREFIX = "consumer-";

    private final IResourceStorage storage;
    private final ResourceIDGenerator idGenerator;
    private final List<Thread> producers;
    private final List<Thread> consumers;
    private final ProductionData data;

    public MultiThreadProduction(ProductionData data) {
        this.data = data;
        storage = new ResourceStorage(data.storageSize());
        idGenerator = new ResourceIDGenerator();
        producers = new ArrayList<>(data.producerCount());
        consumers = new ArrayList<>(data.consumerCount());
        createProducers();
        createConsumers();
    }

    private void createProducers() {
        for (int i = 0; i < data.producerCount(); i++) {
            producers.add(new Thread(
                    new ResourceProducer(storage, idGenerator, data.producerTime()),
                    PRODUCER_PREFIX + (i + 1)
            ));
        }
    }

    private void createConsumers() {
        for (int i = 0; i < data.consumerCount(); i++) {
            producers.add(new Thread(
                    new ResourceConsumer(storage, data.consumerTime()),
                    CONSUMER_PREFIX + (i + 1)
            ));
        }
    }

    public void start() {
        for (var producer : producers) {
            producer.start();
        }
        for (var consumer : consumers) {
            consumer.start();
        }
    }
}
