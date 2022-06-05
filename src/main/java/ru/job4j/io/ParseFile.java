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
        String output = "";
        int data;
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}