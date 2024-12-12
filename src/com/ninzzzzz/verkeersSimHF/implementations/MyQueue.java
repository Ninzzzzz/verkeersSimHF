package com.ninzzzzz.verkeersSimHF.implementations;

public class MyQueue<Vehicle> {
    private Node<Vehicle> front, rear;

    private static class Node<Vehicle> {
        Vehicle data;
        Node<Vehicle> next;

        Node(Vehicle data) {
            this.data = data;
        }
    }

    public void enqueue(Vehicle data) {
        Node<Vehicle> node = new Node<>(data);
        if (rear != null) {
            rear.next = node;
        }
        rear = node;
        if (front == null) {
            front = node;
        }
    }

    public Vehicle dequeue() {
        if (front == null) throw new IllegalStateException("Queue is empty");
        Vehicle data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        return data;
    }

    public Vehicle peek() {
        if (front == null) throw new IllegalStateException("Queue is empty");
        return front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    // Optional size method if needed
    public int size() {
        int size = 0;
        Node<Vehicle> current = front;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MyQueue{");
        Node<Vehicle> current = front;
        while (current != null) {
            sb.append(current.data); // Append the data of each node
            if (current.next != null) {
                sb.append(", "); // Add a comma between elements
            }
            current = current.next;
        }
        sb.append("}");
        return sb.toString();
    }
}
