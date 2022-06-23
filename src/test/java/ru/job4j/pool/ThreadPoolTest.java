package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ThreadPoolTest {
    private static int count = 0;

    private class RunnableForPool implements Runnable {

        @Override
        public synchronized void run() {
            count++;
            count++;
        }
    }

    @Test
    public void when3TasksWorkThenCountIs3() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(3);
        threadPool.work(new RunnableForPool());
        threadPool.work(new RunnableForPool());
        threadPool.work(new RunnableForPool());
        Thread.sleep(500);
        threadPool.shutdown();
        Thread.sleep(500);
        assertThat(count, is(6));
    }
}