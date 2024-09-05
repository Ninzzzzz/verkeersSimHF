package com.ninzzzzz.verkeersSimHF.implementations;

public class MyPriorityQueue<T> {
    private T[] items;
    private int size;
    private int[] priorities;

    @SuppressWarnings("unchecked")
    public MyPriorityQueue(int capacity) {
        items = (T[]) new Object[capacity];
        priorities = new int[capacity];
        size = 0;
    }

    public void enqueue(T item, int priority) {
        if (size == items.length) {
            resize(2 * items.length);
        }
        items[size] = item;
        priorities[size] = priority;
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
        priorities[size] = 0;
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
        return priorities[i] < priorities[j];
    }

    private void swap(int i, int j) {
        T tempItem = items[i];
        int tempPriority = priorities[i];
        items[i] = items[j];
        priorities[i] = priorities[j];
        items[j] = tempItem;
        priorities[j] = tempPriority;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        T[] tempItems = (T[]) new Object[capacity];
        int[] tempPriorities = new int[capacity];
        System.arraycopy(items, 0, tempItems, 0, size);
        System.arraycopy(priorities, 0, tempPriorities, 0, size);
        items = tempItems;
        priorities = tempPriorities;
    }
}
