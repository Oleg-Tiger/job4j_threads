package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T obj;
    private final int from;
    private final int to;
    private static final int SIZE = 10;

    public ParallelSearch(T[] array, T obj, int from, int to) {
        this.array = array;
        this.obj = obj;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from + 1 <= SIZE) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, obj, from, mid);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, obj, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return left > right ? left : right;
    }

    public Integer linearSearch() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(obj)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static <T> int search(T[] array, T obj) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, obj, 0, array.length - 1));
    }
}
