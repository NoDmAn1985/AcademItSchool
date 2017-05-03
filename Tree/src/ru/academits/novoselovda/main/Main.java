package ru.academits.novoselovda.main;

import ru.academits.novoselovda.tree.Tree;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();
        tree.add(123);
        tree.add(93);
        tree.add(83);
        tree.add(183);
        tree.add(97);
        tree.add(197);
        tree.add(85);
        tree.add(32);
        tree.add(29);
        tree.add(17);
        tree.add(183);
        tree.add(35);
        tree.add(33);
        tree.add(37);
        tree.add(30);
        tree.add(96);
        tree.add(122);
        tree.add(193);
        tree.add(203);
        System.out.println("------------------------------------");
        System.out.println("обход по ширине");
        tree.breadthFirstSearch();
        System.out.println("------------------------------------");
        System.out.println("обход в глубину (рекурсия)");
        tree.recursiveDepthFirstSearch();
        System.out.println("------------------------------------");
        System.out.println("обход в глубину (стэк)");
        tree.stackDepthFirstSearch();
        System.out.println("------------------------------------");
        System.out.println("contains(83)");
        System.out.println(tree.contains(83));
        System.out.println("------------------------------------");
        System.out.println("remove(35)");
        System.out.println(tree.remove(35));
        tree.breadthFirstSearch();
    }
}
