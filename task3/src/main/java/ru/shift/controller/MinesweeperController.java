package ru.shift.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.common.dto.CellPosition;
import ru.shift.model.IMinesweeperModel;
import ru.shift.view.IMinesweeperView;
import ru.shift.view.enums.ViewEventType;
import ru.shift.view.event.CellEvent;
import ru.shift.view.event.GameTypeEvent;
import ru.shift.view.event.NewGameEvent;
import ru.shift.view.listener.CellEventListener;
import ru.shift.view.listener.GameTypeListener;
import ru.shift.view.listener.NewGameListener;

public class MinesweeperController implements IMinesweeperController {
    private static final Logger log = LogManager.getLogger(MinesweeperController.class);
    private final IMinesweeperModel model;
    private final IMinesweeperView view;

    public MinesweeperController(IMinesweeperModel model, IMinesweeperView view) {
        this.model = model;
        this.view = view;
        subscribeToView();
    }

    private void subscribeToView() {
        view.addViewEventListener(ViewEventType.CELL, (CellEventListener) this::handleCellEvent);
        view.addViewEventListener(ViewEventType.GAME_TYPE, (GameTypeListener) this::handleGameTypeEvent);
        view.addViewEventListener(ViewEventType.NEW_GAME, (NewGameListener) this::handleNewGameEvent);
    }

    private void handleCellEvent(CellEvent e) {
        var pos = new CellPosition(e.getX(), e.getY());

        log.debug("new {} click at {}, {}", e.getButtonType(), pos.row(), pos.col());
        switch (e.getButtonType()) {
            case LEFT_BUTTON -> model.openCell(pos);
            case RIGHT_BUTTON -> model.markCell(pos);
            case MIDDLE_BUTTON -> model.easyOpenCell(pos);
        }
    }

    private void handleGameTypeEvent(GameTypeEvent e) {
        log.debug("game type changed to {}", e.getGameType());
        model.changeGameType(e.getGameType());
    }

    private void handleNewGameEvent(NewGameEvent e) {
        log.debug("creating new game");
        model.createNewGame();
    }
}
