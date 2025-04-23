package ru.shift.common.dto;

import ru.shift.common.enums.GameType;

import java.io.Serializable;

public record HighScore(GameType gameType, String name, int time) implements Serializable {

    public static HighScore emptyScore(GameType gameType) {
        return new HighScore(gameType, "No score", 999);
    }
}
