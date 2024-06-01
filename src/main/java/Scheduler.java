import domain.ProcessableTask;
import domain.Range;
import domain.TaskExecution;
import domain.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

        int t = 0;
        var taskExecutions = new ArrayList<TaskExecution>();

        while (t < T_MAX) {

            // reset isExecuted if period elapsed
            for (var pt : processableTasks) {
                if (t >= pt.getNextPeriodStart()) {
                    pt.setExecuted(false);
                }
            }

            // find task with lowest deadline which was not executed in this period
            var optNextTask = processableTasks.stream()
                    .filter(tp -> !tp.isExecuted())
                    .min(Comparator.comparing(ProcessableTask::getNextDeadLine));


            // schedule task
            if (optNextTask.isPresent()) {

                var nextTask = optNextTask.get();
                var nextT = t + nextTask.getComputationTime();

                boolean isTimedOut = nextT - 1 > nextTask.getNextDeadLine();

                // end scheduling if range end exceeds T_MAX
                if (isTimedOut || nextT > T_MAX) {
                    break;
                }

                // remove non-periodic task when scheduled
                if (nextTask.getPeriod() == 0) {
                    processableTasks.remove(nextTask);
                }

                nextTask.updateNextDeadLineAndPeriodStart();
                nextTask.setExecuted(true);

                var taskExecution = new TaskExecution(new Range(t, nextT), nextTask.getTask(), isTimedOut);
                taskExecutions.add(taskExecution);

                t = nextT;

            } else {
                // no task ready - continue
                t++;
            }
        }

        return taskExecutions;
    }
}
