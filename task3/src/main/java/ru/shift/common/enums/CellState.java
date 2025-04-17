package ru.shift.common.enums;

import ru.shift.view.enums.GameImage;

public enum CellState {
    CLOSED,
    OPEN,
    MARKED,
    MINE,
    ;

    private int minesNearby;

    public int getMinesNearby() {
        return minesNearby;
    }

    public CellState setMinesNearby(int minesNearby) {
        this.minesNearby = minesNearby;
        return this;
    }

    public GameImage toGameImage() {
        return switch (this) {
            case CLOSED -> GameImage.CLOSED;
            case MARKED -> GameImage.MARKED;
            case MINE -> GameImage.BOMB;
            case OPEN -> switch (minesNearby) {
                case 0 -> GameImage.EMPTY;
                case 1 -> GameImage.NUM_1;
                case 2 -> GameImage.NUM_2;
                case 3 -> GameImage.NUM_3;
                case 4 -> GameImage.NUM_4;
                case 5 -> GameImage.NUM_5;
                case 6 -> GameImage.NUM_6;
                case 7 -> GameImage.NUM_7;
                case 8 -> GameImage.NUM_8;
                default -> throw new IllegalArgumentException("unexpected value " + minesNearby);
            };
        };
    }
}
