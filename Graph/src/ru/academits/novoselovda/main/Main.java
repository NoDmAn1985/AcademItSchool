package ru.academits.novoselovda.main;

import ru.academits.novoselovda.graph.Graph;
import ru.academits.novoselovda.graph.GraphNode;

public class Main {
    public static void main(String[] args) {
        /*
        int[][] table = {
                //0,1, 2, 3, 4, 5, 6, 7
                {0, 1, 0, 0, 0, 0, 0, 0}, //0
                {1, 0, 1, 0, 0, 0, 0, 0}, //1
                {0, 1, 0, 1, 1, 1, 1, 0}, //2
                {0, 0, 1, 0, 0, 0, 0, 1}, //3
                {0, 0, 1, 0, 0, 0, 0, 0}, //4
                {0, 0, 1, 0, 0, 0, 1, 0}, //5
                {0, 0, 1, 0, 0, 1, 0, 1}, //6
                {0, 0, 0, 1, 0, 0, 1, 0}};//7
        */
        int[][] table = {
                //0,1, 2, 3, 4, 5, 6, 7
                {0, 1, 0, 0, 0, 0, 0, 0}, //0
                {1, 0, 1, 0, 0, 0, 0, 0}, //1
                {0, 1, 0, 1, 0, 1, 0, 0}, //2
                {0, 0, 1, 0, 0, 1, 0, 0}, //3
                {0, 0, 0, 0, 0, 0, 0, 0}, //4
                {0, 0, 1, 1, 0, 0, 0, 0}, //5
                {0, 0, 0, 0, 0, 0, 0, 1}, //6
                {0, 0, 0, 0, 0, 0, 1, 0}};//7
        @SuppressWarnings("unchecked")
        GraphNode<String>[] nodes = new GraphNode[table.length];
        nodes[0] = new GraphNode<>("нуль", 0);
        nodes[1] = new GraphNode<>("один", 1);
        nodes[2] = new GraphNode<>("два", 2);
        nodes[3] = new GraphNode<>("три", 3);
        nodes[4] = new GraphNode<>("четыре", 4);
        nodes[5] = new GraphNode<>("пять", 5);
        nodes[6] = new GraphNode<>("шесть", 6);
        nodes[7] = new GraphNode<>("семь", 7);
        Graph<String> graph = new Graph<>(table, nodes);

        System.out.println("обход в ширину:");
        graph.breadthFirstSearch();
        System.out.println();
        System.out.println("обход в глубину:");
        graph.depthFirstSearch();
    }
}
