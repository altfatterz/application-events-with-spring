package com.zoltanaltfatter.events;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.zoltanaltfatter.events.TransactionalEventsExample.TaskProducer;

/**
 * @author Zoltan Altfatter
  */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionalEventsExampleTests {

    @Autowired
    TaskProducer producer;

    @Test
    public void publishEventWithoutTransaction() {
        producer.publishEventWithoutTransaction();
    }

    @Test
    public void publishEventWithTransaction() {
        producer.publishEventWithTransaction();
    }

    @Test(expected = RuntimeException.class)
    public void publishEventWithTransactionButError() throws InterruptedException {
        producer.publishEventWithTransactionButError();
    }

}

