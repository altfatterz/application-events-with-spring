package com.zoltanaltfatter.events.generics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;
import org.springframework.stereotype.Component;

/**
 * @author Zoltan Altfatter
 */

@SpringBootApplication
class GenericEventsExample {

    static final Logger logger = LoggerFactory.getLogger(GenericEventsExample.class);

    static class EntityCreatedEvent<T> implements ResolvableTypeProvider {

        private T source;

        public EntityCreatedEvent(T source) {
            this.source = source;
        }

        @Override
        public ResolvableType getResolvableType() {
            return ResolvableType.forClassWithGenerics(getClass(),
                    ResolvableType.forInstance(source));
        }
    }

    // due to type erasure we cannot publish such an event
    static class EntityCreatedEvent2<T> {

        private Object source;

        public EntityCreatedEvent2(T source) {
            this.source = source;
        }
    }

    static class Bid {

        final int amount;

        public Bid(int amount) {
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return "Bid{amount='" + amount + '}';
        }
    }


    @Component
    static class BidCreatedEventListener {

        @EventListener
        public void handle(EntityCreatedEvent<Bid> bidCreatedEvent) {
            logger.info("handle bid created event '{}'", bidCreatedEvent);
        }
    }

    @Component
    static class BidProducer {

        final ApplicationEventPublisher publisher;

        public BidProducer(ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }

        public void create(Bid bid) {
            publisher.publishEvent(new EntityCreatedEvent(bid));
        }
    }

}
