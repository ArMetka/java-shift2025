package ru.shift.common.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("error_data")
public record ErrorData(
        String description
) implements Serializable {
}
