package ru.shift.server.controller;

import ru.shift.common.MessageType;

import java.util.EnumMap;
import java.util.ServiceLoader;

public abstract class ControllerRegistry {
    private static final EnumMap<MessageType, Controller> registry;

    static {
        registry = new EnumMap<>(MessageType.class);
        for (var controller : ServiceLoader.load(Controller.class)) {
            registerController(controller);
        }
    }

    public static void registerController(Controller controller) {
        for (var type : controller.getAcceptedMessageTypes()) {
            registry.put(type, controller);
        }
    }

    public static Controller findByMessageType(MessageType type) {
        return registry.get(type);
    }
}
