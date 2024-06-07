package org.example.edfscheduler.domain;

/**
 *
 * @param name
 * @param computationTime ticks per period
 * @param deadline
 * @param optionalPeriod
 */
public record Task(
        String name,
        int computationTime,
        int deadline,
        int optionalPeriod
) {

    public Task(String name, int computationTime, int deadline) {

        this(name, deadline, computationTime, 0);
    }
}
