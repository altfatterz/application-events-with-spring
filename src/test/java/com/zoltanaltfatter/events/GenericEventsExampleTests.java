package com.zoltanaltfatter.events;

import com.zoltanaltfatter.events.GenericEventsExample.Bid;
import com.zoltanaltfatter.events.GenericEventsExample.BidProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Zoltan Altfatter
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GenericEventsExample.class)
public class GenericEventsExampleTests {

    @Autowired
    BidProducer bidProducer;

    @Test
    public void create() throws InterruptedException {
        bidProducer.create(new Bid(100));

        // A chance to see the logging message produced by LoggingErrorHandler before the JVM exists.
        Thread.sleep(1000);
    }


}