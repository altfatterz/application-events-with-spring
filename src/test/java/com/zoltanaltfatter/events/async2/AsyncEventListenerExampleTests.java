package com.zoltanaltfatter.events.async2;

import com.zoltanaltfatter.events.async2.AsyncEventListenerExample.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Zoltan Altfatter
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AsyncEventListenerExample.class)
public class AsyncEventListenerExampleTests {

    @Autowired
    Producer producer;

    @Test
    public void createEvent() throws InterruptedException {

        producer.create("foo");

        // A chance to see the logging messages before the JVM exists.
        Thread.sleep(2000);

    }
}
