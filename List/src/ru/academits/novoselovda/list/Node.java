package ru.academits.novoselovda.list;

import java.util.Objects;

public class Node<E> {
    private E data;
    private Node next;
    private Node randomNext;

    public Node() {
        this.data = null;
        this.next = null;
    }

    public Node(E data) {
        this.data = data;
        this.next = null;
    }
    public Node(E data, Node next) {
        this.data = data;
        this.next = next;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    Node getNext() {
        return next;
    }

    void setNext(Node next) {
        this.next = next;
    }

    public void setRandomNext(Node randomNext) {
        this.randomNext = randomNext;
    }

    public Node getRandomNext() {
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
