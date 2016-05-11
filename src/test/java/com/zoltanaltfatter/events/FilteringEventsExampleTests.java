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
    ReminderProducer producer;

    @Test
    public void events() throws InterruptedException {
        producer.create("foo", HIGH);
        producer.create("bar", LOW);

        producer.activate("foo");

        // A chance to see the logging message produced by LoggingErrorHandler before the JVM exists.
        Thread.sleep(1000);
    }

    @Test
    public void highBid() throws InterruptedException {
        producer.createBid(100);

        // A chance to see the logging message produced by LoggingErrorHandler before the JVM exists.
        Thread.sleep(1000);
    }


}