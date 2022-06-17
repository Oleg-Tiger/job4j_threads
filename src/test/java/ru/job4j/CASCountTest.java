package ru.job4j;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CASCountTest {

    private class ThreadCASCount extends Thread {
        private final CASCount casCount;

        public ThreadCASCount(CASCount casCount) {
            this.casCount = casCount;
        }

        @Override
        public void run() {
            casCount.increment();
            casCount.increment();
            casCount.increment();
        }
    }

    @Test
    public void whenTwoThreadsTripleIncrementThenResultIsSix() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new ThreadCASCount(casCount);
        Thread second = new ThreadCASCount(casCount);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(casCount.get(), is(6));
    }

}