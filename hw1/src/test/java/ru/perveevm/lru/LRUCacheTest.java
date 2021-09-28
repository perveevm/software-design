package ru.perveevm.lru;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LRUCacheTest {
    private LRUCache<Integer, Integer> cache;
    private final static int CAPACITY = 20;

    @BeforeEach
    public void initCache() {
        cache = new LRUCache<>(CAPACITY);
    }

    @Test
    public void emptyTest() {
        assertEquals(cache.capacity(), CAPACITY);
        assertEquals(cache.size(), 0);
        assertFalse(cache.getHottestKey().isPresent());
    }

    @Test
    public void simpleTest() {
        cache.add(237, 238);
        Optional<Integer> value = cache.get(237);

        assertTrue(value.isPresent());
        assertEquals(value.get(), 238);
        assertEquals(cache.size(), 1);
    }

    @Test
    public void getEmptyTest() {
        cache.add(237, 238);
        Optional<Integer> value = cache.get(1);

        assertFalse(value.isPresent());
    }

    @Test
    public void sizeNotGreaterThanCapacityTest() {
        for (int i = 0; i < 2 * CAPACITY; i++) {
            cache.add(i, i);
        }
        assertEquals(cache.size(), CAPACITY);

        for (int i = 0; i < CAPACITY; i++) {
            assertFalse(cache.get(i).isPresent());
        }
        for (int i = CAPACITY; i < 2 * CAPACITY; i++) {
            assertTrue(cache.get(i).isPresent());
        }
    }

    @Test
    public void getHottestKeyTest() {
        for (int i = 0; i < CAPACITY; i++) {
            cache.add(i, i);
        }
        assertEquals(cache.size(), CAPACITY);

        assertTrue(cache.getHottestKey().isPresent());
        assertEquals(cache.getHottestKey().get(), CAPACITY - 1);

        cache.get(1);

        assertTrue(cache.getHottestKey().isPresent());
        assertEquals(cache.getHottestKey().get(), 1);
    }

    @Test
    public void addSameKeyTest() {
        cache.add(237, 237);
        cache.add(237, 238);

        assertEquals(cache.size(), 1);

        Optional<Integer> value = cache.get(237);
        assertTrue(value.isPresent());
        assertEquals(value.get(), 238);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void getNullTest() {
        assertThrows(NullPointerException.class, () -> cache.get(null));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void addNullTest() {
        assertThrows(NullPointerException.class, () -> cache.add(null, 237));
        assertThrows(NullPointerException.class, () -> cache.add(237, null));
    }
}
