import domain.ProcessableTask;
import domain.Range;
import domain.ScheduledRangeWithTask;
import domain.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Scheduler {

    private static int T_MAX = 1000;

    public static void settMax(int tMax) {
        T_MAX = tMax;
    }

    List<ScheduledRangeWithTask> schedule(List<Task> tasks) {

        // create datastructure to operate on without changing given data
        int index = 0;
        List<ProcessableTask> processableTasks = new ArrayList<>();
        for (var task : tasks) {
            processableTasks.add(ProcessableTask.fromTask(index++, task));
        }

        int t = 0;
        var scheduledRangesWithTask = new ArrayList<ScheduledRangeWithTask>();
        // schedule processes
        while (t < T_MAX) {

            // schedule task with lowest deadline
            var nextTask = processableTasks.stream().min(Comparator.comparing(ProcessableTask::getNextDeadLine)).orElseThrow();

            var nextT = t + nextTask.getComputationTime();

            boolean isTimedOut = nextT-1 > nextTask.getNextDeadLine();

            // end scheduling if range end exceeds T_MAX
            if (nextT > T_MAX) {
                break;
            }

            // remove non-periodic task when scheduled
            if (nextTask.getPeriod() == 0) {
                processableTasks.remove(nextTask);
            }

            // deadline = lastDeadLine + Period
            nextTask.updateDeadLine();

            var scheduleWithTask = new ScheduledRangeWithTask(new Range(t, nextT), tasks.get(nextTask.getId()), isTimedOut);

            scheduledRangesWithTask.add(scheduleWithTask);

            // end on first timeout
            if (isTimedOut)
                break;

            t = nextT;
        }

        return scheduledRangesWithTask;
    }
}
