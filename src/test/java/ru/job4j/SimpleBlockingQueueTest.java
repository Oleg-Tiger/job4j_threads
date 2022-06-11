package ru.job4j;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import org.junit.Test;

public class SimpleBlockingQueueTest {

    private class ThreadConsumer extends Thread {
        private final SimpleBlockingQueue<Integer> simpleBlockingQueue;

        private ThreadConsumer(final SimpleBlockingQueue<Integer> simpleBlockingQueue) {
            this.simpleBlockingQueue = simpleBlockingQueue;
        }

        @Override
        public void run() {
            simpleBlockingQueue.poll();
        }
    }

    private class ThreadProducer extends Thread {
        private final SimpleBlockingQueue<Integer> simpleBlockingQueue;

        private ThreadProducer(final SimpleBlockingQueue<Integer> simpleBlockingQueue) {
            this.simpleBlockingQueue = simpleBlockingQueue;
        }

        @Override
        public void run() {
            simpleBlockingQueue.offer(1);
            simpleBlockingQueue.offer(2);
        }
    }

    @Test
    public void whenSize1AndOffer2AndPoll1() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(1);
        ThreadConsumer consumer = new ThreadConsumer(simpleBlockingQueue);
        ThreadProducer producer = new ThreadProducer(simpleBlockingQueue);
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(simpleBlockingQueue.poll(), is(2));
    }
}