package ru.shift.timer;

import ru.shift.timer.listener.TimerListener;

public interface IGameTimer {
    void addTimerListener(TimerListener listener);

    void startTimer();

    void stopTimer();
}
