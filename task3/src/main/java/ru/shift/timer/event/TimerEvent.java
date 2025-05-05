package ru.shift.timer.event;

public class TimerEvent {
    private final int secondsElapsed;

    public TimerEvent(int secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }

    public int getSecondsElapsed() {
        return secondsElapsed;
    }
}
