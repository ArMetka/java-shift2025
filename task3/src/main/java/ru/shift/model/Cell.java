package ru.shift.model;

import ru.shift.common.dto.CellPosition;

public class Cell {
    private final CellPosition pos;
    private int nearbyMinesCount;
    private boolean isMine;
    private boolean isOpen;
    private boolean isMarked;

    public Cell(CellPosition pos) {
        this.pos = pos;
    }

    public Cell(int row, int col) {
        this.pos = new CellPosition(row, col);
    }

    public CellPosition getPos() {
        return pos;
    }

    public int getNearbyMinesCount() {
        return nearbyMinesCount;
    }

    public void setNearbyMinesCount(int nearbyMinesCount) {
        this.nearbyMinesCount = nearbyMinesCount;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean isMarked) {
        this.isMarked = isMarked;
    }
}
