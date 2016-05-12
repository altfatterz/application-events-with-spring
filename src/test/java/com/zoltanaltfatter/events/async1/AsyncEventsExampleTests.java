package com.zoltanaltfatter.events.async1;

import com.zoltanaltfatter.events.async1.AsyncEventsExample.TodoEventProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AsyncEventsExample.class)
public class AsyncEventsExampleTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncEventsExampleTests.class);

    @Autowired
    TodoEventProducer todoEventProducer;

    @Test
    public void createEvent() throws InterruptedException {

        LOGGER.info("Publishing event...");
        todoEventProducer.create("foo");

        // A chance to see the logging message produced by LoggingErrorHandler before the JVM exists.
        Thread.sleep(1000);

        LOGGER.info("Finished publishing event");
    }
}
