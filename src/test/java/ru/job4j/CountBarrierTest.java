package ru.job4j;

import org.junit.Test;
import static org.junit.Assert.*;

public class CountBarrierTest {

    private class ThreadCountBarrier extends Thread {
        private final CountBarrier countBarrier;

        private ThreadCountBarrier(final CountBarrier countBarrier) {
            this.countBarrier = countBarrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
                countBarrier.count();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void when() throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(2);
        ThreadCountBarrier master1 = new ThreadCountBarrier(countBarrier);
        ThreadCountBarrier master2 = new ThreadCountBarrier(countBarrier);
        Thread slave = new Thread(
                () -> countBarrier.await()
        );
        master1.start();
        slave.start();
        Thread.sleep(300);
        assertTrue(slave.getState() == Thread.State.WAITING);
        Thread.sleep(600);
        assertTrue(slave.getState() == Thread.State.WAITING);
        master2.start();
        Thread.sleep(300);
        assertTrue(slave.getState() == Thread.State.WAITING);
        Thread.sleep(600);
        assertTrue(slave.getState() == Thread.State.TERMINATED);
    }
}