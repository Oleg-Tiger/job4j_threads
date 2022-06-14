package ru.job4j.buffer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleBlockingQueueTest {

    private class ThreadConsumer extends Thread {
        private final SimpleBlockingQueue<Integer> simpleBlockingQueue;

        private ThreadConsumer(final SimpleBlockingQueue<Integer> simpleBlockingQueue) {
            this.simpleBlockingQueue = simpleBlockingQueue;
        }

        @Override
        public void run() {
            try {
                simpleBlockingQueue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class ThreadProducer extends Thread {
        private final SimpleBlockingQueue<Integer> simpleBlockingQueue;

        private ThreadProducer(final SimpleBlockingQueue<Integer> simpleBlockingQueue) {
            this.simpleBlockingQueue = simpleBlockingQueue;
        }

        @Override
        public void run() {
            try {
                simpleBlockingQueue.offer(1);
                simpleBlockingQueue.offer(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<String> buffer = new CopyOnWriteArrayList<>();
        SimpleBlockingQueue<String> simpleBlockingQueue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.offer("one");
                        simpleBlockingQueue.offer("two");
                        simpleBlockingQueue.offer("three");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    while (!simpleBlockingQueue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(simpleBlockingQueue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList("one", "two", "three")));
    }
}