package com.ninzzzzz.verkeersSimHF.implementations;

public class MyStack<Vehicle> {
    private Node<Vehicle> top;

    private static class Node<Vehicle> {
        Vehicle data;
        Node<Vehicle> next;

        Node(Vehicle data) {
            this.data = data;
        }
    }

    public void push(Vehicle data) {
        Node<Vehicle> node = new Node<>(data);
        node.next = top;
        top = node;
    }

    public Vehicle pop() {
        if (top == null) throw new IllegalStateException("Stack is empty");
        Vehicle data = top.data;
        top = top.next;
        return data;
    }

    public Vehicle peek() {
        if (top == null) throw new IllegalStateException("Stack is empty");
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
