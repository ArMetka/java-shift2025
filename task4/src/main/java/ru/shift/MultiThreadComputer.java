package ru.shift;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.dto.TaskInfo;
import ru.shift.exception.InvalidTaskException;
import ru.shift.exception.InvalidThreadLimitException;

import java.util.concurrent.ForkJoinPool;

public class MultiThreadComputer {
    private static final Logger log = LogManager.getLogger(MultiThreadComputer.class);
    private final ForkJoinPool pool;
    private final TaskInfo taskInfo;

    public MultiThreadComputer(TaskInfo taskInfo, int threadLimit) {
        validateTask(taskInfo);
        validateThreadLimit(threadLimit);

        this.taskInfo = taskInfo;
        pool = new ForkJoinPool(threadLimit);
        log.info("created pool with {} workers", threadLimit);
    }

    public double calculate() {
        log.info("starting calculation...");

        long timeStart = System.currentTimeMillis();
        double result = pool.invoke(new Task(taskInfo));
        long timeElapsed = System.currentTimeMillis() - timeStart;

        log.info("calculation completed in {}ms", timeElapsed);

        return result;
    }

    private void validateTask(TaskInfo task) {
        if (task.low() >= task.high()) {
            throw new InvalidTaskException("invalid task interval: [" + task.low() + ", " + task.high() + ")");
        }

        if (task.threshold() < 2) {
            throw new InvalidTaskException("invalid task threshold: " + task.threshold());
        }
    }

    private void validateThreadLimit(int threadLimit) {
        if (threadLimit < 1) {
            throw new InvalidThreadLimitException("thread limit must be > 1, got " + threadLimit);
        }

        var procs = Runtime.getRuntime().availableProcessors();
        if (threadLimit > procs) {
            log.warn("thread limit is higher than number of available processors({})", procs);
        }
    }
}
