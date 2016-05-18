package com.zoltanaltfatter.events.listenandpublish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Zoltan Altfatter
 */
@SpringBootApplication
public class ListenAndPublishEventsExample {

    static final Logger logger = LoggerFactory.getLogger(ListenAndPublishEventsExample.class);

    static class TaskAssignedEvent {
        @Override
        public String toString() {
            return "TaskAssignedEvent";
        }
    }

    static class TaskModifiedEvent {
        @Override
        public String toString() {
            return "TaskModifiedEvent";
        }
    }

    static class TaskEvent {
        @Override
        public String toString() {
            return "TaskEvent";
        }
    }

    @Component
    static class Receiver {

        @EventListener
        TaskModifiedEvent handleAssignedEvent1(TaskAssignedEvent event) {
            logger.info("thread '{}' handling '{}' event", Thread.currentThread(), event);
            return new TaskModifiedEvent();
        }

        @EventListener
        List<TaskEvent> handleAssignedEvent2(TaskAssignedEvent event) {
            logger.info("thread '{}' handling '{}' event", Thread.currentThread(), event);
            return Arrays.asList(new TaskEvent(), new TaskEvent());
        }

        @EventListener
        void handleTaskModified(TaskModifiedEvent event) {
            logger.info("thread '{}' handling '{}' event", Thread.currentThread(), event);
        }

        @EventListener
        void handleA(TaskEvent event) {
            logger.info("thread '{}' handling '{}' event", Thread.currentThread(), event);
        }

    }

    @Component
    static class Producer {

        private final ApplicationEventPublisher publisher;

        public Producer(ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }

        public void createTask() {
            publisher.publishEvent(new TaskAssignedEvent());
        }
    }


}
