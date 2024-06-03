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
    private boolean ready;
    private final Task task;

    public ProcessableTask(int computationTime, int deadLine, int period, Task task) {
        this.task = task;
        this.computationTime = computationTime;
        this.nextDeadLine = deadLine;
        this.period = period;
        this.nextPeriodStart = 0;
        this.ready = true;
    }

    public static ProcessableTask fromTask(Task task) {
        return new ProcessableTask(task.computationTime(), task.deadline(), task.optionalPeriod(), task);
    }

    public void updateNextDeadLineAndPeriodStart() {

        nextDeadLine += period;
        nextPeriodStart += period;
    }

    public void setNextDeadLine(int nextDeadLine) {
        this.nextDeadLine = nextDeadLine;
    }

    public void setNextPeriodStart(int nextPeriodStart) {
        this.nextPeriodStart = nextPeriodStart;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
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
