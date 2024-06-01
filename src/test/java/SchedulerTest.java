import domain.ProcessableTask;
import domain.Range;
import domain.TaskExecution;
import domain.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SchedulerTest {

    @Test
    void givenFivePeriodicTasks_returns6ExecutionsNoneTimedOut() {

        var tasks = List.of(
                new Task("T0", 5, 13, 20),
                new Task("T1", 3, 7, 11),
                new Task("T2", 4, 6, 10),
                new Task("T3", 1, 1, 20)
        );

        Scheduler.setTMax(20);
        var schedule = new Scheduler().schedule(tasks);
        System.out.println(schedule);

        Assertions.assertEquals(5, schedule.size());
        Assertions.assertFalse(schedule.stream().anyMatch(TaskExecution::deadlineExceeded));
    }

    @Test
    void givenThreePeriodicOneNonPeriodicTasks_returns5ExecutionsAPeriodicExactlyOnce() {

        var tasks = List.of(
                new Task("T0", 5, 13, 20),
                new Task("T1", 3, 7, 11),
                new Task("T2", 4, 6, 10),
                new Task("T3", 1, 1)
        );

        Scheduler.setTMax(20);

        var schedule = new Scheduler().schedule(tasks);
        System.out.println(schedule);

        Assertions.assertEquals(5, schedule.size());
        Assertions.assertEquals(1, schedule.stream().filter(r -> r.task().name().equals("T3")).toList().size());

    }

    @Test
    void given3PeriodicTasks_returns13ExecutionsNoneTimedOut1IdleTick() {

        var tasks = List.of(
                new Task("T1", 1, 8, 8),
                new Task("T2", 2, 5, 5),
                new Task("T3", 4, 10, 10)
        );

        Scheduler.setTMax(29);
        var schedule = new Scheduler().schedule(tasks);

        System.out.println(schedule);

        Assertions.assertEquals(13, schedule.size());
        Assertions.assertFalse(schedule.stream().anyMatch(TaskExecution::deadlineExceeded));
        Assertions.assertEquals(29 - 1, schedule.stream()
                .map(TaskExecution::range)
                .reduce(new Range(0, 0), (r0, r1) -> new Range(r0.start(), r0.end() - r1.start() + r1.end()))
                .end()
        );
    }

    @Test
    void given3PeriodicTasks_return7ExecutionsWith2IdleTicks() {

        var tasks = List.of(
                new Task("T1", 3, 7, 20),
                new Task("T2", 2, 4, 5),
                new Task("T3", 2, 8, 10)
        );

        Scheduler.setTMax(21);
        var schedule = new Scheduler().schedule(tasks);

        System.out.println(schedule);

        Assertions.assertEquals(7, schedule.size());
        Assertions.assertEquals(17 - 2, schedule.stream()
                .map(TaskExecution::range)
                .reduce(new Range(0, 0), (r0, r1) -> new Range(r0.start(), r0.end() - r1.start() + r1.end()))
                .end()
        );

    }

    @Test
    void given3PeriodicTasks1000Ticks_returnNExecutionsWithNIdleTicks() {

        var tasks = List.of(
                new Task("T1", 3, 7, 20),
                new Task("T2", 2, 4, 5),
                new Task("T3", 2, 8, 10)
        );

//        Scheduler.setTMax(21);
        var schedule = new Scheduler().schedule(tasks);

        System.out.println(schedule);
        Assertions.assertFalse(schedule.stream().anyMatch(TaskExecution::deadlineExceeded));

    }

}
