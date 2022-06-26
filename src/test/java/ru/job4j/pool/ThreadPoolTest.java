package ru.job4j.pool;

import org.junit.Test;
import ru.job4j.Count;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ThreadPoolTest {
    private static final Count COUNT = new Count();

    private class RunnableForPool implements Runnable {

        @Override
        public void run() {
            COUNT.increment();
            COUNT.increment();
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
        assertThat(COUNT.get(), is(6));
    }
}