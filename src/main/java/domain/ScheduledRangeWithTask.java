package domain;

public record ScheduledRangeWithTask(
        Range range,
        Task task,
        boolean deadlineExceeded) {

    @Override
    public String toString() {
        return "[" + range.start() + " " + task.name() + "(" + (deadlineExceeded ? "timeout" : "ok") + ")" + " " + range.end() + "]";
    }
}
