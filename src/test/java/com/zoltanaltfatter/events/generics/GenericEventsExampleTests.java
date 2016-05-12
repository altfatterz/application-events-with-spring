package com.zoltanaltfatter.events.generics;

import com.zoltanaltfatter.events.generics.GenericEventsExample.Bid;
import com.zoltanaltfatter.events.generics.GenericEventsExample.BidProducer;
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

    }


}