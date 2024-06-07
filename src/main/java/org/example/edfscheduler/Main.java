package org.example.edfscheduler;

import org.example.edfscheduler.domain.Scheduler;
import org.example.edfscheduler.domain.Task;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // example from https://en.wikipedia.org/wiki/Earliest_deadline_first_scheduling

        var tasks = List.of(
                new Task("T0", 5, 13, 20),
                new Task("T1", 3, 7, 11),
                new Task("T2", 4, 6, 10),
                new Task("T3", 1, 1, 20)
        );

//        domain.Scheduler.setTMax(20);
        var schedule = new Scheduler().schedule(tasks);

        System.out.println(schedule);

    }
}
