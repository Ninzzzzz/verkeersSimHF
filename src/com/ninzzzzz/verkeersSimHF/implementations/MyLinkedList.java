package com.ninzzzzz.verkeersSimHF.implementations;

public class MyLinkedList<Vehicle> {
    private Node<Vehicle> head;

    private static class Node<Vehicle> {
        Vehicle data;
        Node<Vehicle> next;

        Node(Vehicle data) {
            this.data = data;
        }
    }

    public void add(Vehicle data) {
        Node<Vehicle> node = new Node<>(data);
        if (head == null) {
            head = node;
        } else {
            Node<Vehicle> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;
        }
    }

    public void remove(Vehicle data) {
        if (head == null) return;

        if (head.data.equals(data)) {
            head = head.next;
            return;
        }

        Node<Vehicle> current = head;
        while (current.next != null && !current.next.data.equals(data)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    public boolean contains(Vehicle data) {
        Node<Vehicle> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
