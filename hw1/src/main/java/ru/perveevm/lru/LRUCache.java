package ru.perveevm.lru;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCache<K, V> {
    private final Map<K, Node> data;
    private final LRUCacheList order = new LRUCacheList();
    private final int capacity;

    public LRUCache(int capacity) {
        data = new HashMap<>(capacity);
        this.capacity = capacity;
    }

    public Optional<V> get(@NonNull final K key) {
        if (!data.containsKey(key)) {
            return Optional.empty();
        }

        Node node = data.get(key);
        assert node != null : "Data can't contain null nodes";
        assert node.key.equals(key) : "Key mismatch";

        order.remove(node);
        order.add(node);
        assert node == order.head : "New node should be the first in the order";
        assert data.size() == order.size : "Data and order sizes mismatch";

        return Optional.of(node.value);
    }

    public void add(@NonNull final K key, @NonNull final V value) {
        if (data.containsKey(key)) {
            Node node = data.get(key);
            assert node != null : "Data can't contain null nodes";
            assert node.key.equals(key) : "Key mismatch";

            node.value = value;
            order.remove(node);
            order.add(node);
            assert node == order.head : "New node should be the first in the order";
            assert data.size() == order.size : "Data and order sizes mismatch";

            return;
        }

        if (data.size() == capacity) {
            assert order.tail != null : "Order list can't be null";
            assert order.tail.key != null : "Order list can't contain null keys";
            data.remove(order.tail.key);
            order.remove(order.tail);
        }

        Node node = new Node(key, value);
        data.put(key, node);
        order.add(node);
        assert node == order.head : "New node should be the first in the order";
        assert data.size() == order.size : "Data and order sizes mismatch";
    }

    public Optional<K> getHottestKey() {
        if (data.isEmpty()) {
            return Optional.empty();
        }

        assert order.head != null : "Order list shouldn't be empty so head shouldn't be null";
        assert order.head.key != null : "Order shouldn't contain null keys";
        return Optional.of(order.head.key);
    }

    public int size() {
        return data.size();
    }

    public int capacity() {
        return capacity;
    }

    private class Node {
        final K key;
        V value;
        Node next, prev;

        public Node(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class LRUCacheList {
        Node head, tail;
        private int size = 0;

        public void add(@NonNull final Node node) {
            size++;
            assert size <= capacity : "Capacity overflow";

            if (head != null) {
                node.next = head;
                head.prev = node;
            } else {
                tail = node;
            }
            head = node;
        }

        public void remove(@NonNull final Node node) {
            size--;
            Node prev = node.prev;
            Node next = node.next;

            if (node == head) {
                head = next;
            }
            if (node == tail) {
                tail = prev;
            }

            if (prev != null) {
                prev.next = next;
            }
            if (next != null) {
                next.prev = prev;
            }
        }
    }
}
