package ru.shift;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.dto.TaskInfo;

import java.util.concurrent.RecursiveTask;

public class Task extends RecursiveTask<Double> {
    private static final Logger log = LogManager.getLogger(Task.class);
    private final TaskInfo taskInfo;

    public Task(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    protected Double compute() {
        if (taskInfo.high() - taskInfo.low() < taskInfo.threshold()) {
            return calculate();
        } else {
            return split();
        }
    }

    private double calculate() {
        log.debug("calculating from {} to {}", taskInfo.low(), taskInfo.high());
        double result = 0;

        for (var i = taskInfo.low(); i < taskInfo.high(); i++) {
            result += taskInfo.function().apply(i);
        }

        log.debug("calculation finished with result {}", result);
        return result;
    }

    private double split() {
        long middle = (taskInfo.high() - taskInfo.low()) / 2L + taskInfo.low();
        Task task1 = new Task(new TaskInfo(
                taskInfo.function(),
                taskInfo.low(),
                middle,
                taskInfo.threshold()
        ));
        Task task2 = new Task(new TaskInfo(
                taskInfo.function(),
                middle,
                taskInfo.high(),
                taskInfo.threshold()
        ));

        task1.fork();
        task2.fork();

        return task1.join() + task2.join();
    }
}
