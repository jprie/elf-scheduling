package domain;

public class ProcessableTask {

    private int id;
    private final int computationTime;
    private int nextDeadLine;
    private final int period;

    public ProcessableTask(int id, int computationTime, int deadLine, int period) {
        this.id = id;
        this.computationTime = computationTime;
        this.nextDeadLine = deadLine;
        this.period = period;
    }

    public void updateDeadLine() {

        nextDeadLine += period;
    }

    public int getNextDeadLine() {
        return nextDeadLine;
    }

    public int getComputationTime() {
        return computationTime;
    }

    public static ProcessableTask fromTask(int id, Task task) {
        return new ProcessableTask(id, task.computationTime(), task.deadline(), task.optionalPeriod());
    }

    public int getPeriod() {
        return period;
    }

    public int getId() {
        return id;
    }
}
