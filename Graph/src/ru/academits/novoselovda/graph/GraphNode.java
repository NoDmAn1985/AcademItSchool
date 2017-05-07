package ru.academits.novoselovda.graph;

public class GraphNode<T> {
    private T value;
    private int index;

    public GraphNode(T value, int index) {
        this.value = value;
        this.index = index;
    }

    int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return "{" + value + "(" + index + ")}";
    }
}
