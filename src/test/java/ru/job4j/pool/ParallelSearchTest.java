package ru.job4j.pool;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParallelSearchTest {

    @Test
    public void whenFound() {
        String[] array = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
        assertThat(ParallelSearch.search(array, "F"), is(5));
    }

    @Test
    public void whenNotFound() {
        String[] array = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
        assertThat(ParallelSearch.search(array, "Q"), is(-1));
    }
}