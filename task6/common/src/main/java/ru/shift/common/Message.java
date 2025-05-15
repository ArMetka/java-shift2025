package ru.shift.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public interface Message extends Serializable {
    @JsonIgnore
    MessageType getType();
}
