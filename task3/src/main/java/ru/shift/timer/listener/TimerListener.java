package ru.shift.timer.listener;

import ru.shift.timer.event.TimerEvent;

public interface TimerListener {
    void onTimeUpdate(TimerEvent e);
}
