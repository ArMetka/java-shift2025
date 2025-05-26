package ru.shift.client.model;

import ru.shift.client.model.enums.ModelEventType;
import ru.shift.client.model.listener.ModelEventListener;

public interface IModelEventPublisher {
    void addModelEventListener(ModelEventType type, ModelEventListener listener);
}
