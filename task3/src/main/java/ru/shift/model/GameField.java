package ru.shift.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.common.dto.CellPosition;
import ru.shift.model.exception.InvalidGameFieldParametersException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameField {
    private static final Logger log = LogManager.getLogger(GameField.class);

    private final Cell[][] cells;
    private final List<Cell> mines;
    private final int rowsCount;
    private final int colsCount;
    private final int minesCount;
    private int markCount = 0;
    private int closedCellsLeft;

    public GameField(int rowsCount, int colsCount, int minesCount) {
        if (rowsCount * colsCount < minesCount) {
            throw new InvalidGameFieldParametersException(
                    "game field does not have enough space for " + minesCount + " mines"
            );
        }

        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
        this.minesCount = minesCount;
        closedCellsLeft = rowsCount * colsCount - minesCount;
        cells = new Cell[rowsCount][colsCount];
        mines = new ArrayList<>(minesCount);

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void generateField(CellPosition firstOpenedCell) {
        createMines(firstOpenedCell);
        countNearbyMines();
    }

    public Cell getCell(CellPosition pos) {
        return cells[pos.row()][pos.col()];
    }

    public void setCellOpen(Cell cell) {
        log.trace("open at {} {}", cell.getPos().row(), cell.getPos().col());
        cell.setOpen(true);
        closedCellsLeft -= 1;
    }

    public boolean toggleCellMark(CellPosition pos) {
        Cell cell = cells[pos.row()][pos.col()];

        if (cell.isMarked()) {
            cell.setMarked(false);
            markCount -= 1;
            return true;
        } else if (markCount < minesCount) {
            cell.setMarked(true);
            markCount += 1;
            return true;
        }
        return false;
    }

    public Set<Cell> getCellsToChainOpen(CellPosition pos) {
        Set<Cell> result = new HashSet<>();
        Set<CellPosition> visited = new HashSet<>();

        findLinkedCellsToChainOpen(pos, visited, result);

        for (var cell : result) {
            if (cell.isMarked() && cell.getNearbyMinesCount() == 0) {
                cell.setMarked(false);
                markCount -= 1;
            }
        }

        return result;
    }

    public List<Cell> getCellsToEasyOpen(CellPosition pos) {
        List<Cell> result = getCellsAround(pos.row(), pos.col());

        result.removeIf(cell -> cell.isOpen() || cell.isMarked());

        return result;
    }

    public int getNearbyMarksCount(CellPosition pos) {
        int result = 0;

        for (var cell : getCellsAround(pos.row(), pos.col())) {
            if (cell.isMarked()) {
                result += 1;
            }
        }

        return result;
    }

    public boolean checkAllCellsOpen() {
        log.trace("closed cells left: {}", closedCellsLeft);
        return closedCellsLeft == 0;
    }

    private void createMines(CellPosition ignore) {
        for (int i = 0; i < minesCount; i++) {
            boolean isCreated = false;

            while (!isCreated) {
                final int row = (int) (Math.random() * (rowsCount - 1));
                final int col = (int) (Math.random() * (colsCount - 1));

                if (!cells[row][col].isMine() && row != ignore.row() && col != ignore.col()) {
                    cells[row][col].setMine(true);
                    mines.add(cells[row][col]);
                    isCreated = true;
                }
            }
        }
    }

    private void countNearbyMines() {
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                if (cells[i][j].isMine()) {
                    for (var cell : getCellsAround(i, j)) {
                        cell.setNearbyMinesCount(cell.getNearbyMinesCount() + 1);
                    }
                }
            }
        }
    }

    private List<Cell> getCellsAround(int row, int col) {
        List<Cell> result = new ArrayList<>(8);

        if (row != 0 && col != 0) { // not upper-left
            result.add(cells[row - 1][col - 1]);
        }
        if (row != 0 && col != colsCount - 1) { // not upper-right
            result.add(cells[row - 1][col + 1]);
        }
        if (row != rowsCount - 1 && col != colsCount - 1) { // not bottom-right
            result.add(cells[row + 1][col + 1]);
        }
        if (row != rowsCount - 1 && col != 0) { // not bottom-left
            result.add(cells[row + 1][col - 1]);
        }

        if (row != 0) { // not first row
            result.add(cells[row - 1][col]);
        }
        if (col != 0) { // not first col
            result.add(cells[row][col - 1]);
        }

        if (row != rowsCount - 1) { // not last row
            result.add(cells[row + 1][col]);
        }
        if (col != colsCount - 1) { // not last col
            result.add(cells[row][col + 1]);
        }

        return result;
    }

    private void findLinkedCellsToChainOpen(CellPosition pos, Set<CellPosition> visited, Set<Cell> result) {
        if (!visited.add(pos)) {
            return;
        }

        Cell cell = cells[pos.row()][pos.col()];
        if (cell.isOpen() || cell.isMine()) {
            return;
        }

        result.add(cell);

        if (cell.getNearbyMinesCount() != 0) {
            return;
        }

        if (pos.row() != 0 && pos.col() != 0) { // not upper-left
            findLinkedCellsToChainOpen(new CellPosition(pos.row() - 1, pos.col() - 1), visited, result);
        }
        if (pos.row() != 0 && pos.col() != colsCount - 1) { // not upper-right
            findLinkedCellsToChainOpen(new CellPosition(pos.row() - 1, pos.col() + 1), visited, result);
        }
        if (pos.row() != rowsCount - 1 && pos.col() != colsCount - 1) { // not bottom-right
            findLinkedCellsToChainOpen(new CellPosition(pos.row() + 1, pos.col() + 1), visited, result);
        }
        if (pos.row() != rowsCount - 1 && pos.col() != 0) { // not bottom-left
            findLinkedCellsToChainOpen(new CellPosition(pos.row() + 1, pos.col() - 1), visited, result);
        }

        if (pos.row() != 0) { // not first row
            findLinkedCellsToChainOpen(new CellPosition(pos.row() - 1, pos.col()), visited, result);
        }
        if (pos.col() != 0) { // not first col
            findLinkedCellsToChainOpen(new CellPosition(pos.row(), pos.col() - 1), visited, result);
        }

        if (pos.row() != rowsCount - 1) { // not last row
            findLinkedCellsToChainOpen(new CellPosition(pos.row() + 1, pos.col()), visited, result);
        }
        if (pos.col() != colsCount - 1) { // not last col
            findLinkedCellsToChainOpen(new CellPosition(pos.row(), pos.col() + 1), visited, result);
        }
    }

    public int getMarkCount() {
        return markCount;
    }

    public List<Cell> getMines() {
        return mines;
    }
}
