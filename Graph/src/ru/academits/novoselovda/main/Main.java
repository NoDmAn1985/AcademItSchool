package ru.academits.novoselovda.main;

import ru.academits.novoselovda.graph.Graph;

public class Main {
    public static void main(String[] args) {
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
        Graph<String> graph = new Graph<>(table);
        graph.addGraphNode("нуль");
        graph.addGraphNode("один");
        graph.addGraphNode("два");
        graph.addGraphNode("три");
        graph.addGraphNode("четыре");
        graph.addGraphNode("пять");
        graph.addGraphNode("шесть");
        graph.addGraphNode("семь");
        System.out.println("обход в ширину:");
        graph.breadthFirstSearch();
        System.out.println();
        System.out.println("обход в глубину:");
        graph.depthFirstSearch();
    }
}
