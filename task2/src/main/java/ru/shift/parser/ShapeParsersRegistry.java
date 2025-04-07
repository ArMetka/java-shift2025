package ru.shift.parser;

import ru.shift.dto.ShapeData;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public abstract class ShapeParsersRegistry {
    private static final Map<String, ShapeParser> registry;

    static {
        registry = new HashMap<>();
        for (var parser : ServiceLoader.load(ShapeParser.class)) {
            registerShapeParser(parser);
        }
    }

    public static void registerShapeParser(ShapeParser parser) {
        registry.put(parser.getCode(), parser);
    }

    public static ShapeParser findByShapeData(ShapeData data) {
        return registry.get(data.getCode());
    }

    public static ShapeParser findByCode(String code) {
        return registry.get(code);
    }
}
