package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String[] process = new String[] {"\\", "|", "/"};
        int i = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(500);
                System.out.print("\rLoading: " + process[i]);
                i = i == process.length - 1 ? 0 : i + 1;
            }
        } catch (InterruptedException e) {
            System.out.println("\rCompleted");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
