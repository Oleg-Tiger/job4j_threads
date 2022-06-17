package ru.job4j.pool;

import ru.job4j.buffer.SimpleBlockingQueue;
import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;
    private static final int SIZE = Runtime.getRuntime().availableProcessors();

    public ThreadPool(int size) {
        this.tasks = new SimpleBlockingQueue<>(size);
        for (int i = 0; i < SIZE; i++) {
            threads.add(new ThreadForPool());
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.start();
            thread.interrupt();
        }
    }

    private class ThreadForPool extends Thread {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() || !tasks.isEmpty()) {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}