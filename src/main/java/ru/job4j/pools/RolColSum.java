package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int length = matrix.length;
        Sums[] rsl = new Sums[length];
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            rsl[i] = new Sums(rowSum, colSum);
            rowSum = 0;
            colSum = 0;
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int length = matrix.length;
        Sums[] rsl = new Sums[length];
        for (int i = 0; i < length; i++) {
            rsl[i] = getTask(matrix, i).get();
        }
        return rsl;
    }

    public static CompletableFuture<Sums> getTask(int[][] data, int number) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < data.length; i++) {
                rowSum += data[number][i];
                colSum += data[i][number];
            }
            return new Sums(rowSum, colSum);
        });
    }
}