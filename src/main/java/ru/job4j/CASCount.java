package ru.job4j;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private  final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int countBeforeIncrement;
        do {
            countBeforeIncrement = count.get();
        } while (!count.compareAndSet(countBeforeIncrement, ++countBeforeIncrement));
    }

    public int get() {
     return count.get();
    }
}