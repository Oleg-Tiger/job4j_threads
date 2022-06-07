package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File f) {
        this.file = f;
    }

    public String getContent() {
        return getContentByPredicate(x -> true);
    }

    public String getContentWithoutUnicode() {
        return getContentByPredicate(x -> x < 0x80);
    }

    public String getContentByPredicate(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        int data;
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}