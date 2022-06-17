package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), createBiFunction(model)) != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId(), model);
    }

    private BiFunction<Integer, Base, Base> createBiFunction(Base model) {
        return (key, stored) -> {
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base rsl = new Base(model.getId(), model.getVersion() + 1);
            rsl.setName(model.getName());
            return rsl;
        };
    }
}
