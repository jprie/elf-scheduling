package domain;

public record Task(
        String name,
        int deadline,
        int computationTime,
        int optionalPeriod
) {

    public Task(String name, int deadline, int computationTime) {

        this(name, deadline, computationTime, 0);
    }
}
