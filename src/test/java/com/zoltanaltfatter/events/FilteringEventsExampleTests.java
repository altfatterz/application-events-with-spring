package com.zoltanaltfatter.events;

import com.zoltanaltfatter.events.FilteringEventsExample.ReminderProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.zoltanaltfatter.events.FilteringEventsExample.Priority.HIGH;
import static com.zoltanaltfatter.events.FilteringEventsExample.Priority.LOW;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FilteringEventsExample.class)
public class FilteringEventsExampleTests {

    @Autowired
    ReminderProducer reminderProducer;

    @Test
    public void events() throws InterruptedException {
        reminderProducer.create("foo", HIGH);
        reminderProducer.create("bar", LOW);

        reminderProducer.activate("foo");

        // A chance to see the logging message produced by LoggingErrorHandler before the JVM exists.
        Thread.sleep(1000);
    }


}