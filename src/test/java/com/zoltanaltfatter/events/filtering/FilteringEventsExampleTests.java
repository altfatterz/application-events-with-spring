package com.zoltanaltfatter.events.filtering;

import com.zoltanaltfatter.events.filtering.FilteringEventsExample.ReminderProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.zoltanaltfatter.events.filtering.FilteringEventsExample.Priority.HIGH;
import static com.zoltanaltfatter.events.filtering.FilteringEventsExample.Priority.LOW;

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
    }

    @Test
    public void highBid() throws InterruptedException {
        producer.createBid(100);

    }


}