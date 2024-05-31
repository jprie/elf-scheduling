import domain.TaskExecution;
import domain.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SchedulerTest {

    @Test
    void givenFivePeriodicTasks_returnsSixRanges() {

        var tasks = List.of(
                new Task("T0", 13, 5, 20),
                new Task("T1", 7, 3, 11),
                new Task("T2", 6, 4, 10),
                new Task("T3", 1, 1, 20)
        );

        Scheduler.settMax(20);
        var schedule = new Scheduler().schedule(tasks);
        System.out.println(schedule);

        Assertions.assertEquals(6, schedule.size());
        Assertions.assertEquals(true, schedule.get(5).deadlineExceeded());
    }

    @Test
    void givenThreePeriodicOneNonPeriodicTasks_returns4Ranges() {

        var tasks = List.of(
                new Task("T0", 13, 5, 20),
                new Task("T1", 7, 3, 11),
                new Task("T2", 6, 4, 10),
                new Task("T3", 1, 1)
        );

        Scheduler.settMax(20);

        var schedule = new Scheduler().schedule(tasks);
        System.out.println(schedule);

        Assertions.assertEquals(6, schedule.size());
        Assertions.assertEquals(1, schedule.stream().filter(r -> r.task().name().equals("T3")).toList().size());

    }

    @Test
    void givenThreePeriodicOneNonPeriodicTasks_returns5Ranges2TO() {

        var tasks = List.of(
                new Task("T0", 3, 2, 8),
                new Task("T1", 7, 3, 12),
                new Task("T2", 6, 5, 9),
                new Task("T3", 6, 7)
        );

        Scheduler.settMax(50);
        var schedule = new Scheduler().schedule(tasks);

        System.out.println(schedule);

        Assertions.assertEquals(1, schedule.stream().filter(r -> r.task().name().equals("T3")).count());
        Assertions.assertEquals(3, schedule.size());
        Assertions.assertEquals(1, schedule.stream().filter(TaskExecution::deadlineExceeded).count());


    }
}
