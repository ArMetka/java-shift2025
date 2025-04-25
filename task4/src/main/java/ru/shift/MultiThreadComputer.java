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

    private final ExecutorService pool;
    private final TaskInfo taskInfo;
    private final int threadNum;
    private final boolean calculateInParallel;

    private boolean isShutdown;

    public MultiThreadComputer(TaskInfo taskInfo, int threadNum, long singleThreadThreshold) {
        validateTask(taskInfo);
        validateThreadLimit(threadNum);
        validateSingleThreadThreshold(singleThreadThreshold);

        this.taskInfo = taskInfo;
        this.threadNum = threadNum;
        calculateInParallel = taskInfo.high() - taskInfo.low() > singleThreadThreshold;

        if (calculateInParallel) {
            pool = Executors.newFixedThreadPool(threadNum);
            log.info("created pool with {} workers", threadNum);
        } else {
            pool = null;
        }
    }

    public double calculate() {
        if (isShutdown) {
            throw new IllegalStateException("already calculated");
        }
        isShutdown = true;

        double result;
        var timeStart = System.currentTimeMillis();
        if (calculateInParallel) {
            log.info("calculating with {} threads", threadNum);
            result = calculateMultiThreaded();
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

    private double calculateMultiThreaded() {
        double result = 0;

        List<Task> tasks = new ArrayList<>(threadNum);
        double rangePerTask = ((taskInfo.high() + 1) - taskInfo.low()) / (double) threadNum;

        for (var i = 0; i < threadNum; i++) {
            var task = new Task(new TaskInfo(
                    taskInfo.function(),
                    (long) (taskInfo.low() + rangePerTask * i),
                    (long) (taskInfo.low() + rangePerTask * (i + 1))
            ));
            tasks.add(task);
            pool.execute(task);
        }

        for (var task : tasks) {
            result += task.awaitResult();
        }
        pool.shutdown();

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
}
