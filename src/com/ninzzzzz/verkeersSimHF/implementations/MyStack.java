package com.ninzzzzz.verkeersSimHF.implementations;

public class MyStack<T> {
    private Node<T> top;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public void push(T data) {
        Node<T> node = new Node<>(data);
        node.next = top;
        top = node;
    }

    public T pop() {
        if (top == null) throw new IllegalStateException("Stack is empty");
        T data = top.data;
        top = top.next;
        return data;
    }

    public T peek() {
        if (top == null) throw new IllegalStateException("Stack is empty");
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
