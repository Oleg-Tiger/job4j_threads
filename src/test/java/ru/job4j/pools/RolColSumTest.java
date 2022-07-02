package ru.job4j.pools;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class RolColSumTest {

    @Test
    public void whenSum() {
        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] rsl = RolColSum.sum(matrix);
        assertThat(rsl[0].getColSum(), is(12));
        assertThat(rsl[1].getColSum(), is(15));
        assertThat(rsl[2].getColSum(), is(18));
        assertThat(rsl[0].getRowSum(), is(6));
        assertThat(rsl[1].getRowSum(), is(15));
        assertThat(rsl[2].getRowSum(), is(24));
    }

    @Test
    public void whenASyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] rsl = RolColSum.asyncSum(matrix);
        assertThat(rsl[0].getColSum(), is(12));
        assertThat(rsl[1].getColSum(), is(15));
        assertThat(rsl[2].getColSum(), is(18));
        assertThat(rsl[0].getRowSum(), is(6));
        assertThat(rsl[1].getRowSum(), is(15));
        assertThat(rsl[2].getRowSum(), is(24));
    }
}