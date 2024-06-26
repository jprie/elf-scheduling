package org.example.edfscheduler.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implements the EDF (Earliest deadline first) Scheduling Algorithm
 *
 */
public class Scheduler {

    private static int T_MAX = 1000;

    public static void setTMax(int tMax) {
        T_MAX = tMax;
    }

    /**
     * Given a list of tasks (computing time, deadline, period) finds a scheduling
     * such that deadlines are not exceeded and tasks get executed exactly for computing time
     * ticks per period.
     * @param tasks list of periodic and aperiodic tasks
     * @return schedule of task executions
     */
    public List<TaskExecution> schedule(List<Task> tasks) {

        // create data structure to operate on without changing given data
        List<ProcessableTask> processableTasks = tasks.stream()
                .map(ProcessableTask::fromTask)
                .collect(Collectors.toCollection(ArrayList::new));

        int currentTime = 0;
        var taskExecutions = new ArrayList<TaskExecution>();

        while (currentTime < T_MAX && !processableTasks.isEmpty()) {

            // reset ready if period elapsed
            for (var pt : processableTasks) {
                if (currentTime >= pt.getNextPeriodStart()) {
                    pt.setReady(true);
                }
            }

            // find task with the lowest deadline which was not executed in this period, yet i.e. is ready
            Optional<ProcessableTask> optNextTask = processableTasks.stream()
                    .filter(ProcessableTask::isReady)
                    .min(Comparator.comparing(ProcessableTask::getNextDeadLine));


            // process next task
            if (optNextTask.isPresent()) {

                var nextTask = optNextTask.get();
                int timeEndOfTask = currentTime + nextTask.getComputationTime();
                boolean isTimedOut = nextTask.getNextDeadLine() < timeEndOfTask - 1; // FIXME: must really be done before deadline?

                // end scheduling if task timed out or range end exceeds T_MAX
                if (isTimedOut || timeEndOfTask > T_MAX) {
                    break;
                }

                // remove non-periodic task when scheduled
                if (nextTask.getPeriod() == 0) {
                    processableTasks.remove(nextTask);
                }

                // update state
                nextTask.updateNextDeadLineAndPeriodStart();
                nextTask.setReady(false);

                // schedule task
                var taskExecution = new TaskExecution(new Range(currentTime, timeEndOfTask), nextTask.getTask(), isTimedOut);
                taskExecutions.add(taskExecution);

                currentTime = timeEndOfTask;

            } else {
                // no task ready - continue
                currentTime++;
            }
        }

        return taskExecutions;
    }
}
