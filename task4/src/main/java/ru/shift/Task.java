package ru.shift;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.dto.TaskInfo;

public class Task implements Runnable {
    private static final Logger log = LogManager.getLogger(Task.class);

    private final TaskInfo taskInfo;
    private final Object lock = new Object();

    private volatile boolean isCalculated;
    private volatile double result;

    public Task(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public void run() {
        synchronized (lock) {
            log.debug("calculating from {} to {}", taskInfo.low(), taskInfo.high());

            for (var i = taskInfo.low(); i < taskInfo.high(); i++) {
                result += taskInfo.function().apply(i);
            }
            log.debug("calculation finished with result {}", result);
            isCalculated = true;

            lock.notifyAll();
        }
    }

    public double awaitResult() {
        synchronized (lock) {
            while (!isCalculated) {
                try {
                    lock.wait();
                } catch (InterruptedException ignore) {
                }
            }

            return result;
        }
    }
}
