package ru.job4j;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private  final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int countNow;
        do {
            countNow = count.get();
        } while (!count.compareAndSet(countNow, countNow + 1));
    }

    public int get() {
     return count.get();
    }
}