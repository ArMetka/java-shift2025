package ru.shift.common.enums;

import java.io.Serializable;

public enum GameType implements Serializable {
    NOVICE("Novice", 9, 9, 10),
    MEDIUM("Medium", 16, 16, 40),
    EXPERT("Expert", 30, 16, 99),
    ;

    private final String name;
    private final int width;
    private final int height;
    private final int minesCount;

    GameType(String name, int width, int height, int minesCount) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.minesCount = minesCount;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMinesCount() {
        return minesCount;
    }
}
