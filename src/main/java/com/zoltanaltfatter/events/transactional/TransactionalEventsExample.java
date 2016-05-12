package com.zoltanaltfatter.events.transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Date;

/**
 * @author Zoltan Altfatter
 */
@SpringBootApplication
class TransactionalEventsExample {

    static final Logger logger = LoggerFactory.getLogger(TransactionalEventsExample.class);

    static class TaskScheduledEvent {

        private String taskId;
        private String byWho;
        private Date when;

        public TaskScheduledEvent(String taskId, String byWho, Date when) {
            this.taskId = taskId;
            this.byWho = byWho;
            this.when = when;
        }

        public String getTaskId() {
            return taskId;
        }

        public String getByWho() {
            return byWho;
        }

        public Date getWhen() {
            return when;
        }

        @Override
        public String toString() {
            return "TaskScheduledEvent{" +
                    "taskId='" + taskId + '\'' +
                    "byWho='" + byWho + '\'' +
                    "when='" + when + '\'' +
                    '}';
        }
    }

    @Component
    static class TaskProducer {

        private final ApplicationEventPublisher publisher;

        public TaskProducer(ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }

        public void publishEventWithoutTransaction() {
            publisher.publishEvent(new TaskScheduledEvent("abc", "123", new Date()));
        }

        @Transactional
        public void publishEventWithTransaction() {
            publisher.publishEvent(new TaskScheduledEvent("bcd", "234", new Date()));
        }

        @Transactional
        public void publishEventWithTransactionButError() {
            publisher.publishEvent(new TaskScheduledEvent("def", "345", new Date()));
            throw new RuntimeException("error");
        }


    }

    @Component
    static class TaskListeners {

        @EventListener
        public void handleWithoutTransaction(TaskScheduledEvent event) {
            logger.info("{} thread -- handling todo event without transaction", Thread.currentThread().getName());
        }

        @TransactionalEventListener
        public void handleAfterCommit(TaskScheduledEvent event) {
            logger.info("{} thread -- handling todo event after commit", Thread.currentThread().getName());
        }

        @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
        public void handleBeforeCommit(TaskScheduledEvent event) {
            logger.info("{} thread -- handling todo event before commit", Thread.currentThread().getName());
        }

        @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
        public void handleAfterCompletion(TaskScheduledEvent event) {
            logger.info("{} thread -- handling todo event after completion", Thread.currentThread().getName());
        }

        @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
        public void handleAfterRollback(TaskScheduledEvent event) {
            logger.info("{} thread -- handling todo event after rollback", Thread.currentThread().getName());
        }

    }


}
