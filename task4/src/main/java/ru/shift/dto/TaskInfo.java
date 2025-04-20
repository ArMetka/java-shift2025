package ru.shift.dto;

import java.util.function.Function;

public record TaskInfo(
        Function<Long, Double> function,
        long low, // Inclusive
        long high, // Exclusive
        long threshold
) {
}
