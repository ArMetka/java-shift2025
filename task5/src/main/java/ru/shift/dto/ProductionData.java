package ru.shift.dto;

public record ProductionData(
        int producerCount,
        int consumerCount,
        int producerTime,
        int consumerTime,
        int storageSize
) {
}
