package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ThreadPoolTest {
    private static final AtomicReference<Integer> COUNT = new AtomicReference<>(0);

    private class RunnableForPool implements Runnable {

        @Override
        public synchronized void run() {
            int countNow;
            do {
                countNow = COUNT.get();
            } while (!COUNT.compareAndSet(countNow, countNow + 1));
        }
    }

    @Test
    public void when() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(3);
        threadPool.work(new RunnableForPool());
        threadPool.work(new RunnableForPool());
        threadPool.work(new RunnableForPool());
        Thread.sleep(1000);
        threadPool.shutdown();
        Thread.sleep(1000);
        assertThat(COUNT.get(), is(3));
    }
}