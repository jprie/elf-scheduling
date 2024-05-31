import domain.Task;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // example from https://en.wikipedia.org/wiki/Earliest_deadline_first_scheduling

        var tasks = List.of(
                new Task("T0", 13, 5, 20),
                new Task("T1", 7, 3, 11),
                new Task("T2", 6, 4, 10),
                new Task("T3", 1, 1, 20)
        );

        Scheduler.settMax(20);
        var schedule = new Scheduler().schedule(tasks);

        System.out.println(schedule);

    }
}
