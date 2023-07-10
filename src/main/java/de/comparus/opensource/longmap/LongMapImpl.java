package de.comparus.opensource.longmap;

import java.lang.reflect.Array;

public class LongMapImpl<V> implements LongMap<V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final int MAX_CAPACITY = 1073741824;
    private static final double LOAD_FACTOR = 0.75;

    private Node<V>[] table;
    private int size;

    public LongMapImpl(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity should be more than zero");
        }
        if (capacity > MAX_CAPACITY) {
            capacity = MAX_CAPACITY;
        }
        table = new Node[capacity];
    }

    public LongMapImpl() {
        table = new Node[INITIAL_CAPACITY];
    }

    public V put(long key, V value) {
        reSize();
        int index = findIndex(key);
        Node<V> newNode = new Node<>(key, value);
        if (table[index] == null) {
            table[index] = newNode;
        } else {
            Node<V> current = table[index];
            while (current != null) {
                if (current.key == key) {
                    V oldValue = current.value;
                    current.value = value;
                    return oldValue;
                } else if (current.next == null) {
                    current.next = newNode;
                    break;
                }
                current = current.next;
            }
        }
        size++;
        return null;
    }

    public V get(long key) {
        Node<V> node = findNodeByKey(key);
        return node == null ? null : node.value;
    }

    public V remove(long key) {
        int index = findIndex(key);
        for (Node<V> prev = null, current = table[index]; current != null;
             prev = current, current = current.next) {
            if (current.key == key) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        Node<V> nodeByKey = findNodeByKey(key);
        return nodeByKey != null;
    }

    public boolean containsValue(V value) {
        for (Node<V> node : table) {
            for (Node<V> current = node; current != null; current = current.next) {
                if (current.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public long[] keys() {
        long[] keys = new long[size];
        int index = 0;
        for (Node<V> node : table) {
            for (Node<V> current = node; current != null; current = current.next) {
                keys[index] = current.key;
                index++;
            }
        }
        return keys;
    }

    public V[] values() {
        V[] objectArray = (V[]) new Object[size];

        int index = 0;
        for (Node<V> node : table) {
            Node<V> currentNode = node;
            while (currentNode != null) {
                objectArray[index] = currentNode.value;
                currentNode = currentNode.next;
                index++;
            }
        }
        return objectArray;
    }

    private V[] createEmptyArray(V value) {
        return (V[]) Array.newInstance(value.getClass(), size);
    }

    public long size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    private void reSize() {
        if (table.length * LOAD_FACTOR <= size) {
            Node<V>[] newTable = new Node[table.length * 2];
            transfer(newTable);
            table = newTable;
        }
    }

    private void transfer(Node<V>[] newTable) {
        for (Node<V> node : table) {
            for (Node<V> oldCurrent = node; oldCurrent != null; oldCurrent = oldCurrent.next) {
                long key = oldCurrent.key;
                int index = findIndex(key, newTable.length);
                Node<V> newNode = new Node<>(oldCurrent.key, oldCurrent.value);
                if (newTable[index] == null) {
                    newTable[index] = newNode;
                } else {
                    Node<V> newCurrent = newTable[index];
                    while (newCurrent.next != null) {
                        newCurrent = newCurrent.next;
                    }
                    newCurrent.next = newNode;
                }
            }
        }
    }

    private int findIndex(long key, int capacity) {
        int keyHashCode = Math.abs(Long.hashCode(key));
        return keyHashCode % capacity;
    }

    private int findIndex(long key) {
        return findIndex(key, table.length);
    }

    private Node<V> findNodeByKey(long key) {
        int index = findIndex(key);
        for (Node<V> current = table[index]; current != null; current = current.next) {
            if (current.key == key) {
                return current;
            }
        }
        return null;
    }

    private static class Node<V> {
        long key;
        V value;
        Node<V> next;

        public Node(long key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
