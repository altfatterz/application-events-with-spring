package com.zoltanaltfatter.events.listenandpublish;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Zoltan Altfatter
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ListenAndPublishEventsExample.class)
public class ListenAndPublishEventsExampleTests {

    @Autowired
    ListenAndPublishEventsExample.Producer producer;

    @Test
    public void createEvent() throws InterruptedException {
        producer.createTask();
    }
}