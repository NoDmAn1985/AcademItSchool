package ru.academits.novoselovda.graph;

import java.util.LinkedList;
import java.util.Queue;

public class Graph<T> {
    private final int GRAPH_NODE_COUNT;
    private int graphNodeIndex;
    private GraphNode<T>[] nodes;
    private boolean[] isVerifiedNodes;
    private int[][] table;

    private static class GraphNode<T> {
        private T value;
        private int index;

        GraphNode(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + value + "(" + index + ")}";
        }
    }

    @SuppressWarnings("unchecked")
    public Graph(int[][] table) {
        this.GRAPH_NODE_COUNT = table.length;
        this.nodes = new GraphNode[GRAPH_NODE_COUNT];
        this.isVerifiedNodes = new boolean[GRAPH_NODE_COUNT];
        this.table = table;
    }

    public void addGraphNode(T value) {
        if (this.graphNodeIndex == GRAPH_NODE_COUNT) {
            throw new IndexOutOfBoundsException();
        }
        this.nodes[this.graphNodeIndex] = new GraphNode<>(value);
        this.nodes[this.graphNodeIndex].index = this.graphNodeIndex;
        ++this.graphNodeIndex;
    }

    public void breadthFirstSearch() {
        int i = 0;
        Queue<GraphNode<T>> queue = new LinkedList<>();
        this.isVerifiedNodes[i] = true;
        queue.add(this.nodes[i]);
        while (!queue.isEmpty()) {
            GraphNode<T> tempNode = queue.remove();
            i = tempNode.index;
            System.out.println(tempNode);
            for (int j = 0; j < this.table.length; ++j) {
                if (this.table[i][j] != 0 && !this.isVerifiedNodes[j]) {
                    this.isVerifiedNodes[j] = true;
                    queue.add(this.nodes[j]);
                }
            }
        }
    }

    public void depthFirstSearch() {
        int i = 0;
        Queue<GraphNode<T>> queue = new LinkedList<>();
        System.out.println(this.nodes[i]);
        queue.add(this.nodes[i]);
        visit(i, queue);
    }

    private void visit(int index, Queue<GraphNode<T>> queue) {
        for (int j = 0; j < this.table.length; ++j) {
            if (this.table[index][j] != 0 && !queue.contains(this.nodes[j])) {
                System.out.println(this.nodes[j]);
                queue.add(this.nodes[j]);
                visit(j, queue);
            }
        }
    }
}
