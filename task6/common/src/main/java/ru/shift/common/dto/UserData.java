package ru.shift.common.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("user_data")
public record UserData(
        String name
) implements Serializable {
}
