package ru.academits.novoselovda.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Graph<T> {
    private GraphNode<T>[] nodes;
    private int[][] table;

    public Graph(int[][] table, GraphNode<T>[] nodes) {
        this.table = table;
        this.nodes = nodes;
    }

    public void breadthFirstSearch() {
        Queue<GraphNode<T>> queue = new LinkedList<>();
        boolean[] isVerifiedNodes = new boolean[this.nodes.length];
        int lastIndex;
        for (int i = 0; i < this.nodes.length; i++) {
            lastIndex = i;
            if (!isVerifiedNodes[i]) {
                isVerifiedNodes[i] = true;
                queue.add(this.nodes[i]);
                while (!queue.isEmpty()) {
                    GraphNode<T> tempNode = queue.remove();
                    i = tempNode.getIndex();
                    System.out.println(tempNode);
                    for (int j = 0; j < this.table.length; ++j) {
                        if (this.table[i][j] != 0 && !isVerifiedNodes[j]) {
                            isVerifiedNodes[j] = true;
                            queue.add(this.nodes[j]);
                        }
                    }
                }
                i = lastIndex;
            }
        }
    }

    public void depthFirstSearch() {
        HashSet<GraphNode<T>> set = new HashSet<>();
        for (int i = 0; i < this.nodes.length; i++) {
            if (set.add(this.nodes[i])) {
                System.out.println(this.nodes[i]);
                visit(i, set);
            }
        }
    }

    private void visit(int index, HashSet<GraphNode<T>> set) {
        for (int j = 0; j < this.table.length; ++j) {
            if (this.table[index][j] != 0 && set.add(this.nodes[j])) {
                System.out.println(this.nodes[j]);
                visit(j, set);
            }
        }
    }
}
