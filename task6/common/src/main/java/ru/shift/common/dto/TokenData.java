package ru.shift.common.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("token_data")
public record TokenData(
        String token
) implements Serializable {
}
