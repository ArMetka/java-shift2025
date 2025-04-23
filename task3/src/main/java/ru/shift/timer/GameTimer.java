package ru.shift.timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.timer.event.TimerEvent;
import ru.shift.timer.listener.TimerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer implements IGameTimer {
    private static final Logger log = LogManager.getLogger(GameTimer.class);

    private final List<TimerListener> listeners = new ArrayList<>();
    private Timer timer;
    private long timeStart;
    private volatile int secondsElapsed;

    @Override
    public void addTimerListener(TimerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void startTimer() {
        if (timer != null) {
            log.warn("attempt to start already running timer");
            return;
        }

        timer = new Timer();
        timeStart = System.currentTimeMillis();
        secondsElapsed = 0;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                secondsElapsed = (int) ((System.currentTimeMillis() - timeStart) / 1000);
                notifyTimerListeners();
            }
        }, 0, 500);
    }

    @Override
    public void stopTimer() {
        if (timer == null) {
            log.warn("attempt to stop already stopped timer");
            return;
        }

        timer.cancel();
        timer = null;
    }

    private void notifyTimerListeners() {
        for (var listener : listeners) {
            listener.onTimeUpdate(new TimerEvent(secondsElapsed));
        }
    }
}
