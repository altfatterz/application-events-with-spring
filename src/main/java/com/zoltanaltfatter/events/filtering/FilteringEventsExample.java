package com.zoltanaltfatter.events.filtering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Zoltan Altfatter
 */
@SpringBootApplication
class FilteringEventsExample {

    static final Logger logger = LoggerFactory.getLogger(FilteringEventsExample.class);

    enum Priority {
        NONE, LOW, MEDIUM, HIGH
    }

    static class ReminderCreatedEvent {

        private String title;
        private Priority priority;

        public ReminderCreatedEvent(String title, Priority priority) {
            this.title = title;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "ReminderCreatedEvent{" +
                    "title='" + title + '\'' +
                    ",priority='" + priority + '\'' +
                    '}';
        }

        public Priority getPriority() {
            return priority;
        }
    }

    static class ReminderActivatedEvent {

        private String title;
        private Date date;

        public ReminderActivatedEvent(String title, Date date) {
            this.title = title;
            this.date = date;
        }

        @Override
        public String toString() {
            return "ReminderActivatedEvent{" +
                    "title='" + title + '\'' +
                    ",date='" + date + '\'' +
                    '}';
        }
    }

    static class BidCreatedEvent {

        private int amount;

        public BidCreatedEvent(int amount) {
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }
    }

    @Component
    static class ReminderListener {

        @EventListener(condition = "#event.priority.name() == 'HIGH'")
        public void handleHighPriorityReminders(ReminderCreatedEvent event) {
            logger.info("handle high priority reminder created events '{}'", event);
        }

        @EventListener
        public void handleReminderCreatedEvents(ReminderCreatedEvent event) {
            logger.info("handle all reminder created events '{}'", event);
        }

        @EventListener({ReminderCreatedEvent.class, ReminderActivatedEvent.class})
        public void handleAllEvents() {
            logger.info("handle all reminder events");
        }

        @EventListener(condition = "#bidCreatedEvent.amount >= 100")
        public void handleHighBids(BidCreatedEvent bidCreatedEvent) {
            logger.info("handle high bid event of '{}'", bidCreatedEvent.amount);
        }

    }

    @Component
    static class ReminderProducer {

        final ApplicationEventPublisher publisher;

        public ReminderProducer(ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }

        public void create(String title, Priority priority) {
            ReminderCreatedEvent event = new ReminderCreatedEvent(title, priority);
            logger.info("producer created '{}' event'", event);
            publisher.publishEvent(event);
        }

        public void activate(String title) {
            ReminderActivatedEvent event = new ReminderActivatedEvent(title, new Date());
            logger.info("producer created '{}' event", event);
            publisher.publishEvent(event);
        }

        public void createBid(int amount) {
            publisher.publishEvent(new BidCreatedEvent(amount));
        }
    }

}
