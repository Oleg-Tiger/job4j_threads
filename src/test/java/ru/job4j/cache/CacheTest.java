package ru.job4j.cache;

import org.junit.Test;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddFirstAndNotAddSecond() {
        Cache cache = new Cache();
        assertTrue(cache.add(new Base(1, 1)));
        assertFalse(cache.add(new Base(1, 1)));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        Base modelForDelete = new Base(model.getId(), model.getVersion());
        assertTrue(cache.delete(modelForDelete));
    }

    @Test
    public void whenOtherNameThenNotDelete() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        Base modelForDelete = new Base(model.getId(), model.getVersion());
        modelForDelete.setName("Name");
        assertFalse(cache.delete(modelForDelete));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        Base updatedModel = new Base(model.getId(), model.getVersion());
        updatedModel.setName("Name");
        assertTrue(cache.update(updatedModel));
        Base modelForDelete = new Base(updatedModel.getId(), updatedModel.getVersion() + 1);
        modelForDelete.setName("Name");
        assertTrue(cache.delete(modelForDelete));
    }

    @Test
    public void whenIdNotFoundThenNotUpdate() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        Base updatedModel = new Base(2, 1);
        updatedModel.setName("Name");
        assertFalse(cache.update(updatedModel));
    }

    @Test (expected = OptimisticException.class)
    public void whenDifferentVersion() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        Base updatedModel = new Base(1, 2);
        cache.update(updatedModel);
    }

}