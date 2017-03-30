package ru.academits.novoselovda.list;

import java.util.Objects;

public class Node<T> {
    private T data;
    private Node<T> next;
    private Node<T> randomNext;

    public Node() {
        this.data = null;
        this.next = null;
    }

    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    Node<T> getNext() {
        return next;
    }

    void setNext(Node<T> next) {
        this.next = next;
    }

    public void setRandomNext(Node<T> randomNext) {
        this.randomNext = randomNext;
    }

    public Node<T> getRandomNext() {
        return randomNext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return Objects.equals(data, node.data) &&
                Objects.equals(next, node.next) &&
                Objects.equals(randomNext, node.randomNext);
    }

    @Override
    public String toString() {
        if (data != null) {
            return data.toString();
        } else {
            return "пусто";
        }
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int hash = 1;
        hash = prime * hash + ((data != null) ? data.hashCode() : 0);
        hash = prime * hash + ((next != null) ? next.hashCode() : 0);
        hash = prime * hash + ((next != null) ? randomNext.hashCode() : 0);
        return hash;
    }
}
