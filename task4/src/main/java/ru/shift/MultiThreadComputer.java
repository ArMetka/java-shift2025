package ru.shift;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.dto.TaskInfo;
import ru.shift.exception.InvalidTaskException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadComputer {
    private static final Logger log = LogManager.getLogger(MultiThreadComputer.class);

    private final TaskInfo taskInfo;
    private final int threadNum;
    private final boolean calculateInParallel;

    public MultiThreadComputer(TaskInfo taskInfo, int threadNum, long singleThreadThreshold) {
        validateTask(taskInfo);
        validateThreadLimit(threadNum);
        validateSingleThreadThreshold(singleThreadThreshold);

        this.taskInfo = taskInfo;
        this.threadNum = threadNum;
        calculateInParallel = shouldCalculateInParallel(singleThreadThreshold);
    }

    public double calculate() {
        double result;
        var timeStart = System.currentTimeMillis();

        if (calculateInParallel) {
            var pool = Executors.newFixedThreadPool(threadNum);
            try {
                log.info("calculating with {} threads", threadNum);
                result = calculateMultiThreaded(pool);
            } finally {
                pool.shutdown();
            }
        } else {
            log.info("calculating in current thread");
            result = calculateSingleThreaded();
        }

        log.info("calculation completed in {}ms", System.currentTimeMillis() - timeStart);

        return result;
    }

    private double calculateSingleThreaded() {
        var task = new Task(new TaskInfo(
                taskInfo.function(),
                taskInfo.low(),
                taskInfo.high() + 1
        ));
        task.run();

        return task.awaitResult();
    }

    private double calculateMultiThreaded(ExecutorService pool) {
        double result = 0;

        List<Task> tasks = new ArrayList<>(threadNum);
        long rangePerTask = ((taskInfo.high() + 1) - taskInfo.low()) / threadNum;

        for (var i = 0; i < threadNum; i++) {
            long low = taskInfo.low() + rangePerTask * i;
            long high = (i == threadNum - 1) ? (taskInfo.high() + 1) : (low + rangePerTask);

            var task = new Task(new TaskInfo(
                    taskInfo.function(),
                    low,
                    high
            ));

            tasks.add(task);
            pool.execute(task);
        }

        for (var task : tasks) {
            result += task.awaitResult();
        }

        return result;
    }

    private void validateTask(TaskInfo task) {
        if (task.low() >= task.high()) {
            throw new InvalidTaskException("invalid task interval: [" + task.low() + ", " + task.high() + "]");
        }
    }

    private void validateThreadLimit(int threadLimit) {
        if (threadLimit < 1) {
            throw new InvalidTaskException("thread limit must be > 1, got " + threadLimit);
        }

        var procs = Runtime.getRuntime().availableProcessors();
        if (threadLimit > procs) {
            log.warn("thread limit is higher than number of available processors({})", procs);
        }
    }

    private void validateSingleThreadThreshold(long singleThreadThreshold) {
        if (singleThreadThreshold < 2) {
            throw new InvalidTaskException("invalid single thread threshold: " + singleThreadThreshold);
        }
    }

    private boolean shouldCalculateInParallel(long singleThreadThreshold) {
        long range = taskInfo.high() + 1 - taskInfo.low();

        if (range < singleThreadThreshold) {
            return false;
        }

        if (range < threadNum) {
            log.warn("unable to split work, calculating in single thread");
            return false;
        }

        return true;
    }
}
