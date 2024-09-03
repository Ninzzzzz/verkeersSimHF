package com.ninzzzzz.verkeersSimHF.implementations;

public class MyPriorityQueue<T extends Comparable<T>> {
    private T[] items;
    private int size;

    @SuppressWarnings("unchecked")
    public MyPriorityQueue(int capacity) {
        items = (T[]) new Comparable[capacity];
        size = 0;
    }

    public void enqueue(T item) {
        if (size == items.length) {
            resize(2 * items.length);
        }
        items[size] = item;
        size++;
        swim(size - 1);
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T max = items[0];
        swap(0, size - 1);
        size--;
        sink(0);
        items[size] = null;
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return max;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return items[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void swim(int k) {
        while (k > 0 && less((k - 1) / 2, k)) {
            swap(k, (k - 1) / 2);
            k = (k - 1) / 2;
        }
    }

    private void sink(int k) {
        while (2 * k + 1 < size) {
            int j = 2 * k + 1;
            if (j < size - 1 && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return items[i].compareTo(items[j]) < 0;
    }

    private void swap(int i, int j) {
        T temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        T[] temp = (T[]) new Comparable[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }
}
