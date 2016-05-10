package com.zoltanaltfatter.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Zoltan Altfatter
 */
class FilteringEventsExample {

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

    @Component
    static class ReminderListener {

        static Logger LOGGER = LoggerFactory.getLogger(AsyncEventsExample.TodoEventListener.class);

        @EventListener(condition = "#event.priority.name() == 'HIGH'")
        public void handleHighPriorityReminders(ReminderCreatedEvent event) {
            LOGGER.info("handle high priority reminder created events '{}'", event);
        }

        @EventListener
        public void handleReminderCreatedEvents(ReminderCreatedEvent event) {
            LOGGER.info("handle all reminder created events '{}'", event);
        }

        @EventListener({ReminderCreatedEvent.class, ReminderActivatedEvent.class})
        public void handleAllEvents() {
            LOGGER.info("handle all reminder events");
        }

    }

    @Component
    static class ReminderProducer {

        static Logger LOGGER = LoggerFactory.getLogger(AsyncEventsExample.TodoEventProducer.class);

        final ApplicationEventPublisher publisher;

        public ReminderProducer(ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }

        public void create(String title, Priority priority) {
            ReminderCreatedEvent event = new ReminderCreatedEvent(title, priority);
            LOGGER.info("producer created '{}' event'", event);
            publisher.publishEvent(event);
        }

        public void activate(String title) {
            ReminderActivatedEvent event = new ReminderActivatedEvent(title, new Date());
            LOGGER.info("producer created '{}' event", event);
            publisher.publishEvent(event);
        }
    }

}
