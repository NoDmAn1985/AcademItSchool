package ru.academits.novoselovda.main;

import ru.academits.novoselovda.tree.Tree;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();
//        tree.add(null);
//        tree.add(123);
//        tree.add(93);
//        tree.add(83);
//        tree.add(183);
//        tree.add(97);
//        tree.add(197);
//        tree.add(85);
//        tree.add(32);
//        tree.add(29);
//        tree.add(17);
//        tree.add(183);
//        tree.add(null);
//        tree.add(35);
//        tree.add(33);
//        tree.add(37);
//        tree.add(30);
//        tree.add(96);
//        tree.add(122);
//        tree.add(193);
//        tree.add(203);
//        System.out.println("------------------------------------");
//        System.out.println("обход по ширине");
//        tree.breadthFirstSearch();
//        System.out.println("------------------------------------");
//        System.out.println("обход в глубину (рекурсия)");
//        tree.recursiveDepthFirstSearch();
//        System.out.println("------------------------------------");
//        System.out.println("обход в глубину (стэк)");
//        tree.stackDepthFirstSearch();
//        System.out.println("------------------------------------");
//        System.out.println("contains(83)");
//        System.out.println(tree.contains(null));
//        System.out.println("------------------------------------");
//        System.out.println("remove(null)");
//        System.out.println(tree.remove(null));
//        tree.breadthFirstSearch();

        tree.add(10);
        tree.add(6);
        tree.add(12);
        tree.add(4);
        tree.add(9);
        tree.add(7);
        tree.add(11);
        tree.add(13);
        tree.add(5);

        tree.breadthFirstSearch();
        System.out.println();

        tree.remove(4);
        tree.breadthFirstSearch();


    }
}
