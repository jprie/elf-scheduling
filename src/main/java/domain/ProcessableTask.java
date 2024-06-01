package domain;

/**
 * Unit of work for the EDF scheduler
 * Stores a reference to the task as well as the state needed for dynamic scheduling
 */
public class ProcessableTask {

    private final int computationTime;
    private final int period;
    private int nextDeadLine;
    private int nextPeriodStart;
    private boolean executed;
    private final Task task;

    public ProcessableTask(int computationTime, int deadLine, int period, Task task) {
        this.task = task;
        this.computationTime = computationTime;
        this.nextDeadLine = deadLine;
        this.period = period;
        this.nextPeriodStart = 0;
        this.executed = false;
    }

    public static ProcessableTask fromTask(Task task) {
        return new ProcessableTask(task.computationTime(), task.deadline(), task.optionalPeriod(), task);
    }

    public void updateNextDeadLineAndPeriodStart() {

        nextDeadLine += period;
        nextPeriodStart += period;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean val) {
        this.executed = val;
    }

    public int getNextDeadLine() {
        return nextDeadLine;
    }

    public int getNextPeriodStart() {
        return nextPeriodStart;
    }

    public int getComputationTime() {
        return computationTime;
    }



    public int getPeriod() {
        return period;
    }

    public Task getTask() {
        return task;
    }
}
