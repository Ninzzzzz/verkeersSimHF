package com.ninzzzzz.verkeersSimHF.implementations;

public class MyQueue<T> {
    private Node<T> front, rear;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public void enqueue(T data) {
        Node<T> node = new Node<>(data);
        if (rear != null) {
            rear.next = node;
        }
        rear = node;
        if (front == null) {
            front = node;
        }
    }

    public T dequeue() {
        if (front == null) throw new IllegalStateException("Queue is empty");
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        return data;
    }

    public T peek() {
        if (front == null) throw new IllegalStateException("Queue is empty");
        return front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }
}
