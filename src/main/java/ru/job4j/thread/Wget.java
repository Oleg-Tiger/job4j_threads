package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private static final int BYTES_IN_MB = 1048576;
    private final String url;
    private final int speed;
    private final String resultFile;

    public Wget(String url, int speed, String resultFile) {
        this.url = url;
        this.speed = speed;
        this.resultFile = resultFile;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(resultFile)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long speedInBytes = speed * BYTES_IN_MB;
            int downloadData = 0;
            long timeBeforeLoading = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speedInBytes) {
                    long downloadTime = System.currentTimeMillis() - timeBeforeLoading;
                    if (downloadTime < 1000) {
                        Thread.sleep(1000 - downloadTime);
                    }
                    downloadData = 0;
                    timeBeforeLoading = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
            throw new IllegalArgumentException();
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String resultFile = args[2];
        Thread wget = new Thread(new Wget(url, speed, resultFile));
        wget.start();
        wget.join();
    }
}