package ru.shift.common.message;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "msg_type")
@JsonPropertyOrder({"type"})
public interface Message extends Serializable {
    MessageType getType();

    MessageCategory getCategory();
}
